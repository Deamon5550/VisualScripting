package com.thevoxelbox.vsl.api;

import java.util.Map;

import com.thevoxelbox.vsl.error.GraphCompilationException;

public interface IChainableNodeGraph extends INodeGraph
{
    boolean doInputsMatch(IChainableNodeGraph nextGraph);

    void chain(IChainableNodeGraph name) throws GraphCompilationException;
    
    void addChainedInput(String name, Class<?> type);
    
    void addChainedOutput(String name, Class<?> type);
    
    Map<String, Class<?>> getInputs();
    
    Map<String, Class<?>> getOutputs();

    IChainableNodeGraph getNextGraph();
    
}
