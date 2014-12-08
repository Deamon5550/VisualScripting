package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for finding a regular expression pattern within a string.
 */
public class StringFindNode extends Node implements Opcodes
{

    private static final long serialVersionUID = -6054613299764204990L;

    /**
     * Creates a new {@link StringFindNode}.
     */
    public StringFindNode()
    {
        super("String find", "string");
        addInput("string", Type.STRING, true, null);
        addInput("token", Type.STRING, false, 0);
        addOutput("index", Type.INTEGER, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        /*
        String a = "";
        String pattern = "";
        Matcher p = Pattern.compile(pattern).matcher(a);
        int i;
        if(p.find())
        {
            i = p.start();
        }
        else
        {
            i = -1;
        }
         */
        int string_i = getInput("string").getSource().get();
        int token_i = getInput("token").getSource().get();
        int pattern_i = localsIndex++;
        mv.visitVarInsn(ALOAD, token_i);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/regex/Pattern", "compile", "(Ljava/lang/String;)Ljava/util/regex/Pattern;", false);
        mv.visitVarInsn(ALOAD, string_i);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", false);
        mv.visitVarInsn(ASTORE, pattern_i);
        mv.visitVarInsn(ALOAD, pattern_i);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "find", "()Z", false);
        Label l1 = new Label();
        mv.visitJumpInsn(IFEQ, l1);
        mv.visitVarInsn(ALOAD, pattern_i);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "start", "()I", false);
        mv.visitVarInsn(ISTORE, localsIndex);
        Label l2 = new Label();
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(ICONST_M1);
        mv.visitVarInsn(ISTORE, localsIndex);
        mv.visitLabel(l2);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }
}
