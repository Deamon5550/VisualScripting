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
import com.thevoxelbox.vsl.nodes.math.AbsNode;
import com.thevoxelbox.vsl.nodes.math.AdditionNode;
import com.thevoxelbox.vsl.nodes.math.DivisionNode;
import com.thevoxelbox.vsl.nodes.math.ModuloNode;
import com.thevoxelbox.vsl.nodes.math.MultiplicationNode;
import com.thevoxelbox.vsl.nodes.math.SqrtNode;
import com.thevoxelbox.vsl.nodes.math.SubtractionNode;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Tests for number based nodes.
 */
public class NumberNodeTest extends StandardTest
{
    
    Node mockNode;
    
    @Before
    public void setup()
    {
        this.mockNode = Mockito.mock(Node.class);
    }

    /**
     * 
     */
    @Test
    public void testAddition()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 6);

        AdditionNode add = new AdditionNode(a, b, false);

        add.exec(this.state);

        assertEquals(11L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testSubtraction()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 6);

        SubtractionNode add = new SubtractionNode(a, b, false);

        add.exec(this.state);

        assertEquals(-1L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testMultiplication()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 6);

        MultiplicationNode add = new MultiplicationNode(a, b, false);

        add.exec(this.state);

        assertEquals(30L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testDivision()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 14);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 6);

        DivisionNode add = new DivisionNode(a, b, false);

        add.exec(this.state);

        assertEquals(2L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testModulo()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 8);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 6);

        ModuloNode add = new ModuloNode(a, b, false);

        add.exec(this.state);

        assertEquals(2L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testSqrt()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 16);

        SqrtNode add = new SqrtNode(a);

        add.exec(this.state);

        assertEquals(4.0, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testAbs()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, -5);

        AbsNode add = new AbsNode(a, false);

        add.exec(this.state);

        assertEquals(5L, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatAbs()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, -5.2);

        AbsNode add = new AbsNode(a, true);

        add.exec(this.state);

        assertEquals(5.2, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatAddition()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.2);
        Provider<Double> b = new Provider<Double>(this.mockNode, 6.3);

        AdditionNode add = new AdditionNode(a, b, true);

        add.exec(this.state);

        assertEquals(11.5, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatSubtraction()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.2);
        Provider<Double> b = new Provider<Double>(this.mockNode, 6.2);

        SubtractionNode add = new SubtractionNode(a, b, true);

        add.exec(this.state);

        assertEquals(-1.0, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatMultiplication()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 2.5);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.0);

        MultiplicationNode add = new MultiplicationNode(a, b, true);

        add.exec(this.state);

        assertEquals(12.5, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatDivision()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 7.5);
        Provider<Double> b = new Provider<Double>(this.mockNode, 3.0);

        DivisionNode add = new DivisionNode(a, b, true);

        add.exec(this.state);

        assertEquals(2.5, add.getResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatModulo()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 2.2);

        ModuloNode add = new ModuloNode(a, b, true);

        add.exec(this.state);

        assertEquals(1.0, add.getResult().get(this.state));
    }

}
