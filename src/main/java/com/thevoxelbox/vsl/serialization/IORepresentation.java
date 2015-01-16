package com.thevoxelbox.vsl.serialization;

/**
 * Represents either an output or and input to a node.
 */
public interface IORepresentation
{

    /**
     * Gets the name of the input/output.
     * 
     * @return The name
     */
    String getName();
    
    /**
     * Gets the index of the output/input.
     * 
     * @return The index
     */
    int getIndex();
    
}
