package com.thevoxelbox.vsl.node;

import java.io.Serializable;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.type.Type;

public class NodeInput implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4321453232297119565L;
    private final String name;
    private final Type type;
    private NodeOutput out = null;
    private boolean required;
    private Object defaultValue;

    public NodeInput(String n, Type t)
    {
        this(n, t, true, null);
    }

    public NodeInput(String n, Type t, boolean r, Object d)
    {
        this.name = n;
        this.type = t;
        this.required = r;
        this.defaultValue = d;
    }

    public void setSource(NodeOutput o)
    {
        this.out = o;
    }

    public NodeOutput getSource()
    {
        return this.out;
    }

    public String getName()
    {
        return this.name;
    }

    public Type getType()
    {
        return this.type;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public int insertDefaultValue(MethodVisitor mv, int init) throws GraphCompilationException
    {
        if (this.type.equals(Type.INTEGER))
        {
            if (this.defaultValue == null)
            {
                throw new GraphCompilationException("Default value was null for a type not supporting null");
            }
            long value = (long) this.defaultValue;
            if (value <= Byte.MAX_VALUE && value >= Byte.MIN_VALUE)
            {
                mv.visitIntInsn(Opcodes.BIPUSH, (byte) value);
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            } else if (value <= Short.MAX_VALUE && value >= Short.MIN_VALUE)
            {
                mv.visitIntInsn(Opcodes.SIPUSH, (short) value);
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            } else if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE)
            {
                mv.visitLdcInsn(new Integer((int) value));
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            } else
            {
                mv.visitLdcInsn(new Long(value));
                mv.visitVarInsn(Opcodes.LSTORE, init);
                init += 2;
            }
        } else if (this.type.equals(Type.STRING))
        {
            if (this.defaultValue == null)
            {
                throw new GraphCompilationException("Default value was null for a type not supporting null");
            }
            mv.visitLdcInsn(defaultValue.toString());
            mv.visitVarInsn(Opcodes.ASTORE, init);
            init += 1;
        } else
        {
            throw new GraphCompilationException("Unknown type " + this.type.getName() + " for default value in " + this.name); //TODO identification name is fairly useless for debugging...
        }
        return init;
    }
}
