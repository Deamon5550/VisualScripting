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
import com.thevoxelbox.vsl.nodes.control.ForEachNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

public class ForLoopTest
{
    IVariableHolder vars;

    @Before
    public void setup()
    {
        vars = new VariableScope();
    }

    @Test
    public void test() throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StaticValueNode<String[]> array = new StaticValueNode<String[]>(new String[]{"Hel", "lo ", "Wor", "ld"});

        ForEachNode<String> forloop = new ForEachNode<String>(array.getValue());
        PrintNode print = new PrintNode(forloop.getNextValue());
        forloop.setBody(print);

        NodeGraph graph = new NodeGraph("Test Graph", forloop);
        graph.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }

}
