package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class IntegerMultiplicationNode extends Node implements Opcodes
{

    public IntegerMultiplicationNode()
    {
        super("Integer Multiplication", "math");
        addInput("a", int.class, true, null);
        addInput("b", int.class, true, null);
        addOutput("result", int.class, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        ILOAD a
        ILOAD b
        IMUL
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IMUL);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
