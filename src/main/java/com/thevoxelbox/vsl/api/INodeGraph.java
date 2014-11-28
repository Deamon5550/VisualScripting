package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.node.ExecutableNode;

/**
 * A graph of nodes which may be compiled and then run.
 */
public interface INodeGraph
{

    /**
     * Sets the starting Node.
     * 
     * @param start the starting point, cannot be null
     */
    void setStartNode(ExecutableNode start);

    /**
     * Returns the name of this graph.
     * 
     * @return the name
     */
    String getName();

    /**
     * Returns the starting point of this graph
     * 
     * @return the starting node
     */
    ExecutableNode getStart();

    /**
     * Returns the increment of this graph, each time a graph is compiled its name must be incremented in order for there to be no overlap within the
     * classloader.
     * 
     * @return the current increment
     */
    int getIncrement();

    /**
     * Increments this node in order to avoid an overlap within the classloader upon recompiling.
     */
    void incrementName();

}
