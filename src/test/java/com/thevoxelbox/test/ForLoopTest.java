package com.thevoxelbox.test;

import org.junit.Test;

import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.control.ForEachNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

public class ForLoopTest extends StandardTest
{

    @Test
    public void test() throws Exception
    {
        output.setup();

        StaticValueNode<String[]> array = new StaticValueNode<String[]>(new String[] { "Hel", "lo ", "Wor", "ld" });

        ForEachNode<String> forloop = new ForEachNode<String>(array.getValue());
        PrintNode print = new PrintNode(forloop.getNextValue());
        forloop.setBody(print);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(forloop);
        graph.run(vars);

        output.check("Hello World");
        output.reset();
    }

}
