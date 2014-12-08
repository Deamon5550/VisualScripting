package com.thevoxelbox.vsl.node.variables;

import static com.google.common.base.Preconditions.checkNotNull;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for inserting a static String value.
 */
public class StringValueNode extends Node
{

    private static final long serialVersionUID = -5522179292881089093L;
    /**
     * The String value to insert.
     */
    String value;

    /**
     * Creates a new {@link StringValueNode}.
     * 
     * @param value the string value, cannot be null
     */
    public StringValueNode(String value)
    {
        super("String Value", "variables");
        checkNotNull(value, "Value cannot be null");
        this.value = value;
        addOutput("value", Type.STRING, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        mv.visitLdcInsn(value.toString());
        mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }
}
