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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thevoxelbox.test.util.MockUtility;
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
    private Provider<Boolean> test_field = MockUtility.mock(false, Boolean.class);

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param output
     *            The file to output to
     * @throws FileNotFoundException
     *             If the file cannot be found
     */
    public FileGraphWriter(File output) throws FileNotFoundException
    {
        this.outputStream = new PrintWriter(output);
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param writer
     *            The writer to output to
     */
    public FileGraphWriter(PrintWriter writer)
    {
        this.outputStream = writer;
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param stream
     *            The {@link PrintStream} to output to
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
        List<NodeData> nodes = walk(graph);
        BiMap<Class<? extends Node>, Integer> proto = getPrototypes(nodes);
        for (int i = 0; i < proto.size(); i++)
        {
            printPrototype(proto.inverse().get(i));
        }
        String ins = "i";
        for (NodeData n : nodes)
        {
            ins += " " + proto.get(n.getClass());
        }
        this.outputStream.println(ins);
        //extractStaticValues(graph);
        printEdges(nodes);
    }

    private void extractStaticValues(NodeGraph graph)
    {
        List<String> values = Lists.newArrayList();
        List<String> edges = Lists.newArrayList();
        List<Node> previous = Lists.newArrayList();
        
        Node start = graph.getStart();
        while (start != null)
        {
            findStatics(start, values, edges, previous);
            start = start.getNext();
        }
        
        for(String s: values)
        {
            this.outputStream.println(s);
        }
        for(String s: edges)
        {
            this.outputStream.println(s);
        }

    }

    private void findStatics(Node start, List<String> values, List<String> edges, List<Node> previous)
    {
        previous.add(start);
        NodeInfo inf = start.getClass().getAnnotation(NodeInfo.class);
        for (String s : inf.inputs())
        {
            try
            {
                Field f = ReflectionHelper.getField(start.getClass(), s);
                if (f == null)
                {
                    continue;
                }
                Provider<?> p = (Provider<?>) f.get(start);
                
                if (p.isStatic())
                {
                    Gson g = new GsonBuilder().create();
                    String json = g.toJson(p.get(null), p.getType());
                    values.add("v " + json);
                    
                } else if (p.getOwner() != start && !previous.contains(p.getOwner()))
                {
                    findStatics(p.getOwner(), values, edges, previous);
                }

            } catch (Exception ignored)
            {
                ignored.printStackTrace();
                continue;
            }
        }
    }

    private void printEdges(List<NodeData> nodes)
    {
        NodeData start = nodes.get(0);
        while (start != null)
        {
            printEdge(nodes, start);
            start = start.getNext();
        }
    }

    private void printEdge(List<NodeData> nodes, NodeData a)
    {
        for (String s : a.getInfo().inputs())
        {
            try
            {
                Field f = ReflectionHelper.getField(this.getClass(), s);
                if (f == null)
                {
                    continue;
                }
                Provider<?> p = (Provider<?>) f.get(this);
                Node other = p.getOwner();
                if (other != a)
                {
                    String name = other.getNameOfOutput(p);
                }

            } catch (Exception ignored)
            {
                ignored.printStackTrace();
                continue;
            }
        }
    }

    private BiMap<Class<? extends Node>, Integer> getPrototypes(List<NodeData> nodes)
    {
        BiMap<Class<? extends Node>, Integer> p = HashBiMap.create();
        int i = 0;
        for (NodeData n : nodes)
        {
            if (!p.containsKey(n.getClass()))
            {
                p.put(n.getNodeClass(), i++);
            }
        }

        return p;
    }

    private List<NodeData> walk(NodeGraph graph)
    {
        List<NodeData> nodes = Lists.newArrayList();

        Node start = graph.getStart();
        while (start != null)
        {
            addNode(nodes, start);
            start = start.getNext();
        }

        return nodes;
    }

    private void addNode(List<NodeData> nodes, Node n)
    {
        NodeData data = null;
        boolean found = false;
        for(NodeData d: nodes)
        {
            if(d.getNode() == n)
            {
                data = d;
                found = true;
                break;
            }
        }
        if(!found)
        {
            nodes.add(data = new NodeData(n));
        }

        for (Node i : data.getInputs())
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
     * @throws IOException
     *             If an I/O error occurs
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
     * @throws IOException
     *             If an I/O error occurs
     * @see Flushable#flush()
     */
    @Override
    public void flush() throws IOException
    {
        this.outputStream.flush();
    }

}
