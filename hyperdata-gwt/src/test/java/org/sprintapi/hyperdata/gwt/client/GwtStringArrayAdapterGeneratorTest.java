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
package org.sprintapi.hyperdata.gwt.client;

import org.sprintapi.hyperdata.gwt.client.json.testdata.TestMapping;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtStringArrayAdapterGeneratorTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.sprintapi.gwt.converter.JSONConverterTest";
	}
	
    @Override
    protected void gwtSetUp() throws Exception {
        System.out.println(getClass().getName() + "." + getName());
    }

    public void testGetArrayClass() {
    	Class<?> c0 = TestMapping.STRING_ARRAY_ADAPTER.getArrayClass(0);
    	assertNotNull(c0);
    	assertEquals(String.class, c0);
    	
    	Class<?> c1 = TestMapping.STRING_ARRAY_ADAPTER.getArrayClass(1);
    	assertNotNull(c1);
    	assertEquals(String[].class, c1);
    	
    	Class<?> c2 = TestMapping.STRING_ARRAY_ADAPTER.getArrayClass(2);
    	assertNotNull(c2);
    	assertEquals(String[][].class, c2);
    	
    	Class<?> c3 = TestMapping.STRING_ARRAY_ADAPTER.getArrayClass(3);
    	assertNotNull(c3);
    	assertEquals(String[][][].class, c3);
    	
    	Class<?> c4 = TestMapping.STRING_ARRAY_ADAPTER.getArrayClass(4);
    	assertNotNull(c4);
    	assertEquals(String[][][][].class, c4);
    }
    
    public void testMaxDimension() {
    	assertEquals(4, TestMapping.STRING_ARRAY_ADAPTER.maxDimension());
    }
    
    public void testCreateInstance() {
    	Object a1 = TestMapping.STRING_ARRAY_ADAPTER.createInstance(0, 1);
    	assertNotNull(a1);
    	assertEquals(String[].class, a1.getClass());
    	assertEquals(0, ((String[])a1).length);
    	
    	Object a2 = TestMapping.STRING_ARRAY_ADAPTER.createInstance(5, 3);
    	assertNotNull(a2);
    	assertEquals(String[][][].class, a2.getClass());
    	assertEquals(5, ((String[][][])a2).length);
    }

}
