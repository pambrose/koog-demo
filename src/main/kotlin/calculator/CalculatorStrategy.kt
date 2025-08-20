package calculator

import ai.koog.agents.core.dsl.builder.forwardTo
import ai.koog.agents.core.dsl.builder.strategy
import ai.koog.agents.core.dsl.extension.nodeExecuteMultipleTools
import ai.koog.agents.core.dsl.extension.nodeLLMCompressHistory
import ai.koog.agents.core.dsl.extension.nodeLLMRequestMultiple
import ai.koog.agents.core.dsl.extension.nodeLLMSendMultipleToolResults
import ai.koog.agents.core.dsl.extension.onAssistantMessage
import ai.koog.agents.core.dsl.extension.onMultipleToolCalls
import ai.koog.agents.core.environment.ReceivedToolResult
import io.github.oshai.kotlinlogging.KotlinLogging


object CalculatorStrategy {
  internal val logger = KotlinLogging.logger {}
  private const val MAX_TOKENS_THRESHOLD = 1000

  val strategy = strategy<String, String>("test") {
    val initialLLMNode by nodeLLMRequestMultiple()
    val executeMultipleToolsNode by nodeExecuteMultipleTools(parallelTools = true)
    val compressHistoryLLMNode by nodeLLMCompressHistory<List<ReceivedToolResult>>()
    val receiveMultipleToolResultsLLMNode by nodeLLMSendMultipleToolResults()


    edge(nodeStart forwardTo initialLLMNode)

    edge(
      initialLLMNode
          forwardTo nodeFinish
          transformed { resp -> resp.first() }
          onAssistantMessage { assistant ->
        logger.info { "initialLLMNode -> nodeFinish: assistant message: ${assistant}" }
        true
      }
    )

    edge(
      initialLLMNode
          forwardTo executeMultipleToolsNode
          onMultipleToolCalls {
        logger.info { "initialLLMNode -> executeMultipleToolsNode with tool calls: ${it}" }
        true
      }
    )

    edge(
      executeMultipleToolsNode
          forwardTo compressHistoryLLMNode
          onCondition {
        llm.readSession {
          logger.info { "executeMultipleToolsNode -> compressHistoryLLMNode transition: token usage: ${prompt.latestTokenUsage}" }
          prompt.latestTokenUsage > MAX_TOKENS_THRESHOLD
        }
      }
    )

    edge(compressHistoryLLMNode forwardTo receiveMultipleToolResultsLLMNode)

    edge(
      executeMultipleToolsNode
          forwardTo receiveMultipleToolResultsLLMNode
          onCondition {
        llm.readSession {
          logger.info { "executeMultipleToolsNode -> receiveMultipleToolResultsLLMNode transition with tool calls: ${it}" }
          prompt.latestTokenUsage <= MAX_TOKENS_THRESHOLD
        }
      }
    )

    edge(
      receiveMultipleToolResultsLLMNode
          forwardTo executeMultipleToolsNode
          onMultipleToolCalls {
        logger.info { "receiveMultipleToolResultsLLMNode -> executeMultipleToolsNode with tool calls: ${it}" }
        true
      }
    )

    edge(
      receiveMultipleToolResultsLLMNode
          forwardTo nodeFinish
          transformed { responses ->
        logger.info { "\nreceiveMultipleToolResultsLLMNode -> nodeFinish output: \n${responses.joinToString("\n") { "[${it.content}]" }}" }
        logger.info { "\nreceiveMultipleToolResultsLLMNode -> nodeFinish output: \n${responses.first()}" }
        responses.first()
      }
          onAssistantMessage {
        true
      }
    )
  }
}