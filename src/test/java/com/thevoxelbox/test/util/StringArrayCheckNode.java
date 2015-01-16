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
package com.thevoxelbox.test.util;

import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * A testing utility node for checking if an array of strings is correctly
 * passed from a loop.
 */
public class StringArrayCheckNode extends AbstractNode
{

    final String[] check;
    int index = 0;
    Provider<String> prov;

    /**
     * Creates a new {@link StringArrayCheckNode}.
     * 
     * @param prov The string provider
     * @param values The expected strings
     */
    public StringArrayCheckNode(Provider<String> prov, String... values)
    {
        this.check = values;
        this.prov = prov;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(RuntimeState state)
    {
        if (this.index >= this.check.length)
        {
            throw new ArrayIndexOutOfBoundsException("More than the expected number of strings were passed into check node.");
        }
        if (!this.check[this.index++].equals(this.prov.get(state)))
        {
            throw new IllegalArgumentException("An unexpected string was passed into check node");
        }
    }

    /**
     * Validates the test.
     */
    public void end()
    {
        if (this.index < this.check.length)
        {
            throw new UnsupportedOperationException("Check was called too few times.");
        }
    }

}
