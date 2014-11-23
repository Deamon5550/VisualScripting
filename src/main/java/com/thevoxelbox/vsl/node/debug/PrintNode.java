package com.thevoxelbox.vsl.node.debug;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.node.ExecutableNode;
import com.thevoxelbox.vsl.type.Type;

public class PrintNode extends ExecutableNode
{
    public PrintNode()
    {
        super("Print String", "debug");
        addInput("msg", Type.STRING, true, null);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        int msg_i = getInput("msg").getSource().get();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ALOAD, msg_i);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
        return localsIndex;
    }
}
