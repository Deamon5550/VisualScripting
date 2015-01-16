package com.thevoxelbox.vsl.api.node;

import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;

/**
 * Represents a system on nodes which may be executed.
 */
public interface NodeGraph
{

    /**
     * Gets the graph name.
     * 
     * @return The name
     */
    String getName();
    
    /**
     * Runs this graph based on the given variables.
     * 
     * @param vars The runtime variables
     */
    void run(VariableHolder vars);

    /**
     * Chains this graph into another graph
     * 
     * @param next The next graph
     */
    void chain(RunnableNodeGraph next);

    /**
     * Gets the next graph.
     * 
     * @return The graph
     */
    RunnableNodeGraph getNextGraph();

    /**
     * Gets the starting node in the graphs execution pathway.
     * 
     * @return The starting node
     */
    Node getStart();

    /**
     * Sets the starting node in this graph's execution pathway.
     * 
     * @param node The new starting node
     */
    void setStart(Node node);

}
