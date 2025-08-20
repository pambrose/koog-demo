package calculator

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.all.simpleOpenAIExecutor
import calculator.CalculatorCallbacks.calculatorCallbacks
import calculator.CalculatorConfigs.openAIAgentConfig
import calculator.CalculatorToolRegistry.toolRegistry
import com.github.pambrose.common.ApiKeyService
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

  val agent = AIAgent(
    promptExecutor = simpleOpenAIExecutor(ApiKeyService.openAIApiKey),
    strategy = CalculatorStrategy.strategy,
    agentConfig = openAIAgentConfig,
    toolRegistry = toolRegistry
  ) {
    calculatorCallbacks()
  }

  runBlocking {
    agent.run("(1 + 2) * (5 + 6) / (12 - 6)")
  }
}
