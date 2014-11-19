package com.thevoxelbox.vsl.api;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.NodeInput;
import com.thevoxelbox.vsl.node.NodeOutput;

public interface INode
{
    void addInput(String name, Class<?> type, boolean required, Object defaultValue);

    void addOutput(String name, Class<?> type, INode parent);

    void mapInput(String input, NodeOutput source) throws InvalidNodeTypeException, NullPointerException;

    NodeOutput getOutput(String name);

    NodeInput getInput(String name);

    void setOutput(String name, int localIndex);

    int insert(MethodVisitor mv, int localsIndex) throws GraphCompilationException;

    boolean isExecutable();

    String getName();

    String getPackage();
}
