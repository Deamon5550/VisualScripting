package com.thevoxelbox.test;

import org.junit.Test;

import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

/**
 * Basic {@link NodeGraph} tests.
 */
public class NodeGraphTest extends StandardTest
{

	/**
	 * 
	 */
    @Test
    public void testStringNodes()
    {
        output.setup();

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        PrintNode print = new PrintNode(string.getValue());
        PrintNode print2 = new PrintNode(string2.getValue());
        print.setNext(print2);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        output.check("Hello World");
        output.reset();
    }

}
