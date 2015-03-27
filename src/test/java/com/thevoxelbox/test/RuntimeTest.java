package com.thevoxelbox.test;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.runtime.ObjectRuntime;

/**
 * A set of tests for the memory runtime.
 */
public class RuntimeTest extends StandardTest
{

    /**
     * 
     */
    @Before
    public void setup()
    {
        super.setup();
    }
    
    /**
     * 
     */
    @Test
    public void testStringNodes()
    {
        this.output.setup();

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        PrintNode print = new PrintNode(string.getValue());
        PrintNode print2 = new PrintNode(string2.getValue());
        print.setNext(print2);

        NodeGraph graph = new RunnableNodeGraph("Test Graph");
        graph.setStart(print);
        new ObjectRuntime().run(graph);

        this.output.check("Hello World");
        this.output.reset();
    }

}
