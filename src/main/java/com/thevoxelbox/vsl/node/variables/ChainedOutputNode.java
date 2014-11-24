package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

public class ChainedOutputNode extends VariableSetNode
{
    /**
     * 
     */
    private static final long serialVersionUID = -3772853620892152285L;

    public ChainedOutputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
