package com.thevoxelbox.vsl.node;

/**
 * An executable node.
 */
public abstract class ExecutableNode extends Node
{
    private static final long serialVersionUID = 6938126440494121404L;
    /**
     * The next executable node in the graph.
     */
    ExecutableNode next = null;

    /**
     * Creates a new {@link ExecutableNode}.
     * 
     * @param name the node's name, cannot be null
     */
    public ExecutableNode(String name)
    {
        super(name);
    }

    /**
     * Creates a new {@link ExecutableNode}.
     * 
     * @param name the node's name, cannot be null
     * @param packageName the node's package, cannot be null
     */
    public ExecutableNode(String name, String packageName)
    {
        super(name, packageName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecutable()
    {
        return true;
    }

    /**
     * Sets the next executable node in the graph.
     * 
     * @param node the next node, may be null if this is the final node
     */
    public void setNextNode(ExecutableNode node)
    {
        this.next = node;
    }

    /**
     * Returns the next node in the graph, may be null if this is the final node in the graph.
     * 
     * @return the next node, or null
     */
    public ExecutableNode getNextNode()
    {
        return this.next;
    }

}
