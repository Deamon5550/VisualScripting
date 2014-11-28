package com.thevoxelbox.vsl.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import java.io.Serializable;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.type.Type;

/**
 * An output for a {@link Node}.
 */
public class NodeOutput implements Serializable
{
    private static final long serialVersionUID = 3481166480208261206L;
    /**
     * The local variable index of this output, will be -1 until set.
     */
    private int index = -1;
    /**
     * The name of this output.
     */
    private final String name;
    /**
     * The type of this output.
     */
    private final Type type;
    /**
     * The parent {@link Node} of this output.
     */
    private final INode parent;

    /**
     * Creates a new {@link NodeOutput}.
     * 
     * @param name the name, cannot be null or empty
     * @param type the type, cannot be null
     * @param parent the parent node, cannot be null
     */
    public NodeOutput(String name, Type type, INode parent)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(type, "Type cannot be null!");
        checkNotNull(parent, "Parent node cannot be null!");
        this.name = name;
        this.type = type;
        this.parent = parent;
    }

    /**
     * Returns the parent {@link Node} for this output.
     * 
     * @return the parent node
     */
    public INode getParent()
    {
        return this.parent;
    }

    /**
     * Returns the name of this node.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the {@link Type} of this node.
     * 
     * @return the type
     */
    public Type getType()
    {
        return this.type;
    }

    /**
     * Sets the local variable index of this output.
     * 
     * @param index the new index, cannot be negative
     */
    public void set(int index)
    {
        checkArgument(index >= 0, "Local variable index cannot be zero");
        this.index = index;
    }

    /**
     * Returns the local variable index for this output, or -1 if it has not been set yet.
     * 
     * @return the index
     */
    public int get()
    {
        return this.index;
    }
}
