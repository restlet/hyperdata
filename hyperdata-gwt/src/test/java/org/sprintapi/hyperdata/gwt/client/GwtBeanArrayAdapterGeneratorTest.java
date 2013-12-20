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

import org.sprintapi.hyperdata.gwt.client.ConverterException;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestMapping;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestObject;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtBeanArrayAdapterGeneratorTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.sprintapi.hyperdata.gwt.HyperdataTest";
	}
	
    @Override
    protected void gwtSetUp() throws Exception {
        System.out.println(getClass().getName() + "." + getName());
    }

    public void testGetArrayClass() {
    	Class<?> c0 = TestMapping.BASIC_BEAN_ARRAY_ADAPTER.getArrayClass(0);
    	assertNotNull(c0);
    	assertEquals(TestObject.class, c0);
    	
    	Class<?> c1 = TestMapping.BASIC_BEAN_ARRAY_ADAPTER.getArrayClass(1);
    	assertNotNull(c1);
    	assertEquals(TestObject[].class, c1);
    	
    	Class<?> c2 = TestMapping.BASIC_BEAN_ARRAY_ADAPTER.getArrayClass(2);
    	assertNotNull(c2);
    	assertEquals(TestObject[][].class, c2);    	
    }
    
    public void testMaxDimension() {
    	assertEquals(2, TestMapping.BASIC_BEAN_ARRAY_ADAPTER.maxDimension());
    }
    
    public void testCreateInstance() {
    	Object a1 = TestMapping.BASIC_BEAN_ARRAY_ADAPTER.createInstance(0, 1);
    	assertNotNull(a1);
    	assertEquals(TestObject[].class, a1.getClass());
    	assertEquals(0, ((TestObject[])a1).length);
    	
    	Object a2 = TestMapping.BASIC_BEAN_ARRAY_ADAPTER.createInstance(5, 2);
    	assertNotNull(a2);
    	assertEquals(TestObject[][].class, a2.getClass());
    	assertEquals(5, ((TestObject[][])a2).length);
    }
    
    public void testSet() throws ConverterException {
    	
    	TestObject[] a = new TestObject[2];
    	TestObject o1 = new TestObject();
    	TestObject o2 = new TestObject();
    	
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(a, 0, 0, o1);
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(a, 0, 1, o2);
    	
    	assertEquals(o1, a[0]);
    	assertEquals(o2, a[1]);
    }

    public void testSet2() throws ConverterException {
    	
    	TestObject[][] a = new TestObject[2][];
    	
    	TestObject[] o11 = new TestObject[1];
    	TestObject[] o12 = new TestObject[1];
    	
    	TestObject o1 = new TestObject();
    	TestObject o2 = new TestObject();
    	
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(a, 1, 0, o11);
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(a, 1, 1, o12);
    	
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(o11, 0, 0, o1);
    	TestMapping.BASIC_BEAN_ARRAY_ADAPTER.set(o12, 0, 0, o2);
    	
    	assertEquals(o1, a[0][0]);
    	assertEquals(o2, a[1][0]);
    }
}
