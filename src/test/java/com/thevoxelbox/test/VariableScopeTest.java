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

import com.thevoxelbox.test.util.OutputHelper;
import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;

/**
 * Sets for a {@link VariableScope}.
 */
public class VariableScopeTest extends StandardTest
{

	private VariableScope parent;
	private VariableScope child;
	private VariableScope child2;

	/**
     * 
     */
	@Before
	public void setup()
	{
		parent = new VariableScope();
		child = new VariableScope(parent);
		child2 = new VariableScope(child);

		parent.set("a", "a");
		parent.set("b", "b");

		child.set("b", "c");
		output = new OutputHelper();
	}

	/**
     * 
     */
	@Test
	public void testInheritence()
	{
		assertEquals("a", child.get("a").get());
		assertEquals("c", child.get("b").get());
	}

	/**
     * 
     */
	@Test
	public void testParenthood()
	{
		assertEquals(child, child2.getParent());
		assertEquals(parent, child2.getHighestParent().get());
	}

	/**
     * 
     */
	@Test
	public void testVariableNodes()
	{
		output.setup();

		StaticValueNode<String> value = new StaticValueNode<String>("Hello World");
		VariableSetNode<String> set = new VariableSetNode<String>("name", value.getValue());
		VariableGetNode<String> get = new VariableGetNode<String>("name");
		PrintNode print = new PrintNode(get.getValue());
		set.setNext(print);

		NodeGraph graph = new NodeGraph("Test Graph");
		graph.setNext(set);
		graph.run(child);

		output.check("Hello World");
		output.reset();
	}

	/**
     * 
     */
	@Test
	public void testCaseSensitivity()
	{
		parent.setCaseSensitive(false);
		parent.set("aString", "Hello");
		assertEquals("Hello", parent.get("astring").get());
	}
}
