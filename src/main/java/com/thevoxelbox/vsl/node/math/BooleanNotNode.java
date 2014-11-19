package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class BooleanNotNode extends Node implements Opcodes
{

    public BooleanNotNode()
    {
        super("Boolean Not", "math");
        addInput("a", boolean.class, true, null);
        addOutput("result", boolean.class, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        ILOAD a
        IFEQ l1
        ICONST_0
        GOTO l2
        l1:
        ICONST_1
        l2:
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        Label l1 = new Label();
        mv.visitJumpInsn(IFEQ, l1);
        mv.visitInsn(ICONST_1);
        Label l2 = new Label();
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(ICONST_0);
        mv.visitLabel(l2);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
