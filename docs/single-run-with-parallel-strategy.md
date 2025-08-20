# Single Run With Parallel Strategy

This document describes the single run strategy implementation with parallel tool execution capability.

## Flow Diagram

```mermaid
graph TD
    Start([Start]) --> CallLLM[Call LLM]
    CallLLM -->|Multiple Tool Calls| ExecuteTool[Execute Tools]
    CallLLM -->|Multiple Assistant Messages| Finish([Finish])
    ExecuteTool --> SendToolResult[Send Tool Results]
    SendToolResult -->|Multiple Assistant Messages| Finish
    SendToolResult -->|Multiple Tool Calls| ExecuteTool
```

## Implementation Details

The `singleRunWithParallelAbility` function creates a strategy with the following components:

- **nodeCallLLM**: Handles multiple LLM requests
- **nodeExecuteTool**: Executes multiple tools (with optional parallel execution)
- **nodeSendToolResult**: Sends multiple tool results back to the LLM

The strategy supports conditional flow based on:

- Multiple tool calls trigger tool execution
- Multiple assistant messages lead to completion
- Tool results can either finish the process or trigger additional tool calls