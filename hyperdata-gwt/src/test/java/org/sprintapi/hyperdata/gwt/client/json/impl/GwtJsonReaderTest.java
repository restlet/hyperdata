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

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.json.impl.GwtJsonReader;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonBoolean;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonNumber;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonObject;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonString;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtJsonReaderTest extends GWTTestCase {
	
	GwtJsonReader reader;
	
	@Override
	public String getModuleName() {
		return "org.sprintapi.hyperdata.gwt.HyperdataTest";
	}
	
    @Override
    protected void gwtSetUp() throws Exception {
        System.out.println(getClass().getName() + "." + getName());
        reader = new GwtJsonReader();
    }

	public void testNullRead() throws AdapterException {
		assertNull(reader.read(null));
	}
	
	public void testStringRead() throws AdapterException {
		
		JsonValue result = reader.read("\"JSON string\"");
		
		assertNotNull(result);
		assertNotNull(result.isString());
		assertTrue(result instanceof JsonString);
		assertEquals("JSON string", result.isString().stringValue());
	}

	public void testBooleanRead() throws AdapterException {
		
		JsonValue result = reader.read("true");
		
		assertNotNull(result);
		assertNotNull(result.isBoolean());
		assertTrue(result instanceof JsonBoolean);
		assertEquals(Boolean.TRUE, result.isBoolean().booleanValue());
	}
		
	public void testNumberRead() throws AdapterException {
		
		JsonValue result = reader.read("32234.323");
		
		assertNotNull(result);
		assertNotNull(result.isNumber());
		assertTrue(result instanceof JsonNumber);
		assertEquals(32234.323d, result.isNumber().doubleValue());
	}
	
    public void testObjectRead() throws AdapterException {
    	
		JsonValue value = reader.read("{ "
				+ "\"stringType\" : \"JSON String.\", "
				+ "\"booleanType\" : false, "
				+ "\"integerType\" : 1234 "
				+ "}"
				);
		
		assertNotNull(value);
		
		JsonObject obj = value.isObject();
		assertNotNull(obj);
		
		assertEquals(3, obj.size());

		assertTrue(obj.containsKey("stringType"));
		assertTrue(obj.containsKey("booleanType"));
		assertTrue(obj.containsKey("integerType"));
		
		assertNotNull(obj.get("stringType"));
		assertNotNull(obj.get("booleanType"));
		assertNotNull(obj.get("integerType"));
		
		assertNotNull(obj.get("stringType").isString());
		assertNotNull(obj.get("booleanType").isBoolean());
		assertNotNull(obj.get("integerType").isNumber());

		assertTrue(obj.get("stringType") instanceof JsonString);
		assertTrue(obj.get("booleanType") instanceof JsonBoolean);
		assertTrue(obj.get("integerType") instanceof JsonNumber);
		
		assertEquals("JSON String.", obj.get("stringType").isString().stringValue());
		assertEquals(Boolean.FALSE, obj.get("booleanType").isBoolean().booleanValue());
		assertEquals(Double.valueOf(1234), obj.get("integerType").isNumber().doubleValue());
    }
}
