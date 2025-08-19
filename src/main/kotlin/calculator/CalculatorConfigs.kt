package calculator

import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.llm.OllamaModels
import ai.koog.prompt.params.LLMParams

object CalculatorConfigs {

  // Create agent config with proper prompt
  val openAIAgentConfig = AIAgentConfig(
    prompt = prompt("test") {
      system("You are a calculator.")
    },
    model = OpenAIModels.Chat.GPT4o,
    maxAgentIterations = 50
  )

  // Create agent config with a proper prompt
  val ollamaAgentConfig = AIAgentConfig(
    prompt = prompt("test", LLMParams(temperature = 0.0)) {
      system("You are a calculator.")
    },
    model = OllamaModels.Meta.LLAMA_3_2,
    maxAgentIterations = 50
  )
}