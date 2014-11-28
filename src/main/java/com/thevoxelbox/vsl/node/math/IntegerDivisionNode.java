package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for performing a division operation between two integers.
 */
public class IntegerDivisionNode extends Node implements Opcodes
{

    private static final long serialVersionUID = 2385059426226487134L;

    /**
     * Creates a new {@link IntegerDivisionNode}.
     */
    public IntegerDivisionNode()
    {
        super("Integer Multiplication", "math");
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
