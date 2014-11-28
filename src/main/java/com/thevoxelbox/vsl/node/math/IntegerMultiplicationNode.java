package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for performing a multiplication operation between two integers.
 */
public class IntegerMultiplicationNode extends Node implements Opcodes
{

    private static final long serialVersionUID = -6656878446109197638L;

    /**
     * Creates a new {@link IntegerMultiplicationNode}.
     */
    public IntegerMultiplicationNode()
    {
        super("Integer Multiplication", "math");
        addInput("a", Type.INTEGER, true, null);
        addInput("b", Type.INTEGER, true, null);
        addOutput("result", Type.INTEGER, this);
    }

    /**
     * A node for performing a modulo operation between two integers.
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
