package com.thevoxelbox.vsl.api;

import java.util.Map;

/**
 * A {@link INodeGraph} which may be chained into another IChainableNodeGraph passing values on to it.
 */
public interface IChainableNodeGraph extends INodeGraph
{

    /**
     * Returns whether the given {@link IChainableNodeGraph}'s inputs match the outputs of this IChainableNodeGraph.
     * 
     * @param nextGraph the IChainableNodeGraph to check against
     * @return whether the inputs and outputs match
     */
    boolean doInputsMatch(IChainableNodeGraph nextGraph);

    /**
     * Adds an input which will be received from a preceding IChainableNodeGraph.
     * 
     * @param name the name of the variable to receive, cannot be null or empty
     * @param type the type of the variable to receive, cannot be null
     */
    void addChainedInput(String name, Class<?> type);

    /**
     * Adds an output which will be sent to the next IChainableNodeGraph.
     * 
     * @param name the name of the variable to send, cannot be null or empty
     * @param type the type of the variable to send, cannot be null
     */
    void addChainedOutput(String name, Class<?> type);

    /**
     * Returns a Map of all inputs to this IChainableNodeGraph.
     * 
     * @return the inputs
     */
    Map<String, Class<?>> getInputs();

    /**
     * Returns a Map of all outputs from this IChainableNodeGraph.
     * 
     * @return the outputs
     */
    Map<String, Class<?>> getOutputs();

}
