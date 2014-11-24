package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class StringValueNode extends Node
{

    /**
     * 
     */
    private static final long serialVersionUID = -5522179292881089093L;
    String value;

    public StringValueNode(String value)
    {
        super("String Value", "variables");
        this.value = value;
        addOutput("value", Type.STRING, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        mv.visitLdcInsn(value.toString());
        mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }
}
