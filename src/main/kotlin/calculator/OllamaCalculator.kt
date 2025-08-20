package calculator

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.all.simpleOllamaAIExecutor
import calculator.CalculatorCallbacks.calculatorCallbacks
import calculator.CalculatorConfigs.ollamaAgentConfig
import calculator.CalculatorToolRegistry.toolRegistry
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

  val agent = AIAgent(
    promptExecutor = simpleOllamaAIExecutor(),
    strategy = CalculatorStrategy.strategy,
    agentConfig = ollamaAgentConfig,
    toolRegistry = toolRegistry
  ) {
    calculatorCallbacks()
  }

  runBlocking {
    agent.run(
      //"Use provided tools to calculate: (1 + 2) * (5 + 5) / (6 - 2)."
      "Use provided tools to calculate: (1 + 2) * (5 + 5) / (6 - 2). Call all the tools at once."
      //"Hello"
    )
  }
}
