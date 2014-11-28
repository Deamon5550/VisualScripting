package com.thevoxelbox.vsl.api;

/**
 * A compiled graph.
 */
public interface IRunnableGraph
{

    /**
     * Runs the graph with the given variables.
     * 
     * @param vars the variables for the runtime
     */
    void run(IVariableHolder vars);

    /**
     * Returns the name of this graph.
     * 
     * @return the name
     */
    String getName();

}
