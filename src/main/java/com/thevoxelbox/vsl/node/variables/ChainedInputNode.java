package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

public class ChainedInputNode extends VariableGetNode
{
    /**
     * 
     */
    private static final long serialVersionUID = -7865463712675985852L;

    public ChainedInputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
