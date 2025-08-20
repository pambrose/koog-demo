# Calculator Strategy Diagram

```mermaid
graph TD
    Start([nodeStart]) --> InitialLLM[initialLLMNode]
    
    InitialLLM --> |Assistant Message| Finish([nodeFinish])
    InitialLLM --> |Multiple Tool Calls| ExecuteTools[executeMultipleToolsNode]
    
    ExecuteTools --> |Token Usage > MAX_TOKENS_THRESHOLD| CompressHistory[compressHistoryLLMNode]
    ExecuteTools --> |Token Usage <= MAX_TOKENS_THRESHOLD| ReceiveResults[receiveMultipleToolResultsLLMNode]
    
    CompressHistory --> ReceiveResults
    
    ReceiveResults --> |Multiple Tool Calls| ExecuteTools
    ReceiveResults --> |Assistant Message| Finish
    
    style Start fill:#e1f5fe
    style Finish fill:#f3e5f5
    style InitialLLM fill:#fff3e0
    style ExecuteTools fill:#e8f5e8
    style CompressHistory fill:#fff8e1
    style ReceiveResults fill:#fce4ec
```

## Node Descriptions

- **nodeStart**: Entry point of the workflow
- **initialLLMNode**: Initial LLM request that can handle multiple responses
- **executeMultipleToolsNode**: Executes multiple tools in parallel
- **compressHistoryLLMNode**: Compresses conversation history when token limit is exceeded
- **receiveMultipleToolResultsLLMNode**: Processes multiple tool results from LLM
- **nodeFinish**: Exit point of the workflow

## Edge Conditions

- **Assistant Message**: Flow continues when LLM responds with a regular message
- **Multiple Tool Calls**: Flow branches when LLM requests multiple tool executions
- **Token Threshold**: Conditional routing based on token usage (MAX_TOKENS_THRESHOLD)