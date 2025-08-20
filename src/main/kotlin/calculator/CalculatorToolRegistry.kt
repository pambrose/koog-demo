package calculator

import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.core.tools.reflect.asTools
import ai.koog.agents.ext.tool.AskUser
import ai.koog.agents.ext.tool.SayToUser

object CalculatorToolRegistry {
  // Create a tool registry with calculator tools
  val toolRegistry = ToolRegistry {
    // Special tools, required with this type of agent.
    tool(AskUser)
    tool(SayToUser)
    tools(CalculatorTools().asTools())
  }
}