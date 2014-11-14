package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.node.Node;

public class BooleanXorNode extends Node implements Opcodes
{

    public BooleanXorNode()
    {
        super("Boolean Xor", "math");
        addInput("a", IOType.BOOLEAN, true, null);
        addInput("b", IOType.BOOLEAN, true, null);
        addOutput("result", IOType.BOOLEAN, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /* 
        ILOAD a
        ILOAD b
        IXOR
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IXOR);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
