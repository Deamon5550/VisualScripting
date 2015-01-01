package com.thevoxelbox.test;

import org.junit.Test;

import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.vars.ChainedInputNode;
import com.thevoxelbox.vsl.nodes.vars.ChainedOutputNode;

public class ChainedNodeGraphTest extends StandardTest
{

    @Test
    public void test()
    {
        output.setup();

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

        output.check("Hello World");
        output.reset();
    }

}
