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
import com.thevoxelbox.vsl.nodes.math.compare.NumberEqualsNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberGreaterThanNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberGreaterThanOrEqualsNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberLessThanNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberLessThanOrEqualsNode;
import com.thevoxelbox.vsl.util.Provider;

/**
 * A set of tests for number comparison nodes
 */
public class NumberComparisonTest extends StandardTest
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
    public void testEquals()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberEqualsNode eq = new NumberEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testEquals2()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 4);

        NumberEqualsNode eq = new NumberEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testGreater()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 6);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberGreaterThanNode eq = new NumberGreaterThanNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testGreater2()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 6);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 7);

        NumberGreaterThanNode eq = new NumberGreaterThanNode(a, b, false);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 6);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq2()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq3()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 6);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 7);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testLess()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 4);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberLessThanNode eq = new NumberLessThanNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testLess2()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 4);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 3);

        NumberLessThanNode eq = new NumberLessThanNode(a, b, false);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testLessEq()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 5);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testLessEq2()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 4);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 5);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testLessEq3()
    {
        Provider<Integer> a = new Provider<Integer>(this.mockNode, 4);
        Provider<Integer> b = new Provider<Integer>(this.mockNode, 2);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, false);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatEquals()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.2);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.2);

        NumberEqualsNode eq = new NumberEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatEquals2()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.2);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.3);

        NumberEqualsNode eq = new NumberEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatGreater()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 6.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberGreaterThanNode eq = new NumberGreaterThanNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatGreater2()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 6.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 7.5);

        NumberGreaterThanNode eq = new NumberGreaterThanNode(a, b, true);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 6.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq2()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq3()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.5);

        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatLess()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 4.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberLessThanNode eq = new NumberLessThanNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatLess2()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 4.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 4.2);

        NumberLessThanNode eq = new NumberLessThanNode(a, b, true);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 5.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq2()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 4.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 5.4);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(true, eq.getComparisonResult().get(this.state));
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq3()
    {
        Provider<Double> a = new Provider<Double>(this.mockNode, 4.4);
        Provider<Double> b = new Provider<Double>(this.mockNode, 4.3);

        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(a, b, true);

        eq.exec(this.state);

        assertEquals(false, eq.getComparisonResult().get(this.state));
    }

}
