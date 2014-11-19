package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.Node;

public class StringConcatenationNode extends Node implements Opcodes
{

    public StringConcatenationNode()
    {
        super("String Concatenation", "string");
        addInput("first", String.class, true, null);
        addInput("second", String.class, true, null);
        addOutput("result", String.class, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        /*
        NEW StringBuilder
        DUP
        ALOAD 2: a
        INVOKESTATIC String.valueOf (Object) : String
        INVOKESPECIAL StringBuilder.<init> (String) : void
        ALOAD 3: b
        INVOKEVIRTUAL StringBuilder.append (String) : StringBuilder
        INVOKEVIRTUAL StringBuilder.toString () : String
        ASTORE 4
         */
        int first_i = getInput("first").getSource().get();
        int second_i = getInput("second").getSource().get();
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, first_i);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitVarInsn(ALOAD, second_i);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitVarInsn(ASTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
