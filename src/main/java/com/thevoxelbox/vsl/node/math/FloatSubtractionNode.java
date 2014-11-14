package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.node.Node;

public class FloatSubtractionNode extends Node implements Opcodes
{

    public FloatSubtractionNode()
    {
        super("Float Division", "math");
        addInput("a", IOType.FLOAT, true, null);
        addInput("b", IOType.FLOAT, true, null);
        addOutput("result", IOType.FLOAT, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        FLOAD a
        FLOAD b
        FDIV
        FSTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(FLOAD, a_i);
        mv.visitVarInsn(FLOAD, b_i);
        mv.visitInsn(FDIV);
        mv.visitVarInsn(FSTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
