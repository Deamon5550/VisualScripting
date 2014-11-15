package com.thevoxelbox.vsl.node;

import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.INodeGraph;

public class NodeGraph implements INodeGraph, Opcodes
{

    private String name;
    private int c = 0;
    private ExecutableNode start;

    public NodeGraph(String name)
    {
        this.name = name.replace(" ", "");
    }

    @Override
    public void setStartNode(ExecutableNode start)
    {
        this.start = start;
    }

    @Override
    public String getName()
    {
        return this.name + this.c;
    }

    @Override
    public ExecutableNode getStart()
    {
        return this.start;
    }

    @Override
    public int getIncrement()
    {
        return this.c;
    }

    @Override
    public void incrementName()
    {
        this.c++;
    }

}
