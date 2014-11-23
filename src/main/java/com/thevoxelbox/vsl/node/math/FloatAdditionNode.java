package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class FloatAdditionNode extends Node implements Opcodes
{

    public FloatAdditionNode()
    {
        super("Float Addition", "math");
        addInput("a", Type.FLOAT, true, null);
        addInput("b", Type.FLOAT, true, null);
        addOutput("result", Type.FLOAT, this);
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
        mv.visitVarInsn(DLOAD, a_i);
        mv.visitVarInsn(DLOAD, b_i);
        mv.visitInsn(DADD);
        mv.visitVarInsn(DSTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
