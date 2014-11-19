package com.thevoxelbox.vsl.node.variables;

public class ChainedInputNode extends VariableGetNode
{
    public ChainedInputNode(String name, Class<?> type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
