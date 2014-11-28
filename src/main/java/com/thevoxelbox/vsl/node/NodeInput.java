package com.thevoxelbox.vsl.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import java.io.Serializable;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.type.Type;

/**
 * An input to a {@link Node}.
 */
public class NodeInput implements Serializable
{
    private static final long serialVersionUID = 4321453232297119565L;
    /**
     * The input name.
     */
    private final String name;
    /**
     * The input {@link Type}.
     */
    private final Type type;
    /**
     * The output attached to this input.
     */
    private NodeOutput out = null;
    /**
     * Whether this input is required or may be fulfilled by the default value.
     */
    private boolean required;
    /**
     * The default value for use in the event of this input not being filled.
     */
    private Object defaultValue;

    /**
     * Creates a new {@link NodeInput}.
     * 
     * @param name the input's name, cannot be null or empty
     * @param type the input's type, cannot be null
     */
    public NodeInput(String name, Type type)
    {
        this(name, type, true, null);
    }

    /**
     * Creates a new {@link NodeInput}.
     * 
     * @param name the input's name, cannot be null or empty
     * @param type the input's type, cannot be null
     * @param isRequired whether this input is required
     * @param defaultValue the default value for the event of this input not being fulfilled, will be ignored if the input is marked as required
     */
    public NodeInput(String name, Type type, boolean isRequired, Object defaultValue)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(type, "Type cannot be null!");
        this.name = name;
        this.type = type;
        this.required = isRequired;
        this.defaultValue = isRequired ? null : defaultValue;
    }

    /**
     * Sets the source for this input.
     * 
     * @param output the source, cannot be null
     */
    public void setSource(NodeOutput output)
    {
        checkNotNull(output, "Source cannot be null!");
        this.out = output;
    }

    /**
     * Returns the source for this input. May be null if no source has been set.
     * 
     * @return the source
     */
    public NodeOutput getSource()
    {
        return this.out;
    }

    /**
     * Returns the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the type.
     * 
     * @return the type
     */
    public Type getType()
    {
        return this.type;
    }

    /**
     * Returns whether this input is a required input.
     * 
     * @return is required?
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * Sets if this input is required.
     * 
     * @param required is required
     */
    public void setRequired(boolean required)
    {
        this.required = required;
    }

    /**
     * Returns the default value for this input, may be null.
     * 
     * @return the default value
     */
    public Object getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * Sets the default value, will be ignored if this input is set as required.
     * 
     * @param defaultValue the new default value
     */
    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = this.required ? null : defaultValue;
    }

    /**
     * Inserts the default value to the given {@link MethodVisitor}.
     * 
     * @param mv the method visitor to append to, cannot be null
     * @param localIndex the local variable index to start at, cannot be negative
     * @return the new local variable index after the insertion
     * @throws GraphCompilationException if an error occurs
     */
    public int insertDefaultValue(MethodVisitor mv, int localIndex) throws GraphCompilationException
    {
        checkNotNull(mv, "MethodVisitor cannot be null");
        checkArgument(localIndex >= 0, "Local variable index cannot be negative");
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
                mv.visitVarInsn(Opcodes.ISTORE, localIndex);
                localIndex++;
            } else if (value <= Short.MAX_VALUE && value >= Short.MIN_VALUE)
            {
                mv.visitIntInsn(Opcodes.SIPUSH, (short) value);
                mv.visitVarInsn(Opcodes.ISTORE, localIndex);
                localIndex++;
            } else if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE)
            {
                mv.visitLdcInsn(new Integer((int) value));
                mv.visitVarInsn(Opcodes.ISTORE, localIndex);
                localIndex++;
            } else
            {
                mv.visitLdcInsn(new Long(value));
                mv.visitVarInsn(Opcodes.LSTORE, localIndex);
                localIndex += 2;
            }
        } else if (this.type.equals(Type.STRING))
        {
            if (this.defaultValue == null)
            {
                throw new GraphCompilationException("Default value was null for a type not supporting null");
            }
            mv.visitLdcInsn(defaultValue.toString());
            mv.visitVarInsn(Opcodes.ASTORE, localIndex);
            localIndex += 1;
        } else
        {
            throw new GraphCompilationException("Unknown type " + this.type.getName() + " for default value in " + this.name); //TODO identification name is fairly useless for debugging...
        }
        return localIndex;
    }
}
