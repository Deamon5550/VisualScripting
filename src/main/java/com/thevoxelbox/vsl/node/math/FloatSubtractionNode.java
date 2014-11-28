package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for performing a subtraction operation between two floating point numbers.
 */
public class FloatSubtractionNode extends Node implements Opcodes
{

    private static final long serialVersionUID = 5008316400875167971L;

    /**
     * Creates a new {@link FloatSubtractionNode}.
     */
    public FloatSubtractionNode()
    {
        super("Float Division", "math");
        addInput("a", Type.FLOAT, true, null);
        addInput("b", Type.FLOAT, true, null);
        addOutput("result", Type.FLOAT, this);
    }

    /**
     * {@inheritDoc}
     */
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
        mv.visitVarInsn(DLOAD, a_i);
        mv.visitVarInsn(DLOAD, b_i);
        mv.visitInsn(DDIV);
        mv.visitVarInsn(DSTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 2;
    }

}
