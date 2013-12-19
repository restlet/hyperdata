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
package org.sprintapi.hyperdata.gwt.client.json.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.sprintapi.hyperdata.gwt.client.ConverterException;
import org.sprintapi.hyperdata.gwt.client.json.JsonWriter;
import org.sprintapi.hyperdata.gwt.client.json.impl.JsonWriterImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonBooleanImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonNumberImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonStringImpl;

public class JsonWriterImplTest {

	JsonWriter writer;
	
    @Before
    public void setup() {
        writer = new JsonWriterImpl();
    }

    @Test
	public void testNullWrite() throws ConverterException {
			
		try {
			String out = writer.write(null);
			Assert.assertEquals("null", out);
			return;

		} catch (IllegalArgumentException e) {
			return;
		}
	}
	
    @Test
    public void testStringWrite() throws ConverterException {

		String result = writer.write(new JsonStringImpl("JSON string"));
		
		Assert.assertNotNull(result);
		Assert.assertEquals("\"JSON string\"", result);
	}

	public void testBooleanWrite() throws ConverterException {

		String result = writer.write(JsonBooleanImpl.getInstance(Boolean.TRUE)); 
		
		Assert.assertNotNull(result);
		Assert.assertEquals("true", result);
	}
	
	@Test
	public void testNumberWrite() throws ConverterException {

		String result = writer.write(new JsonNumberImpl(32234.3d));

		Assert.assertNotNull(result);
		Assert.assertEquals("32234.3", result);
	}	
}
