package com.thevoxelbox.vsl.node;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.error.GraphCompilationException;

public class NodeInput
{
    private final String name;
    private final IOType type;
    private NodeOutput out = null;
    private boolean chained = false;
    private boolean required;
    private Object defaultValue;
    
    public NodeInput(String n, IOType t)
    {
        this(n, t, true, null);
    }
    
    public NodeInput(String n, IOType t, boolean r, Object d)
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
    
    public IOType getType()
    {
        return this.type;
    }

    public boolean isChained()
    {
        return chained;
    }

    public void setChained(boolean chained)
    {
        this.chained = chained;
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
        switch(this.type)
        {
        case INTEGER:
            long value = (long) this.defaultValue;
            if (value <= Byte.MAX_VALUE && value >= Byte.MIN_VALUE)
            {
                mv.visitIntInsn(Opcodes.BIPUSH, (byte) value);
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            }
            else if (value <= Short.MAX_VALUE && value >= Short.MIN_VALUE)
            {
                mv.visitIntInsn(Opcodes.SIPUSH, (short) value);
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            }
            else if(value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE)
            {
                mv.visitLdcInsn(new Integer((int) value));
                mv.visitVarInsn(Opcodes.ISTORE, init);
                init++;
            }
            else
            {
                mv.visitLdcInsn(new Long(value));
                mv.visitVarInsn(Opcodes.LSTORE, init);
                init += 2;
            }
            break;
        case FLOAT:
            //TODO
            break;
        case VOID:
            //uhhh...
            break;
        case OBJECT:
            //TODO
            break;
        case STRING:
            mv.visitLdcInsn(defaultValue.toString());
            mv.visitVarInsn(Opcodes.ASTORE, init);
            init+=1;
            break;
        default:
            throw new GraphCompilationException("Unknown type for default value in " + this.name); //TODO identification name is fairly useless...
        }
        return init;
    }
}
