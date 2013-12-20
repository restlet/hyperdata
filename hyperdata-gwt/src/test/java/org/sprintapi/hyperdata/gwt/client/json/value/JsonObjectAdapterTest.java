package org.sprintapi.hyperdata.gwt.client.json.value;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.json.JsonAdapterContext;
import org.sprintapi.hyperdata.gwt.client.json.JsonValueAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonObject;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonNumberImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonObjectImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonStringImpl;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonValueImpl;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestObject;
import org.sprintapi.hyperdata.gwt.client.json.testdata.TestObjectBeanAdapterMock;

public class JsonObjectAdapterTest  {

	@Test
	public void testReadNull() {
		JsonObjectAdapter<TestObject> a = new JsonObjectAdapter<TestObject>(
				new JsonAdapterContextMock(),
				new TestObjectBeanAdapterMock()
				);
		
		;
		try {
			TestObject o = a.read(null);
			Assert.fail();
		} catch (AdapterException e) {
			Assert.fail(e.getMessage());
			
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testReadTestObject() {
		JsonObjectAdapter<TestObject> a = new JsonObjectAdapter<TestObject>(
				new JsonAdapterContextMock(),
				new TestObjectBeanAdapterMock()
				);
		
		try {
			JsonObjectImpl json = new JsonObjectImpl();
			json.put("@href", new JsonStringImpl("/api/test0"));
			json.put("integerType", new JsonNumberImpl(1234d));
			
			TestObject o = a.read(json);
			
			Assert.assertNotNull(o);
			Assert.assertEquals(Integer.valueOf(1234), o.getIntegerType());
			Assert.assertNotNull(o.getMetadata());

		} catch (AdapterException e) {
			Assert.fail(e.getMessage());

		}
	}

	@Test
	public void testWriteTestObject() {
		JsonObjectAdapter<TestObject> a = new JsonObjectAdapter<TestObject>(
				new JsonAdapterContextMock(),
				new TestObjectBeanAdapterMock()
				);
		
		try {
			TestObject to = new TestObject();
			to.setIntegerType(12345);
			HashMap<String, String> meta = new HashMap<String, String>();
			meta.put("href", "/a/b/c");
			
			to.setMetadata(meta);
			
			JsonValue o = a.write(to);
			
			Assert.assertNotNull(o);
			Assert.assertNotNull(o.isObject());
			Assert.assertNotNull(o.isObject().get("integerType"));
			Assert.assertNotNull(o.isObject().get("integerType").isNumber());
			Assert.assertEquals(Double.valueOf(12345), o.isObject().get("integerType").isNumber().doubleValue());

			
			Assert.assertNotNull(o.isObject().get("@href"));
			Assert.assertNotNull(o.isObject().get("@href").isString());
			Assert.assertEquals("/a/b/c", o.isObject().get("@href").isString().stringValue()); 

			
		} catch (AdapterException e) {
			Assert.fail(e.getMessage());


		}
	}
	
	class JsonAdapterContextMock implements JsonAdapterContext {

		@Override
		public <T> JsonValueAdapter<T> findAdapter(Class<T> clazz) throws AdapterException {
			if (Integer.class.equals(clazz)) {
				return (JsonValueAdapter<T>) new JsonIntegerAdapter();
				
			} else if (Map.class.equals(clazz)) {
				return (JsonValueAdapter<T>) new JsonMapAdapter(new JsonAdapterContextMock());
				
			} else if (String.class.equals(clazz)) {
				return (JsonValueAdapter<T>) new JsonStringAdapter();
			}
			
			return null;
		}		
	}
}
