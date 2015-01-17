/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The VoxelBox
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;
import com.thevoxelbox.vsl.variables.ParentedVariableScope;

/**
 * A set of tests for the variable nodes.
 */
public class VariableNodeTest
{

    Node mockNode;

    /**
     * 
     */
    @Before
    public void setup()
    {
        this.mockNode = Mockito.mock(Node.class);
    }

    /**
     * 
     */
    @Test
    public void testVariableGetNode()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
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
        ParentedVariableScope vars = new ParentedVariableScope();
        vars.set("a", "b");
        RuntimeState state = new RuntimeState(vars);
        Provider<String> target = new Provider<String>(this.mockNode, "a");

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
        ParentedVariableScope vars = new ParentedVariableScope();
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
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> value = new Provider<String>(this.mockNode, "b");

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
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>(this.mockNode, "a");

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
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>(this.mockNode, "a");
        Provider<String> value = new Provider<String>(this.mockNode, "b");

        VariableSetNode<String> get = new VariableSetNode<String>(target, value);
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }
}
