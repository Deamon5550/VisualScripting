/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The Voxel Plugineering Team
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
package com.thevoxelbox.vsl.util;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.api.INode;

/**
 * Tracks values for an input or output of a node and stores them with an
 * associated UUID for a {@link RuntimeState}.
 * 
 * @param <T> The value type
 */
public class Provider<T>
{

	private final Map<UUID, T> values;
	private final T value;
	private final boolean isStatic;
	private INode callback;

	/**
	 * Creates a new {@link Provider} which will perform a callback to the given
	 * node if the value is not set and a call to {@link #get(RuntimeState)}
	 * occurs.
	 * 
	 * @param n The node to call back to
	 */
	public Provider(INode n)
	{
		this.callback = n;
		this.values = Maps.newHashMap();
		isStatic = false;
		this.value = null;
	}

	/**
	 * Creates a new {@link Provider} with the given static value.
	 * 
	 * @param value The static value
	 */
	public Provider(T value)
	{
		this.value = value;
		isStatic = true;
		this.values = null;
	}

	/**
	 * Gets the value for the given {@link RuntimeState} from this provider. I
	 * decided not to use optionals here in preference of the debugging utility
	 * of null pointers over checking an optional value.
	 * 
	 * @param state The runtime state
	 * @return The value, or null
	 */
	public T get(RuntimeState state)
	{
		if (isStatic)
		{
			return this.value;
		}
		if (!this.values.containsKey(state.getUUID()))
		{
			this.callback.exec(state);
		}
		return this.values.get(state.getUUID());
	}

	/**
	 * Sets the value for the given {@link UUID}.
	 * 
	 * @param newValue The new value
	 * @param uuid The UUID
	 */
	public void set(T newValue, UUID uuid)
	{
		this.values.put(uuid, newValue);
	}

}
