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
    private int dict_index = 0;
    private final List<NodeInstanceRepresentation> instances;
    private int instance_index = 0;
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
            if(next == null)
            {
                break;
            }
            else
            {
                addExecEdge(node, next);
                node = next;
            }
        }
    }
    
    private void addExecEdge(Node a, Node b)
    {
        NodeInstanceRepresentation aIns = getNodeInstance(a);
        NodeInstanceRepresentation bIns = getNodeInstance(b);
        this.edges.add(new EdgeRepresentation(aIns.getIndex(), -1, bIns.getIndex(), -1));
        
    }
    
    private NodeInstanceRepresentation addInstance(Node next) throws InvalidNodeException
    {
        NodeRepresentation node = addNodeToDict(next);
        NodeInstanceRepresentation ins = new NodeInstanceRepresentation(this.instance_index++, next, node);
        System.out.printf("Adding instance %s %d\n", node.getName(), node.getIndex());
        this.instances.add(ins);
        for (InputRepresentation input : ins.getInputs())
        {
            Node owner = input.getProvider().getOwner();
            if (owner != null && owner != next)
            {
                NodeInstanceRepresentation other = getNodeInstance(owner);
                if(other == null)
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
        System.out.printf("Adding edge %d %s %d %s\n", outNode.getIndex(), out, inNode.getIndex(), in);
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
        NodeRepresentation rep = new NodeRepresentation(n, this.dict_index);
        if (!this.nodeDictionary.containsKey(rep.getName()))
        {
            this.nodeDictionary.put(rep.getName(), rep);
            this.dict_index++;
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
        for(Map.Entry<String, NodeRepresentation> entry: this.nodeDictionary.entrySet())
        {
            nodes.add(entry.getValue());
        }
        Collections.sort(nodes, new Comparator<NodeRepresentation>(){

            @Override
            public int compare(NodeRepresentation a, NodeRepresentation b)
            {
                return a.getIndex()-b.getIndex();
            }
            
        });
        return nodes;
    }

}
