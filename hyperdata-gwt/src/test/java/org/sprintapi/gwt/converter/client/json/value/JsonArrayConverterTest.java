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
package org.sprintapi.gwt.converter.client.json.value;

import junit.framework.Assert;

import org.junit.Test;
import org.sprintapi.gwt.converter.client.ConverterException;
import org.sprintapi.gwt.converter.client.array.impl.ArrayAdapterImpl;
import org.sprintapi.gwt.converter.client.json.lang.impl.JsonArrayImpl;
import org.sprintapi.gwt.converter.client.json.lang.impl.JsonNumberImpl;
import org.sprintapi.gwt.converter.client.json.lang.impl.JsonStringImpl;

public class JsonArrayConverterTest {

	@Test
	public void testReadStringArray() throws ConverterException {
		JsonArrayConverter<String[]> converter = 
				new JsonArrayConverter<String[]>(
						new JsonStringConverter(),
						new ArrayAdapterImpl(new Class[]{String.class, String[].class})
						);
		
		JsonArrayImpl array = new JsonArrayImpl(4);
		array.set(0, new JsonStringImpl("1"));
		array.set(1, new JsonStringImpl("12"));
		array.set(2, new JsonStringImpl("123"));
		array.set(3, new JsonStringImpl("12 34"));
		
		String[] result = converter.read(array);

		Assert.assertNotNull(result);
		Assert.assertEquals(4, result.length);
		
		Assert.assertEquals("1", result[0]);
		Assert.assertEquals("12", result[1]);
		Assert.assertEquals("123", result[2]);
		Assert.assertEquals("12 34", result[3]);
	}

	@Test
	public void testRead2dDoubleArray() throws ConverterException {
		JsonArrayConverter<Double[][]> converter = 
				new JsonArrayConverter<Double[][]>(
						new JsonDoubleConverter(), 
						new ArrayAdapterImpl(new Class[]{Double.class, Double[].class, Double[][].class})
						);
				
		JsonArrayImpl array = new JsonArrayImpl(2);
		
		JsonArrayImpl array1 = new JsonArrayImpl(3);
		array1.set(0, new JsonNumberImpl(1.1));
		array1.set(1, new JsonNumberImpl(32d));
		array1.set(2, new JsonNumberImpl(0.3));
		
		JsonArrayImpl array2 = new JsonArrayImpl(1);
		array2.set(0, new JsonNumberImpl(13.3));
				
		array.set(0, array1);
		array.set(1, array2);
		
		Double[][] result = converter.read(array);
		
		Assert.assertNotNull(result);
		
		Assert.assertEquals(2, result.length);
		Assert.assertEquals(3, result[0].length);
		Assert.assertEquals(1, result[1].length);
		
		Assert.assertEquals(1.1, result[0][0]);
		Assert.assertEquals(32d, result[0][1]);
		Assert.assertEquals(0.3, result[0][2]);
		Assert.assertEquals(13.3, result[1][0]);
	}

}
