package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;

public class VariableGetNode extends Node
{

    public VariableGetNode()
    {
        super("Variable Get", "variables");
        addOutput("value", IOType.WILD, this);
        addInput("name", IOType.STRING, true, null);
    }

    public VariableGetNode(String name)
    {
        super("Variable Get", "variables");
        addOutput("value", IOType.WILD, this);
        addInput("name", IOType.STRING, false, name);
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
        mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
        setOutput("value", localsIndex);
        return localsIndex + 1;
    }
}
