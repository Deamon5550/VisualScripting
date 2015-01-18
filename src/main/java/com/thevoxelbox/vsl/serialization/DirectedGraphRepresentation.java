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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.error.InvalidNodeException;

/**
 * A representation of a NodeGraph as a simple acyclic directed graph
 */
public class DirectedGraphRepresentation
{

    private final Map<String, NodeRepresentation> nodeDictionary;
    private int dictIndex = 0;
    private final List<NodeInstanceRepresentation> instances;
    private int instanceIndex = 0;
    private final List<EdgeRepresentation> edges;

    /**
     * Creates a new {@link DirectedGraphRepresentation}.
     * 
     * @param graph The graph to represent
     * @throws InvalidNodeException If the graph contains invalid nodess
     */
    public DirectedGraphRepresentation(NodeGraph graph) throws InvalidNodeException
    {
        this.nodeDictionary = Maps.newHashMap();
        this.instances = Lists.newArrayList();
        this.edges = Lists.newArrayList();

        Node next = graph.getStart();
        while (next != null)
        {
            addInstance(next);
            next = next.getNext();
        }
        addExecPath(graph.getStart());
    }

    private void addExecPath(Node start)
    {
        Node node = start;
        while (true)
        {
            Node next = node.getNext();
            if (next == null)
            {
                break;
            } else
            {
                addExecEdge(node, next);
                node = next;
            }
        }
    }

    private void addExecEdge(Node a, Node b)
    {
        NodeInstanceRepresentation instanceA = getNodeInstance(a);
        NodeInstanceRepresentation instanceB = getNodeInstance(b);
        this.edges.add(new EdgeRepresentation(instanceA.getIndex(), -1, instanceB.getIndex(), -1));

    }

    private NodeInstanceRepresentation addInstance(Node next) throws InvalidNodeException
    {
        NodeRepresentation node = addNodeToDict(next);
        NodeInstanceRepresentation ins = new NodeInstanceRepresentation(this.instanceIndex++, next, node);
        this.instances.add(ins);
        for (InputRepresentation input : ins.getInputs())
        {
            Node owner = input.getProvider().getOwner();
            if (owner != null && owner != next)
            {
                NodeInstanceRepresentation other = getNodeInstance(owner);
                if (other == null)
                {
                    other = addInstance(owner);
                }
                OutputRepresentation otherOutput = other.getOutput(input.getProvider());
                addEdge(other, otherOutput, ins, input);
            }
        }
        return ins;
    }

    private void addEdge(NodeInstanceRepresentation outNode, OutputRepresentation out, NodeInstanceRepresentation inNode, InputRepresentation in)
    {
        this.edges.add(new EdgeRepresentation(outNode.getIndex(), out.getIndex(), inNode.getIndex(), in.getIndex()));
    }

    private NodeInstanceRepresentation getNodeInstance(Node n)
    {
        if (n == null)
        {
            return null;
        }
        for (NodeInstanceRepresentation ins : this.instances)
        {
            if (ins.getNode() == n)
            {
                return ins;
            }
        }
        return null;
    }

    private NodeRepresentation addNodeToDict(Node n) throws InvalidNodeException
    {
        if (n == null)
        {
            throw new InvalidNodeException("Node was null");
        }
        NodeRepresentation rep = new NodeRepresentation(n, this.dictIndex);
        if (!this.nodeDictionary.containsKey(rep.getName()))
        {
            this.nodeDictionary.put(rep.getName(), rep);
            this.dictIndex++;
            return rep;
        } else
        {
            return this.nodeDictionary.get(rep.getName());
        }
    }

    /**
     * Gets the dictionary of node prototypes.
     * 
     * @return The dictionary
     */
    public Map<String, NodeRepresentation> getNodeDictionary()
    {
        return this.nodeDictionary;
    }

    /**
     * Gets the list of node instances.
     * 
     * @return The instances
     */
    public List<NodeInstanceRepresentation> getInstances()
    {
        return this.instances;
    }

    /**
     * Gets the list of graph edges.
     * 
     * @return The edges
     */
    public List<EdgeRepresentation> getEdges()
    {
        return this.edges;
    }

    /**
     * Gets an ordered list of the dictionary contents.
     * 
     * @return The ordered prototypes
     */
    public List<NodeRepresentation> getOrderedDict()
    {
        List<NodeRepresentation> nodes = Lists.newArrayList();
        for (Map.Entry<String, NodeRepresentation> entry : this.nodeDictionary.entrySet())
        {
            nodes.add(entry.getValue());
        }
        Collections.sort(nodes, new Comparator<NodeRepresentation>()
        {

            @Override
            public int compare(NodeRepresentation a, NodeRepresentation b)
            {
                return a.getIndex() - b.getIndex();
            }

        });
        return nodes;
    }

}
