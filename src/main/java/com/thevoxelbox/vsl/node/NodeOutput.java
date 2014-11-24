package com.thevoxelbox.vsl.node;

import java.io.Serializable;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.type.Type;

public class NodeOutput implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3481166480208261206L;
    private int i = -1;
    private final String name;
    private final Type type;
    private final INode parent;

    public NodeOutput(String n, Type t, INode parent)
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

    public Type getType()
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
