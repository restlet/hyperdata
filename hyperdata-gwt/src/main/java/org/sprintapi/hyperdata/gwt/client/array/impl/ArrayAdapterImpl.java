/*
 *  Copyright 2012 sprintapi.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sprintapi.hyperdata.gwt.client.array.impl;

import java.lang.reflect.Array;

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.array.ArrayAdapter;

public class ArrayAdapterImpl<T> implements ArrayAdapter<T> {

	private Class<?>[] clazz;
	
	public ArrayAdapterImpl(Class<?>[] clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public void set(Object array, int dim, int index, Object value) throws AdapterException {
		Array.set(array, index, value);
	}

	@Override
	public Class<?> getArrayClass(int dimension) {
		return clazz[dimension];
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A createInstance(int length, int dimension) {
		return (A) Array.newInstance(getArrayClass(dimension-1), length);
	}

	@Override
	public int maxDimension() {
		return clazz.length - 1;
	}

	@Override
	public Object get(Object array, int dim, int index) throws AdapterException {
		return Array.get(array, index);
	}
}
