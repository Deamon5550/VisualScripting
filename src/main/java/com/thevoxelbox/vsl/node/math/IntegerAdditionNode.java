package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for performing an addition operation between two integers.
 */
public class IntegerAdditionNode extends Node implements Opcodes
{

    private static final long serialVersionUID = -748709622936011183L;

    /**
     * Creates a new {@link IntegerAdditionNode}.
     */
    public IntegerAdditionNode()
    {
        super("Integer Addition", "math");
        addInput("a", Type.INTEGER, true, null);
        addInput("b", Type.INTEGER, true, null);
        addOutput("result", Type.INTEGER, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        ILOAD a
        ILOAD b
        IADD
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IADD);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
