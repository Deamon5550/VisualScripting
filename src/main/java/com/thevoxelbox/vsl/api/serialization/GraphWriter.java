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
package com.thevoxelbox.vsl.api.serialization;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.error.InvalidNodeException;

/**
 * An interface for a serialization consumer for NodeGraphs.
 */
public interface GraphWriter extends Closeable, Flushable
{

    /**
     * Writes the given NodeGraph to the output.
     * 
     * @param graph The graph to write
     * @throws IOException If an error occurs while trying to write to the output.
     * @throws InvalidNodeException If the graph contains an invalid node for serialization
     */
    void write(NodeGraph graph) throws IOException, InvalidNodeException;

}
