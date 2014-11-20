package com.thevoxelbox.vsl.node.variables;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class StringArrayValueNode extends Node implements Opcodes
{

    String[] value;

    public StringArrayValueNode(String... value)
    {
        super("String Array Value", "variables");
        this.value = value;
        addOutput("value", String.class, this);
    }

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
        for(int i = 0; i < value.length; i++)
        {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitLdcInsn(value[i]);
            mv.visitInsn(AASTORE);
        }
        mv.visitVarInsn(ASTORE, localsIndex++);
        setOutput("value", localsIndex-1);
        return localsIndex;
    }
}
