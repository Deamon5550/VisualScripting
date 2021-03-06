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

import com.thevoxelbox.test.util.OutputHelper;
import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;
import com.thevoxelbox.vsl.variables.ParentedVariableScope;

/**
 * A standard baseline for {@link RunnableNodeGraph} related tests.
 */
public class StandardTest
{

    protected VariableHolder vars;
    protected OutputHelper output;
    protected RuntimeState state;

    /**
     * Sets up the test.
     */
    public void setup()
    {
        this.vars = new ParentedVariableScope();
        this.output = new OutputHelper();
        this.state = new RuntimeState(this.vars);
    }

    protected <T> void assertResult(T expected, Provider<T> provider)
    {
        assertEquals(expected, provider.get(this.state));
    }
}
