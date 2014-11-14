package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.node.Node;

public class IntegerModuloNode extends Node implements Opcodes
{

    public IntegerModuloNode()
    {
        super("Integer Modulo", "math");
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
        IREM
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IREM);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
