package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

/**
 * A node for receiving an input from the preceding chained node graph.
 */
public class ChainedInputNode extends VariableGetNode
{

    private static final long serialVersionUID = -7865463712675985852L;

    /**
     * Creates a new {@link ChainedInputNode}.
     * 
     * @param name the name of the input, if empty or null will set name input to required
     * @param type the output type, cannot be null
     */
    public ChainedInputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
