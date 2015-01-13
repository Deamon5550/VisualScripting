package com.thevoxelbox.test;

import org.junit.Test;

import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.control.ForEachNode;
import com.thevoxelbox.vsl.nodes.control.IfStatement;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

/**
 * Tests the control flow nodes.
 */
public class ProgramFlowTest extends StandardTest
{

	/**
	 * 
	 */
	@Test
	public void test()
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

	/**
	 * 
	 */
	@Test
	public void isTrue()
	{
		output.setup();

		StaticValueNode<String> string = new StaticValueNode<String>("Hello");
		StaticValueNode<Boolean> statement = new StaticValueNode<Boolean>(true);
		StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
		PrintNode print = new PrintNode(string.getValue());
		IfStatement ifs = new IfStatement(statement.getValue());
		PrintNode print2 = new PrintNode(string2.getValue());
		print.setNext(ifs);
		ifs.setBody(print2);

		NodeGraph graph = new NodeGraph("Test Graph");
		graph.setNext(print);
		graph.run(vars);

		output.check("Hello World");
		output.reset();
	}

	/**
	 * 
	 */
	@Test
	public void isFalse()
	{
		output.setup();

		StaticValueNode<String> string = new StaticValueNode<String>("Hello World");
		StaticValueNode<Boolean> statement = new StaticValueNode<Boolean>(false);
		StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
		PrintNode print = new PrintNode(string.getValue());
		IfStatement ifs = new IfStatement(statement.getValue());
		PrintNode print2 = new PrintNode(string2.getValue());
		print.setNext(ifs);
		ifs.setBody(print2);

		NodeGraph graph = new NodeGraph("Test Graph");
		graph.setNext(print);
		graph.run(vars);

		output.check("Hello World");
		output.reset();
	}

}
