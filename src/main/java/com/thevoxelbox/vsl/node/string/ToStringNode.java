package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for converting an object to it's {@link String} representation.
 */
public class ToStringNode extends Node implements Opcodes
{

    private static final long serialVersionUID = -8752322858200554863L;

    /**
     * Creates a new {@link ToStringNode}.
     */
    public ToStringNode()
    {
        super("toString", "string");
        addInput("value", Type.OBJECT, true, null);
        addOutput("result", Type.STRING, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        ALOAD 1: value
        INVOKESTATIC String.valueOf (Object) : String
        ASTORE i
         */
        int value_i = getInput("value").getSource().get();
        mv.visitVarInsn(ALOAD, value_i);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
        mv.visitVarInsn(ASTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
