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

import static com.thevoxelbox.test.util.MockUtility.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.nodes.math.compare.NumberEqualsNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberGreaterThanNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberGreaterThanOrEqualsNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberLessThanNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberLessThanOrEqualsNode;

/**
 * A set of tests for number comparison nodes
 */
public class NumberComparisonTest extends StandardTest
{

    Node mockNode;

    /**
     * 
     */
    @Before
    public void setup()
    {
        super.setup();
        this.mockNode = Mockito.mock(Node.class);
    }

    /**
     * 
     */
    @Test
    public void testEquals()
    {
        NumberEqualsNode eq = new NumberEqualsNode(mock(5), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testEquals2()
    {
        NumberEqualsNode eq = new NumberEqualsNode(mock(5), mock(4), false);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testGreater()
    {
        NumberGreaterThanNode eq = new NumberGreaterThanNode(mock(6), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testGreater2()
    {
        NumberGreaterThanNode eq = new NumberGreaterThanNode(mock(6), mock(7), false);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(6), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq2()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(5), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testGreaterEq3()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(6), mock(7), false);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testLess()
    {
        NumberLessThanNode eq = new NumberLessThanNode(mock(4), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testLess2()
    {
        NumberLessThanNode eq = new NumberLessThanNode(mock(4), mock(3), false);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testLessEq()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(5), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testLessEq2()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(4), mock(5), false);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testLessEq3()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(4), mock(2), false);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatEquals()
    {
        NumberEqualsNode eq = new NumberEqualsNode(mock(5.2), mock(5.2), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatEquals2()
    {
        NumberEqualsNode eq = new NumberEqualsNode(mock(5.2), mock(5.3), true);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatGreater()
    {
        NumberGreaterThanNode eq = new NumberGreaterThanNode(mock(6.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatGreater2()
    {
        NumberGreaterThanNode eq = new NumberGreaterThanNode(mock(6.4), mock(7.5), true);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(6.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq2()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(5.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatGreaterEq3()
    {
        NumberGreaterThanOrEqualsNode eq = new NumberGreaterThanOrEqualsNode(mock(5.4), mock(5.5), true);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatLess()
    {
        NumberLessThanNode eq = new NumberLessThanNode(mock(4.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatLess2()
    {
        NumberLessThanNode eq = new NumberLessThanNode(mock(4.4), mock(4.2), true);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(5.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq2()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(4.4), mock(5.4), true);

        eq.exec(this.state);

        assertResult(true, eq.getComparisonResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatLessEq3()
    {
        NumberLessThanOrEqualsNode eq = new NumberLessThanOrEqualsNode(mock(4.4), mock(4.3), true);

        eq.exec(this.state);

        assertResult(false, eq.getComparisonResult());
    }

}
