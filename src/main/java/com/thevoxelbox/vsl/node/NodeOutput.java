package com.thevoxelbox.vsl.node;

import com.thevoxelbox.vsl.api.INode;

public class NodeOutput
{
    private int i = -1;
    private final String name;
    private final Class<?> type;
    private final INode parent;

    public NodeOutput(String n, Class<?> t, INode parent)
    {
        this.name = n;
        this.type = t;
        this.parent = parent;
    }

    public INode getParent()
    {
        return this.parent;
    }

    public String getName()
    {
        return this.name;
    }

    public Class<?> getType()
    {
        return this.type;
    }

    public void set(int i)
    {
        this.i = i;
    }

    public int get()
    {
        return this.i;
    }
}
