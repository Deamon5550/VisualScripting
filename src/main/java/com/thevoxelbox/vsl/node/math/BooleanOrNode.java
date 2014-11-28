package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for performing a boolean OR operation.
 */
public class BooleanOrNode extends Node implements Opcodes
{

    private static final long serialVersionUID = 1784526846245132087L;

    /**
     * Creates a new {@link BooleanOrNode}.
     */
    public BooleanOrNode()
    {
        super("Boolean Or", "math");
        addInput("a", Type.BOOLEAN, true, null);
        addInput("b", Type.BOOLEAN, true, null);
        addOutput("result", Type.BOOLEAN, this);
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
        IOR
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IOR);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
