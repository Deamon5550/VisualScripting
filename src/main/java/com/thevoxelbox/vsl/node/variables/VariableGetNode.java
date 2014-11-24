package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class VariableGetNode extends Node
{

    /**
     * 
     */
    private static final long serialVersionUID = -5189214309378156617L;
    private Type type;

    public VariableGetNode(Type type)
    {
        super("Variable Get", "variables");
        addOutput("value", type, this);
        addInput("name", Type.STRING, true, null);
        this.type = type;
    }

    public VariableGetNode(String name, Type type)
    {
        super("Variable Get", "variables");
        addOutput("value", type, this);
        addInput("name", Type.STRING, false, name);
        this.type = type;
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        /*
        ALOAD 1
        ALOAD name
        INVOKEINTERFACE com/thevoxelbox/vsl/api/IVariableHolder.get (Ljava/lang/String;)Ljava/lang/Object;
        ASTORE i
         */
        int name_i;
        if (getInput("name").getSource() == null)
        {
            name_i = localsIndex;
            localsIndex = getInput("name").insertDefaultValue(mv, localsIndex);
        } else
        {
            name_i = getInput("name").getSource().get();
        }
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, name_i);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/thevoxelbox/vsl/api/IVariableHolder", "get", "(Ljava/lang/String;)Ljava/lang/Object;", true);
        if (type.equals(Type.INTEGER))
        {
            mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitVarInsn(Opcodes.ISTORE, localsIndex);
            setOutput("value", localsIndex);
            localsIndex++;
        } else if (type.equals(Type.FLOAT))
        {
            mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            mv.visitVarInsn(Opcodes.DSTORE, localsIndex);
            setOutput("value", localsIndex);
            localsIndex += 2;
        } else if (type.equals(Type.BOOLEAN))
        {
            mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            mv.visitVarInsn(Opcodes.ISTORE, localsIndex);
            setOutput("value", localsIndex);
            localsIndex++;
        } else
        {
            mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
            mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
            setOutput("value", localsIndex);
            localsIndex++;
        }
        return localsIndex;
    }
}
