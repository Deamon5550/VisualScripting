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

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.serialization.GraphWriter;
import com.thevoxelbox.vsl.error.InvalidNodeException;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.ReflectionHelper;

/**
 * A {@link GraphWriter} which outputs to a {@link File}.
 */
public class FileGraphWriter implements GraphWriter
{

    private static final int FORMAT_VERSION = 1;

    private PrintWriter outputStream;

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param output The file to output to
     * @throws FileNotFoundException If the file cannot be found
     */
    public FileGraphWriter(File output) throws FileNotFoundException
    {
        this.outputStream = new PrintWriter(output);
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param writer The writer to output to
     */
    public FileGraphWriter(PrintWriter writer)
    {
        this.outputStream = writer;
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param stream The {@link PrintStream} to output to
     */
    public FileGraphWriter(PrintStream stream)
    {
        this.outputStream = new PrintWriter(stream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(NodeGraph graph) throws IOException, InvalidNodeException
    {
        this.outputStream.println("#version " + FORMAT_VERSION);
        this.outputStream.println("name " + graph.getName());
        List<Node> nodes = walk(graph);
        BiMap<Class<? extends Node>, Integer> proto = getPrototypes(nodes);
        for (int i = 0; i < proto.size(); i++)
        {
            printPrototype(proto.inverse().get(i));
        }
        String ins = "i";
        for (Node n : nodes)
        {
            ins += " " + proto.get(n.getClass());
        }
        this.outputStream.println(ins);
        printEdges(graph);
    }

    private void printEdges(NodeGraph graph)
    {
        Node start = graph.getStart();
        while (start != null)
        {
            printEdge(start);
            start = start.getNext();
        }
    }
    
    private void printEdge(Node a)
    {
        NodeInfo inf = a.getClass().getAnnotation(NodeInfo.class);
        for(String s: inf.inputs())
        {
            try
            {
                Field f = ReflectionHelper.getField(this.getClass(), s);
                if (f == null)
                {
                    continue;
                }
                Provider<?> p = (Provider<?>) f.get(this);
                
                if(p.getOwner() != a)
                {
                    
                }
                
            } catch (Exception ignored)
            {
                ignored.printStackTrace();
                continue;
            }
        }
    }

    private BiMap<Class<? extends Node>, Integer> getPrototypes(List<Node> nodes)
    {
        BiMap<Class<? extends Node>, Integer> p = HashBiMap.create();
        int i = 0;
        for (Node n : nodes)
        {
            if (!p.containsKey(n.getClass()))
            {
                p.put(n.getClass(), i++);
            }
        }

        return p;
    }

    private List<Node> walk(NodeGraph graph)
    {
        List<Node> nodes = Lists.newArrayList();

        Node start = graph.getStart();
        while (start != null)
        {
            addNode(nodes, start);
            start = start.getNext();
        }

        return nodes;
    }

    private void addNode(List<Node> nodes, Node n)
    {
        if (!nodes.contains(n))
        {
            nodes.add(n);
        }

        for (Node i : n.getInputs())
        {
            addNode(nodes, i);
        }
    }

    private void printPrototype(Class<? extends Node> n)
    {
        NodeInfo inf = n.getAnnotation(NodeInfo.class);
        String s = "p " + inf.name();
        for (String i : inf.inputs())
        {
            s += " " + i;
        }
        for (String o : inf.outputs())
        {
            s += " " + o;
        }
        this.outputStream.println(s);
    }

    /**
     * Closes this stream and releases any system resources associated with it.
     * If the stream is already closed then invoking this method has no effect.
     * 
     * @throws IOException If an I/O error occurs
     * @see Closeable#close()
     */
    @Override
    public void close() throws IOException
    {
        this.outputStream.close();
    }

    /**
     * Flushes this stream by writing any buffered output to the underlying
     * stream.
     * 
     * @throws IOException If an I/O error occurs
     * @see Flushable#flush()
     */
    @Override
    public void flush() throws IOException
    {
        this.outputStream.flush();
    }

}
