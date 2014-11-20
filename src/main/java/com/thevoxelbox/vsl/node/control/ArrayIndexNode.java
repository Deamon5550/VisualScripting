package com.thevoxelbox.vsl.node.control;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;

public class ArrayIndexNode extends Node implements Opcodes
{

    public ArrayIndexNode(Class<?> type, long index)
    {
        super("Array Index", "control");
        addInput("array", type, true, null);
        addInput("index", int.class, index >= 0 ? false : true, index >= 0 ? index : null);
        addOutput("value", type, this);
    }

    public ArrayIndexNode(Class<?> type)
    {
        this(type, -1);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        int array = getInput("array").getSource().get();
        int index;
        if (getInput("index").getSource() == null)
        {
            index = localsIndex;
            localsIndex = getInput("index").insertDefaultValue(mv, localsIndex);
        } else
        {
            index = getInput("index").getSource().get();
        }
        mv.visitVarInsn(ALOAD, array);
        mv.visitIntInsn(ILOAD, index);
        mv.visitInsn(AALOAD);
        mv.visitVarInsn(ASTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }

    public void t()
    {
        String[] s = null;

        String v = s[0];
    }

}
