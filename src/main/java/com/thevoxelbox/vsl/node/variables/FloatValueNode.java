package com.thevoxelbox.vsl.node.variables;

import static com.google.common.base.Preconditions.checkNotNull;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
* A node for inserting a static double value.
*/
public class FloatValueNode extends Node
{

    private static final long serialVersionUID = -5522179292881089093L;
    /**
     * The double value to insert.
     */
    double value;

    /**
     * Creates a new {@link FloatValueNode}.
     * 
     * @param value the double value, cannot be null
     */
    public FloatValueNode(double value)
    {
        super("String Value", "variables");
        checkNotNull(value, "Value cannot be null");
        this.value = value;
        addOutput("value", Type.FLOAT, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        mv.visitLdcInsn(new Double(value));
        mv.visitVarInsn(Opcodes.DSTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }
}
