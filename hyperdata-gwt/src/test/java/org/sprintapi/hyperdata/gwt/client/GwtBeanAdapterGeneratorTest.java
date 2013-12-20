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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.sprintapi.hyperdata.gwt.client.ConverterException;
import org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptor;
import org.sprintapi.hyperdata.gwt.client.bean.HyperBeanAttributes;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestBaseObject;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestMapping;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestObject;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestObject.EnumTest;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtBeanAdapterGeneratorTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.sprintapi.hyperdata.gwt.HyperdataTest";
	}
	
    @Override
    protected void gwtSetUp() throws Exception {
        System.out.println(getClass().getName() + "." + getName());
    }

    public void testGetBeanClass() {
    	Class<?> clazz = TestMapping.BASIC_TYPES_ADAPTER.getBeanClass();

    	assertNotNull(clazz);
    	assertEquals(TestObject.class, clazz);
    }

    public void testGetBeanAttributes() {
    	HyperBeanAttributes attrs = TestMapping.BASIC_TYPES_ADAPTER.getAttributes();

    	assertNotNull(attrs);
    	assertNotNull(attrs.getProfile());
    	assertEquals(1, attrs.getProfile().length);
    	assertEquals("http://sprintapi.org/profile/test1", attrs.getProfile()[0]);
    }

    public void testGetProperties() throws ConverterException {
    	
    	BeanPropertyDescriptor[] properties = TestMapping.BASIC_TYPES_ADAPTER.getProperties(); 
    	
    	assertNotNull(properties);
    	assertEquals(12, properties.length);

    	Map<String, BeanPropertyDescriptor> map = new HashMap<String, BeanPropertyDescriptor>();
    	for (BeanPropertyDescriptor p : properties) {
    		assertNotNull(p.getName());
    		map.put(p.getName(), p);
    	}
   
    	assertTrue(map.containsKey("booleanType"));
    	assertEquals(Boolean.class, map.get("booleanType").getClazz());
    	
    	assertTrue(map.containsKey("integerType"));
    	assertEquals(Integer.class, map.get("integerType").getClazz());

    	assertTrue(map.containsKey("longType"));
    	assertEquals(Long.class, map.get("longType").getClazz());

    	assertTrue(map.containsKey("floatType"));
    	assertEquals(Float.class, map.get("floatType").getClazz());

    	assertTrue(map.containsKey("doubleType"));
    	assertEquals(Double.class, map.get("doubleType").getClazz());

    	assertTrue(map.containsKey("dateType"));
    	assertEquals(Date.class, map.get("dateType").getClazz());
    	
    	assertTrue(map.containsKey("selfRef"));
    	assertEquals(TestBaseObject.class, map.get("selfRef").getClazz());
    	
    	assertTrue(map.containsKey("selfRefArray1"));
    	assertEquals(TestObject[].class, map.get("selfRefArray1").getClazz());

    	assertTrue(map.containsKey("selfRefArray2"));
    	assertEquals(TestObject[][].class, map.get("selfRefArray2").getClazz());
    	
    	assertTrue(map.containsKey("enumType"));
    	assertEquals(TestObject.EnumTest.class, map.get("enumType").getClazz());
    	
    	assertTrue(map.containsKey("metadata"));
    	assertEquals(Map.class, map.get("metadata").getClazz());
    	assertNotNull(map.get("metadata").getAttributes());
    }
    
    public void testGetPropertyValue() throws ConverterException {
    	
    	TestObject types = new TestObject();
    	types.setBooleanType(true);
    	types.setDateType(new Date());
    	types.setDoubleType(123.3);
    	types.setFloatType(4.66f);
    	types.setIntegerType(43543);
    	types.setLongType(345242424l);
    	types.setStringType("4234234 sdasd aq44 4234234 ");
    	types.setSelfRef(types);
    	types.setSelfRefArray1(new TestObject[]{types, types});
    	types.setSelfRefArray2(new TestObject[][]{ { types, types } , {types, types}});
    	types.setEnumType(EnumTest.E2);
    	
    	assertEquals(types.getBooleanType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "booleanType"));
    	assertEquals(types.getDateType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "dateType"));
    	assertEquals(types.getDoubleType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "doubleType"));
    	assertEquals(types.getFloatType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "floatType"));
    	assertEquals(types.getIntegerType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "integerType"));
    	assertEquals(types.getLongType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "longType"));
    	assertEquals(types.getStringType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "stringType"));
    	assertEquals(types.getSelfRef(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "selfRef"));
    	assertEquals(types.getSelfRefArray1(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "selfRefArray1"));
    	assertEquals(types.getSelfRefArray2(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "selfRefArray2"));
    	assertEquals(types.getEnumType(), TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "enumType"));
    }
    
    public void testSetPropertyValue() throws ConverterException {
    	TestObject types = new TestObject();
    	
    	Date date = new Date();
    	
    	TestObject ref = new TestObject();
    	TestObject[] refArray1 = new TestObject[]{types, types};
    	TestObject[][] refArray2 = new TestObject[][]{ { types, types } , {types, types}};
    	
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "booleanType", false);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "dateType", date);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "doubleType", 235.43);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "floatType", 6.4f);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "integerType", 23);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "longType", 342342342l);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "stringType", "dsada4324 3424234");
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "selfRef", ref);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "selfRefArray1", refArray1);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "selfRefArray2", refArray2);
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "enumType", EnumTest.E1);

    	assertEquals(Boolean.FALSE, types.getBooleanType());
    	assertEquals(date, types.getDateType());
    	assertEquals(235.43, types.getDoubleType());
    	assertEquals(6.4f, types.getFloatType());
    	assertEquals(Integer.valueOf(23), types.getIntegerType());
    	assertEquals(Long.valueOf(342342342l), types.getLongType());
    	assertEquals("dsada4324 3424234", types.getStringType());
    	assertEquals(ref, types.getSelfRef());
    	assertEquals(refArray1, types.getSelfRefArray1());
    	assertEquals(refArray2, types.getSelfRefArray2());
    	assertEquals(EnumTest.E1, types.getEnumType());
    }
    
    public void testGetUnexistentPropertyValue() throws ConverterException {
    	TestObject types = new TestObject();
    	TestMapping.BASIC_TYPES_ADAPTER.getPropertyValue(types, "xxxxx");
    	//TODO
    }

    public void testSetUnexistentPropertyValue() throws ConverterException {
    	TestObject types = new TestObject();
    	TestMapping.BASIC_TYPES_ADAPTER.setPropertyValue(types, "xxxxx", false);
    	//TODO
    }
    
    public void testCreateInstance() {
    	TestObject types = TestMapping.BASIC_TYPES_ADAPTER.createInstance();
    	assertNotNull(types);
    }
}
