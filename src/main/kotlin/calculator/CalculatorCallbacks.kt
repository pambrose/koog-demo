package calculator

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.features.eventHandler.feature.handleEvents

object CalculatorCallbacks {
  fun AIAgent.FeatureContext.calculatorCallbacks() {
    handleEvents {
      onToolCall { eventContext ->
        println("Tool called: tool ${eventContext.tool.name}, args ${eventContext.toolArgs}")
      }

      onAgentRunError { eventContext ->
        println("An error occurred: ${eventContext.throwable.message}\n${eventContext.throwable.stackTraceToString()}")
      }

      onAgentFinished { eventContext ->
        println("Result: ${eventContext.result}")
      }
    }
  }
}