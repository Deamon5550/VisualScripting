package com.thevoxelbox.vsl.node;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.api.INode;

public class NodeOutput
{
    private int i = -1;
    private final String name;
    private final IOType type;
    private final INode parent;
    private boolean chained = false;

    public NodeOutput(String n, IOType t, INode parent)
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

    public IOType getType()
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

    public boolean isChained()
    {
        return chained;
    }

    public void setChained(boolean chained)
    {
        this.chained = chained;
    }
}
