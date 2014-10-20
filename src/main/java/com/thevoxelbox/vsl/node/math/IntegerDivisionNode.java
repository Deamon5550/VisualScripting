package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.node.Node;

public class IntegerDivisionNode extends Node implements Opcodes
{

    public IntegerDivisionNode()
    {
        super("Integer Multiplication", "math");
        addInput("a", IOType.INTEGER, true, null);
        addInput("b", IOType.INTEGER, true, null);
        addOutput("result", IOType.INTEGER, this);
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
        return localsIndex+1;
    }

}
