package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.type.Type;

public class SubStringNode extends Node
{

    public SubStringNode()
    {
        super("Substring", "string");
        addInput("string", Type.STRING, true, null);
        addInput("start", Type.INTEGER, false, 0);
        addInput("end", Type.INTEGER, false, 0);
        addOutput("result", Type.STRING, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        int start_i;
        if (getInput("start").getSource() == null)
        {
            start_i = localsIndex;
            localsIndex = getInput("start").insertDefaultValue(mv, localsIndex);
        } else
        {
            start_i = getInput("start").getSource().get();
        }
        int end_i;
        if (getInput("end").getSource() == null)
        {
            end_i = localsIndex;
            localsIndex = getInput("end").insertDefaultValue(mv, localsIndex);
        } else
        {
            end_i = getInput("end").getSource().get();
        }
        int string_i = getInput("string").getSource().get();
        mv.visitVarInsn(Opcodes.ALOAD, string_i);
        mv.visitVarInsn(Opcodes.ILOAD, start_i);
        mv.visitVarInsn(Opcodes.ILOAD, end_i);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "substring", "(II)Ljava/lang/String;", false);
        mv.visitVarInsn(Opcodes.ASTORE, localsIndex);
        setOutput("result", localsIndex);
        return localsIndex + 1;
    }

}
