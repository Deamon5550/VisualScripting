package com.thevoxelbox.vsl.node;

public abstract class ExecutableNode extends Node
{
    private static final long serialVersionUID = 6938126440494121404L;
    ExecutableNode next = null;

    public ExecutableNode(String name)
    {
        super(name);
    }

    public ExecutableNode(String name, String packageName)
    {
        super(name, packageName);
    }

    @Override
    public boolean isExecutable()
    {
        return true;
    }

    public void setNextNode(ExecutableNode node)
    {
        this.next = node;
    }

    public ExecutableNode getNextNode()
    {
        return this.next;
    }

}
