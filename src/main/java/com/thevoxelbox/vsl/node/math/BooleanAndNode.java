package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class BooleanAndNode extends Node implements Opcodes
{

    /**
     * 
     */
    private static final long serialVersionUID = -8319895620036184541L;

    public BooleanAndNode()
    {
        super("Boolean And", "math");
        addInput("a", Type.BOOLEAN, true, null);
        addInput("b", Type.BOOLEAN, true, null);
        addOutput("result", Type.BOOLEAN, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /* short circuiting
        ILOAD a
        IFEQ l1
        ILOAD b
        IFEQ l1
        ICONST_1
        GOTO l2
        l1:
        ICONST_0
        l2:
        ISTORE result
        
        non short circuiting since it shouldn't be needed
        ILOAD a
        ILOAD b
        IAND
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IAND);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
