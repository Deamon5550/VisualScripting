/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The VoxelBox
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.thevoxelbox.vsl.serialization;

/**
 * Represents a single edge in a directed graph.
 */
public class EdgeRepresentation
{

    private final int nodeA;
    private final int output;
    private final int nodeB;
    private final int input;

    /**
     * Creates a new {@link EdgeRepresentation}.
     * 
     * @param nodeA The output node index
     * @param output The output node output index
     * @param nodeB The input node index
     * @param input The input node input index
     */
    public EdgeRepresentation(int nodeA, int output, int nodeB, int input)
    {
        this.nodeA = nodeA;
        this.output = output;
        this.nodeB = nodeB;
        this.input = input;
    }

    /**
     * Gets the output node index.
     * 
     * @return The output node
     */
    public int getOutputNode()
    {
        return this.nodeA;
    }

    /**
     * Gets the index of the targeted output of the output node.
     * 
     * @return The output index
     */
    public int getOutputIndex()
    {
        return this.output;
    }

    /**
     * Gets the input node index.
     * 
     * @return The output node
     */
    public int getInputNode()
    {
        return this.nodeB;
    }

    /**
     * Gets the index of the targeted input of the input node.
     * 
     * @return The input index
     */
    public int getInputIndex()
    {
        return this.input;
    }

}
