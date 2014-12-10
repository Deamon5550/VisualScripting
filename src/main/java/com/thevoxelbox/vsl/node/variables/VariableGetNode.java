package com.thevoxelbox.vsl.node.variables;

import static com.google.common.base.Preconditions.checkNotNull;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for fetching a value from the runtime variables.
 */
public class VariableGetNode extends Node
{

    private static final long serialVersionUID = -5189214309378156617L;
    /**
     * The type of the variable being fetched.
     */
    private Type type;

    /**
     * Creates a new {@link VariableGetNode}.
     * 
     * @param type the type of the variable being fetched, cannot be null
     */
    public VariableGetNode(Type type)
    {
        super("Variable Get", "variables");
        checkNotNull(type, "Type cannot be null");
        addOutput("value", type, this);
        addInput("name", Type.STRING, true, null);
        this.type = type;
    }

    /**
     * Creates a new {@link VariableGetNode}.
     * 
     * @param name the name of the variable, if empty or null will set the name input to required
     * @param type the type of the variable being fetched, cannot be null
     */
    public VariableGetNode(String name, Type type)
    {
        super("Variable Get", "variables");
        checkNotNull(type, "Type cannot be null");
        if (name == null || name.isEmpty())
        {
            addInput("name", Type.STRING, true, null);
        } else
        {
            addInput("name", Type.STRING, false, name);
        }
        addOutput("value", type, this);
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
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
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/thevoxelbox/vsl/api/IVariableHolder", "get", "(Ljava/lang/String;)Lcom/google/common/base/Optional;", true);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/google/common/base/Optional", "get", "()Ljava/lang/Object;", false);
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
    
    public void run(IVariableHolder vars)
    {
        vars.get("t").get();
    }
}
