package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

public class NodeGraphTest
{
    IVariableHolder vars;

    @Before
    public void setup()
    {
        vars = new VariableScope();
    }

    @Test
    public void testStringNodes()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        PrintNode print = new PrintNode(string.getValue());
        PrintNode print2 = new PrintNode(string2.getValue());
        print.setNext(print2);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }

}
