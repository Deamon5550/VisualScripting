package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;
import com.thevoxelbox.vsl.type.TypeDepth;

/**
* A node for creating a new {@link String} array.
*/
public class StringArrayValueNode extends Node implements Opcodes
{

    private static final long serialVersionUID = 1242627948755821329L;
    /**
     * The value to be inserted.
     */
    String[] value;
    
   /**
    * Creates a new {@link StringArrayValueNode}.
    * 
    * @param value the array values
    */
    public StringArrayValueNode(String... value)
    {
        super("String Array Value", "variables");
        this.value = value;
        addOutput("value", Type.getType("STRING", TypeDepth.ARRAY), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
         
         BIPUSH value.length
         ANEWARRAY java/lang/String
         
         for-each string in array:
             DUP
             BIPUSH index
             LDC string
             AASTORE
         
         ASTORE value
         
         */

        mv.visitIntInsn(BIPUSH, value.length);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
        for (int i = 0; i < value.length; i++)
        {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitLdcInsn(value[i]);
            mv.visitInsn(AASTORE);
        }
        mv.visitVarInsn(ASTORE, localsIndex++);
        setOutput("value", localsIndex - 1);
        return localsIndex;
    }
}
