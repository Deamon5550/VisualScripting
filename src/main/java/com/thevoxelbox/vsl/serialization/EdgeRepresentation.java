package com.thevoxelbox.vsl.serialization;

/**
 * Represents a single edge in a directed graph.
 */
public class EdgeRepresentation
{
    
    private final int a_node;
    private final int a_output;
    private final int b_node;
    private final int b_input;

    /**
     * Creates a new {@link EdgeRepresentation}.
     * 
     * @param a_node The output node index
     * @param a_output The output node output index
     * @param b_node The input node index
     * @param b_input The input node input index
     */
    public EdgeRepresentation(int a_node, int a_output, int b_node, int b_input)
    {
        this.a_node = a_node;
        this.a_output = a_output;
        this.b_node = b_node;
        this.b_input = b_input;
    }

    /**
     * Gets the output node index.
     * 
     * @return The output node
     */
    public int getOutputNode()
    {
        return this.a_node;
    }

    /**
     * Gets the index of the targeted output of the output node.
     * 
     * @return The output index
     */
    public int getOutputIndex()
    {
        return this.a_output;
    }

    /**
     * Gets the input node index.
     * 
     * @return The output node
     */
    public int getInputNode()
    {
        return this.b_node;
    }

    /**
     * Gets the index of the targeted input of the input node.
     * 
     * @return The input index
     */
    public int getInputIndex()
    {
        return this.b_input;
    }
    
    
    
}
