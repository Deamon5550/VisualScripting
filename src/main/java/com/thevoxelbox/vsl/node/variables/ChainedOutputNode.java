package com.thevoxelbox.vsl.node.variables;

public class ChainedOutputNode extends VariableSetNode
{
    public ChainedOutputNode(String name, Class<?> type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
