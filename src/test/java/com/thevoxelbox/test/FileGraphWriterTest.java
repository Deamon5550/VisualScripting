package com.thevoxelbox.test;

import java.io.IOException;

import org.junit.Test;

import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.error.InvalidNodeException;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.math.AdditionNode;
import com.thevoxelbox.vsl.serialization.FileGraphWriter;

/**
 * A test for the file graph writer.
 */
public class FileGraphWriterTest
{

    /**
     * 
     * @throws IOException
     * @throws InvalidNodeException
     */
    @Test
    public void testWriter() throws IOException, InvalidNodeException
    {
        FileGraphWriter writer = new FileGraphWriter(System.out);
        NodeGraph graph = new RunnableNodeGraph("TestGraph");
        StaticValueNode<Integer> a = new StaticValueNode<Integer>(5);
        StaticValueNode<Integer> b = new StaticValueNode<Integer>(6);
        AdditionNode add = new AdditionNode(a.getValue(), b.getValue(), false);
        PrintNode print = new PrintNode(add.getResult());
        graph.setStart(add);
        add.setNext(print);
        writer.write(graph);
        writer.close();
    }
    
}
