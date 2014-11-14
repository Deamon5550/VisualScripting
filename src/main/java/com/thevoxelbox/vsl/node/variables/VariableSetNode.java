package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.ExecutableNode;

public class VariableSetNode extends ExecutableNode
{

    public VariableSetNode()
    {
        super("Variable Set", "variables");
        addInput("value", IOType.WILD, true, null);
        addInput("name", IOType.STRING, true, null);
    }

    public VariableSetNode(String var)
    {
        super("Variable Set", "variables");
        addInput("value", IOType.WILD, true, null);
        addInput("name", IOType.STRING, false, var);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        /*
        ALOAD 1
        ALOAD name
        ALOAD value
        INVOKEINTERFACE IVariableHolder.set (String, Object) : void
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
        int obj_i = getInput("value").getSource().get();
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, name_i);
        mv.visitVarInsn(Opcodes.ALOAD, obj_i);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/thevoxelbox/vsl/api/IVariableHolder", "set", "(Ljava/lang/String;Ljava/lang/Object;)V", true);
        return localsIndex + 1;
    }
}
