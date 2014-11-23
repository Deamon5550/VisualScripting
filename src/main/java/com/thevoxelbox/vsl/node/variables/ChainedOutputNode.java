package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

public class ChainedOutputNode extends VariableSetNode
{
    public ChainedOutputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
