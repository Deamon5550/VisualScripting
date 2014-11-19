package com.thevoxelbox.vsl.classloader;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.IChainableNodeGraph;
import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.ExecutableNode;

public class NodeGraphCompiler implements IGraphCompiler, Opcodes
{

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends IRunnableGraph> compile(ASMClassLoader cl, INodeGraph graph) throws NullPointerException, GraphCompilationException
    {
        if (graph.getStart() == null)
        {
            throw new NullPointerException("Start node is null");
        }
        while (cl.isClassLoaded("com.thevoxelbox.custom." + graph.getName() + graph.getIncrement()))
        {
            graph.incrementName();
        }
        return (Class<? extends IRunnableGraph>) cl.defineClass("com.thevoxelbox.custom." + graph.getName() + graph.getIncrement(),
                createClass(graph));
    }

    private byte[] createClass(INodeGraph graph) throws GraphCompilationException
    {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), null, "java/lang/Object",
                new String[] { "com/thevoxelbox/vsl/api/IRunnableGraph" });

        cw.visitSource(graph.getName() + graph.getIncrement() + ".java", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitLdcInsn(graph.getName());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "run", "(Lcom/thevoxelbox/vsl/api/IVariableHolder;)V", null, null);
            mv.visitCode();

            int index = 2;
            ExecutableNode current = graph.getStart();
            while (current != null)
            {
                index = current.insert(mv, index);
                current = current.getNextNode();
            }

            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

}
