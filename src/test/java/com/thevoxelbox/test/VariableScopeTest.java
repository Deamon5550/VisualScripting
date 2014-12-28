package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;

public class VariableScopeTest
{
    private VariableScope parent;
    private VariableScope child;
    private VariableScope child2;

    @Before
    public void setup()
    {
        parent = new VariableScope();
        child = new VariableScope(parent);
        child2 = new VariableScope(child);

        parent.set("a", "a");
        parent.set("b", "b");

        child.set("b", "c");
    }

    @Test
    public void testInheritence()
    {
        assertEquals("a", child.get("a").get());
        assertEquals("c", child.get("b").get());
    }

    @Test
    public void testParenthood()
    {
        assertEquals(child, child2.getParent());
        assertEquals(parent, child2.getHighestParent().get());
    }

    @Test
    public void testVariableNodes()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StaticValueNode<String> value = new StaticValueNode<String>("Hello World");
        VariableSetNode<String> set = new VariableSetNode<String>("name", value.getValue());
        VariableGetNode<String> get = new VariableGetNode<String>("name");
        PrintNode print = new PrintNode(get.getValue());
        set.setNext(print);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(set);
        graph.run(child);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }
}
