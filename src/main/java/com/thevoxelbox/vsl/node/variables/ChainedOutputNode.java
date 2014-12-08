package com.thevoxelbox.vsl.node.variables;

import com.thevoxelbox.vsl.type.Type;

/**
 * A node for sending an output to the next chained node graph.
 */
public class ChainedOutputNode extends VariableSetNode
{

    private static final long serialVersionUID = -3772853620892152285L;

    /**
     * Creates a new {@link ChainedOutputNode}.
     * 
     * @param name the name of the output, if empty or null will set name input to required
     * @param type the output type, cannot be null
     */
    public ChainedOutputNode(String name, Type type)
    {
        super("__CHAINEDIO__" + name, type);
    }
}
