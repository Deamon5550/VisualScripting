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
import com.thevoxelbox.vsl.nodes.vars.ChainedInputNode;
import com.thevoxelbox.vsl.nodes.vars.ChainedOutputNode;

public class ChainedNodeGraphTest
{
    IVariableHolder vars;

    @Before
    public void setup()
    {
        vars = new VariableScope();
    }

    @Test
    public void test()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        ChainedOutputNode<String> out1 = new ChainedOutputNode<String>("first", string.getValue());
        ChainedOutputNode<String> out2 = new ChainedOutputNode<String>("second", string2.getValue());
        
        NodeGraph graph1 = new NodeGraph("Chained Graph 1");
        graph1.setNext(out1);
        out1.setNext(out2);

        ChainedInputNode<String> in1 = new ChainedInputNode<>("first");
        ChainedInputNode<String> in2 = new ChainedInputNode<>("second");
        PrintNode print = new PrintNode(in1.getValue());
        PrintNode print2 = new PrintNode(in2.getValue());
        print.setNext(print2);

        NodeGraph graph2 = new NodeGraph("Chained Graph 2");
        graph2.setNext(print);
        graph1.chain(graph2);

        graph1.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }

}
