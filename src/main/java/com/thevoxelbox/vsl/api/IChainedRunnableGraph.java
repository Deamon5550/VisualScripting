package com.thevoxelbox.vsl.api;

/**
 * A compiled graph which may be chained into another IChainedRunnableGraph.
 */
public interface IChainedRunnableGraph extends IRunnableGraph
{

    /**
     * Set the node graph to follow this one.
     * 
     * @param next the node graph to execute next
     */
    void chain(IChainedRunnableGraph next);

}
