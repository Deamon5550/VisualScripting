package com.thevoxelbox.vsl.node;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.error.GraphCompilationException;

public class NodeGraph implements INodeGraph, Opcodes
{
    
    private String name;
    private int c = 0;
    private ExecutableNode start;
    
    private Class<? extends IRunnableGraph> compiled = null;
    
    public NodeGraph(String name)
    {
        this.name = name.replace(" ", "");
    }

    @Override
    public void setStartNode(ExecutableNode start)
    {
        this.start = start;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends IRunnableGraph> compile(ASMClassLoader cl) throws NullPointerException, GraphCompilationException
    {
        if(start == null)
        {
            throw new NullPointerException("Start node is null");
        }
        if(compiled != null)
        {
            
        }
        while(cl.isClassLoaded("com.thevoxelbox.custom." + this.name + this.c))
        {
            this.c++;
        }
        compiled = (Class<? extends IRunnableGraph>) cl.defineClass("com.thevoxelbox.custom." + this.name + this.c, createClass());
        return compiled;
    }
    
    private byte[] createClass() throws GraphCompilationException
    {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/thevoxelbox/custom/" + this.name + this.c, null, "java/lang/Object", new String[]{ "com/thevoxelbox/vsl/api/IRunnableGraph" });

        cw.visitSource(this.name + this.c + ".java", null);

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
            mv = cw.visitMethod(ACC_PUBLIC, "run", "(Lcom/thevoxelbox/vsl/api/IVariableHolder;)V", null, null);
            mv.visitCode();
            
            int index = 2;
            ExecutableNode current = start;
            while(current != null)
            {
                current.insert(mv, index);
                current = current.getNextNode();
            }
            
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    @Override
    public void run(IVariableHolder vars) throws InstantiationException, IllegalAccessException
    {
        if(compiled == null)
        {
            throw new RuntimeException("Attempted to run graph without compilation");
        }
        IRunnableGraph r = compiled.newInstance();
        r.run(vars);
    }

    @Override
    public String getName()
    {
        return this.name + this.c;
    }
    
}
