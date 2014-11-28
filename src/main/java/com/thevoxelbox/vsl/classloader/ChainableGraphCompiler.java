package com.thevoxelbox.vsl.classloader;

import static com.google.common.base.Preconditions.checkNotNull;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.IChainableNodeGraph;
import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.ExecutableNode;

/**
 * A graph compiler for {@link IChainableNodeGraph}s.
 */
public class ChainableGraphCompiler implements IGraphCompiler, Opcodes
{

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends IRunnableGraph> compile(ASMClassLoader cl, INodeGraph graph) throws NullPointerException, GraphCompilationException
    {
        checkNotNull(cl, "Classloader cannot be null");
        checkNotNull(graph, "Graph cannot be null");
        checkNotNull(graph.getStart(), "Graph starting point cannot be null");
        if (!(graph instanceof IChainableNodeGraph))
        {
            throw new GraphCompilationException("Graph type is incorrect type for compiler!");
        }
        IChainableNodeGraph cgraph = (IChainableNodeGraph) graph;
        while (cl.isClassLoaded("com.thevoxelbox.custom." + cgraph.getName() + cgraph.getIncrement()))
        {
            cgraph.incrementName();
        }
        return (Class<? extends IRunnableGraph>) cl.defineClass("com.thevoxelbox.custom." + cgraph.getName() + cgraph.getIncrement(),
                createClass(cgraph));
    }

    /**
     * Creates a new class from the given node graph and returns it as a byte array.
     * 
     * @param graph the graph to compile, cannot be null
     * @return the new class
     * @throws GraphCompilationException if an error occurs while compiling the graph
     */
    public byte[] createClass(IChainableNodeGraph graph) throws GraphCompilationException
    {
        checkNotNull(graph, "Graph cannot be null");
        checkNotNull(graph.getStart(), "Graph starting point cannot be null");

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;
        FieldVisitor fv;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), null, "java/lang/Object",
                new String[] { "com/thevoxelbox/vsl/api/IChainedRunnableGraph" });

        cw.visitSource(graph.getName() + graph.getIncrement() + ".java", null);

        {
            fv = cw.visitField(0, "next", "Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(PUTFIELD, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), "next",
                    "Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;");
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
            mv = cw.visitMethod(ACC_PUBLIC, "chain", "(Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), "next",
                    "Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "run", "(Lcom/thevoxelbox/vsl/api/IVariableHolder;)V", null, null);
            mv.visitCode();

            int index = 3;
            ExecutableNode current = graph.getStart();
            while (current != null)
            {
                index = current.insert(mv, index);
                current = current.getNextNode();
                if (current == null)
                {

                    /* Insert call to next graph in chain:
                     * ===================================
                     * 
                        ALOAD 0: this
                        GETFIELD this.next : IChainedRunnableGraph
                        IFNULL L3
                        ALOAD 0: this
                        GETFIELD this.next : IChainedRunnableGraph
                        ALOAD 1: vars
                        INVOKEINTERFACE IChainedRunnableGraph.run (IVariableHolder) : void
                       L3
                     */

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), "next",
                            "Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;");
                    Label l3 = new Label();
                    mv.visitJumpInsn(IFNULL, l3);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "com/thevoxelbox/custom/" + graph.getName() + graph.getIncrement(), "next",
                            "Lcom/thevoxelbox/vsl/api/IChainedRunnableGraph;");
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEINTERFACE, "com/thevoxelbox/vsl/api/IChainedRunnableGraph", "run",
                            "(Lcom/thevoxelbox/vsl/api/IVariableHolder;)V", true);
                    mv.visitLabel(l3);
                }
            }

            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

}
