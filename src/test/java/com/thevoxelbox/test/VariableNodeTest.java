package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * A set of tests for the variable nodes.
 */
public class VariableNodeTest
{

    /**
     * 
     */
    @Test
    public void testVariableGetNode()
    {
        VariableScope vars = new VariableScope();
        vars.set("a", "b");
        RuntimeState state = new RuntimeState(vars);

        VariableGetNode<String> get = new VariableGetNode<String>("a");
        get.exec(state);

        assertEquals("b", get.getValue().get(state));
    }

    /**
     * 
     */
    @Test
    public void testVariableGetNode2()
    {
        VariableScope vars = new VariableScope();
        vars.set("a", "b");
        RuntimeState state = new RuntimeState(vars);
        Provider<String> target = new Provider<String>("a");

        VariableGetNode<String> get = new VariableGetNode<String>(target);
        get.exec(state);

        assertEquals("b", get.getValue().get(state));
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode1()
    {
        VariableScope vars = new VariableScope();
        RuntimeState state = new RuntimeState(vars);

        VariableSetNode<String> get = new VariableSetNode<String>("a", "b");
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode2()
    {
        VariableScope vars = new VariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> value = new Provider<String>("b");

        VariableSetNode<String> get = new VariableSetNode<String>("a", value);
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode3()
    {
        VariableScope vars = new VariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>("a");

        VariableSetNode<String> get = new VariableSetNode<String>(target, "b");
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode4()
    {
        VariableScope vars = new VariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>("a");
        Provider<String> value = new Provider<String>("b");

        VariableSetNode<String> get = new VariableSetNode<String>(target, value);
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }
}
