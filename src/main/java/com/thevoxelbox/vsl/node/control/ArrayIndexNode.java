package com.thevoxelbox.vsl.node.control;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;
import com.thevoxelbox.vsl.type.TypeDepth;

public class ArrayIndexNode extends Node implements Opcodes
{
    private static final long serialVersionUID = -5544598424756745612L;

    public ArrayIndexNode(Type type, long index) throws GraphCompilationException
    {
        super("Array Index", "control");
        if (type.getDepth() != TypeDepth.ARRAY)
        {
            throw new GraphCompilationException("Input type for ArrayIndexNode is not an array type");
        }
        addInput("array", type, true, null);
        addInput("index", Type.INTEGER, index >= 0 ? false : true, index >= 0 ? index : null);
        addOutput("value", type, this);
    }

    public ArrayIndexNode(Type type) throws GraphCompilationException
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
}
