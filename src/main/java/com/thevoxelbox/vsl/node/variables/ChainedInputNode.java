package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

public class ChainedInputNode extends VariableGetNode
{
    public ChainedInputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
