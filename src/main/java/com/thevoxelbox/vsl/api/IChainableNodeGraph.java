package com.thevoxelbox.vsl.api;

import java.util.Map;

public interface IChainableNodeGraph extends INodeGraph
{
    boolean doInputsMatch(IChainableNodeGraph nextGraph);

    void addChainedInput(String name, Class<?> type);

    void addChainedOutput(String name, Class<?> type);

    Map<String, Class<?>> getInputs();

    Map<String, Class<?>> getOutputs();

}
