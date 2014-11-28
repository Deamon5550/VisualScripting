package com.thevoxelbox.vsl.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import java.io.Serializable;

import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.INodeGraph;

/**
 * An acyclic directed graph of {@link Node}s.
 */
public class NodeGraph implements INodeGraph, Opcodes, Serializable
{
    private static final long serialVersionUID = 4398861156694365589L;
    /**
     * The name of this graph.
     */
    private String name;
    /**
     * The current increment of this graph, used to avoid classloader name collisions.
     */
    private int c = 0;
    /**
     * The starting point of this graph
     */
    private ExecutableNode start = null;

    /**
     * Creates a new {@link NodeGraph}.
     * 
     * @param name the name for the new graph, cannot be null or empty
     */
    public NodeGraph(String name)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        this.name = name.replace(" ", "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartNode(ExecutableNode start)
    {
        checkNotNull(start, "Starting point cannot be null!");
        this.start = start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutableNode getStart()
    {
        return this.start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIncrement()
    {
        return this.c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementName()
    {
        this.c++;
    }

}
