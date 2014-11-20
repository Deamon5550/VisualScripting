package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;

public class VariableGetNode extends Node
{

    private Class<?> type;

    public VariableGetNode(Class<?> type)
    {
        super("Variable Get", "variables");
        addOutput("value", type, this);
        addInput("name", String.class, true, null);
        this.type = type;
    }

    public VariableGetNode(String name, Class<?> type)
    {
        super("Variable Get", "variables");
        addOutput("value", type, this);
        addInput("name", String.class, false, name);
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
        mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(type));
        mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }
}
