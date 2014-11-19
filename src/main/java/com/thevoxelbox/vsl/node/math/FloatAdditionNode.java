package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class FloatAdditionNode extends Node implements Opcodes
{

    public FloatAdditionNode()
    {
        super("Float Addition", "math");
        addInput("a", double.class, true, null);
        addInput("b", double.class, true, null);
        addOutput("result", double.class, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        FLOAD a
        FLOAD b
        FADD
        FSTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(FLOAD, a_i);
        mv.visitVarInsn(FLOAD, b_i);
        mv.visitInsn(FADD);
        mv.visitVarInsn(FSTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
