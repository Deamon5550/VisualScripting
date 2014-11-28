package com.thevoxelbox.vsl.api;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.NodeInput;
import com.thevoxelbox.vsl.node.NodeOutput;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node for use within visual scripting graphs.
 */
public interface INode
{

    /**
     * Adds an input for this node.
     * 
     * @param name the input name, cannot be null or empty
     * @param type the input type, cannot be null
     * @param required whether this input is a required or an optional input
     * @param defaultValue the default value for this node in the case of it being optional, may be null otherwise
     */
    void addInput(String name, Type type, boolean required, Object defaultValue);

    /**
     * Adds an output for this node.
     * 
     * @param name the name for this output, cannot be null or empty
     * @param type the type of the output, cannot be null
     * @param parent the node that this output is attached to, used for input resolution, cannot be null
     */
    void addOutput(String name, Type type, INode parent);

    /**
     * Maps the input if this node to the given output of another node.
     * 
     * @param input the name of the input to map this output to, cannot be null or empty
     * @param source the output to map to the input
     * @throws InvalidNodeTypeException if the types do not match
     * @throws GraphCompilationException if an error occurs
     */
    void mapInput(String input, NodeOutput source) throws InvalidNodeTypeException, GraphCompilationException;

    /**
     * Returns the output from this node with the given name, or null if no output exists with this name.
     * 
     * @param name the name of the output to fetch, cannot be null or empty
     * @return the output for this name, or null
     */
    NodeOutput getOutput(String name);

    /**
     * Returns the input to this node with the given name, or null if no input exists with this name.
     * 
     * @param name the name of the input to fetch, cannot be null or empty
     * @return the input for this name, or null
     */
    NodeInput getInput(String name);

    /**
     * Sets the local variable index for the output specified.
     * 
     * @param name the output to set, cannot be null or empty
     * @param localIndex the local variable index for the output, cannot be negative
     */
    void setOutput(String name, int localIndex);

    /**
     * Inserts this node into the given {@link MethodVisitor}, assumes the next unused local variable index is the given index.
     * 
     * @param mv the MethodVisitor to append the node to, cannot be null
     * @param localsIndex the next unused local variable index, cannot be negative
     * @return the next unused local variable index
     * @throws GraphCompilationException if an error occurs while appending this node
     */
    int insert(MethodVisitor mv, int localsIndex) throws GraphCompilationException;

    /**
     * Returns if this node is considered an executable node
     * 
     * @return is this node executable
     */
    boolean isExecutable();

    /**
     * Returns the name of this node.
     * 
     * @return the name
     */
    String getName();

    /**
     * The package that this node is a part of.
     * 
     * @return the package
     */
    String getPackage();
}
