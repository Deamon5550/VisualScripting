package com.thevoxelbox.vsl.node.math;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class IntegerSubtractionNode extends Node implements Opcodes
{

    /**
     * 
     */
    private static final long serialVersionUID = 938585496057453156L;

    public IntegerSubtractionNode()
    {
        super("Integer Division", "math");
        addInput("a", Type.INTEGER, true, null);
        addInput("b", Type.INTEGER, true, null);
        addOutput("result", Type.INTEGER, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        ILOAD a
        ILOAD b
        IDIV
        ISTORE result
         */
        int a_i = getInput("a").getSource().get();
        int b_i = getInput("b").getSource().get();
        mv.visitVarInsn(ILOAD, a_i);
        mv.visitVarInsn(ILOAD, b_i);
        mv.visitInsn(IDIV);
        mv.visitVarInsn(ISTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
