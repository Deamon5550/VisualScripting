package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class ToStringNode extends Node implements Opcodes
{

    public ToStringNode()
    {
        super("toString", "string");
        addInput("value", Object.class, true, null);
        addOutput("result", String.class, this);
    }

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
