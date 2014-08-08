/*
 * Copyright 2013 the original author or authors.
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
package org.sprintapi.hyperdata.gson;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.sprintapi.hyperdata.HyperHashMap;
import org.sprintapi.hyperdata.HyperMap;
import org.sprintapi.hyperdata.view.HyperDataView;

import com.google.gson.JsonParseException;


@RunWith(JUnit4.class)
public class GsonHyperDataFactoryTest {

	@Rule
    public ResourceFile ressZeroLength = new ResourceFile("zero-length.json");
	@Rule
	public ResourceFile ressBlank = new ResourceFile("blank.json");
	@Rule
	public ResourceFile ressMeta = new ResourceFile("meta.json");	
	@Rule
	public ResourceFile ressR1 = new ResourceFile("r1.json");	
	@Rule
	public ResourceFile ressHData1 = new ResourceFile("hdata1.json");	
	
	protected HyperDataView view;
	
	@Before
	public void setup() {
		view = new HyperDataGsonBuilder().create();
	}

	@Test
	public void testReadBlankIS() throws IOException {
		Assert.assertTrue(ressZeroLength.getFile().exists());
		Assert.assertTrue(ressBlank.getFile().exists());
		
		Assert.assertNull(view.read(ressZeroLength.createInputStream(), HyperMap.class, 1));
		Assert.assertNull(view.read(ressBlank.createInputStream(), HyperMap.class, 1));
	}

	@Test
	public void testReadMetaIS() throws IOException {
		Assert.assertTrue(ressMeta.getFile().exists());
		HyperMap hdata = view.read(ressMeta.createInputStream(), HyperMap.class, 1);
		Assert.assertNotNull(hdata);
		Assert.assertNotNull(hdata.getMetadata());
		Assert.assertEquals(0, hdata.size());
		Assert.assertNotNull(hdata.getMetadata().get("content-version"));
		Assert.assertEquals(Double.class, hdata.getMetadata().get("content-version").getClass());
		Assert.assertEquals(1.0, hdata.getMetadata().get("content-version"));
		
		Assert.assertNotNull(hdata.getMetadata().get("href"));
		Assert.assertEquals(String.class, hdata.getMetadata().get("href").getClass());
		Assert.assertEquals("/233232", hdata.getMetadata().get("href"));
	}

	@Test
	public void testReadSimpleIS() throws IOException {
		Assert.assertTrue(ressR1.getFile().exists());
		HyperMap hdata = view.read(ressR1.createInputStream(), HyperMap.class, 1);
		Assert.assertNotNull(hdata);
		Assert.assertNull(hdata.getMetadata());
		Assert.assertEquals(3, hdata.size());
		
		Assert.assertNotNull(hdata.get("a"));
		Assert.assertEquals(Double.class, hdata.get("a").getClass());
		Assert.assertEquals(3.0, hdata.get("a"));

		Assert.assertNotNull(hdata.get("b"));
		Assert.assertEquals(String.class, hdata.get("b").getClass());
		Assert.assertEquals("c", hdata.get("b"));

		Assert.assertNotNull(hdata.get("d"));
		Assert.assertEquals(Boolean.class, hdata.get("d").getClass());
		Assert.assertEquals(Boolean.TRUE, hdata.get("d"));
	}

	@Test
	public void testReadHData1IS() throws IOException {
		Assert.assertTrue(ressHData1.getFile().exists());
		HData1 hdata = view.read(ressHData1.createInputStream(), HData1.class, 1);
		Assert.assertNotNull(hdata);
		Assert.assertNotNull(hdata.getMetadata());
		Assert.assertEquals("/y", hdata.getMetadata().getHref());
		
		Assert.assertNotNull(hdata.getA());
		Assert.assertEquals((Double)10.0d, (Double)(hdata.getA()));

		Assert.assertNotNull(hdata.getB());
		Assert.assertEquals(Boolean.TRUE, hdata.getB());

		Assert.assertNotNull(hdata.getC());
		Assert.assertNotNull(hdata.getC().getMetadata());
		Assert.assertEquals("/x", hdata.getC().getMetadata().getHref());
		Assert.assertNotNull(hdata.getC().getB());
		Assert.assertEquals(Boolean.FALSE, hdata.getC().getB());
		
		Assert.assertNotNull(hdata.getD());
		Assert.assertEquals("embedded", hdata.getD().getX());

	}

	@Test
	public void testReadHData2IS() throws IOException {
		Assert.assertTrue(ressHData1.getFile().exists());
		HData2 hdata = view.read(ressHData1.createInputStream(), HData2.class, 1);
		Assert.assertNotNull(hdata);
		Assert.assertNotNull(hdata.getMetadata());
		Assert.assertEquals("/y", hdata.getMetadata().get("href"));		
	}

	@Test
	public void testReadHData3IS() throws IOException {
		Assert.assertTrue(ressHData1.getFile().exists());
		HData3 hdata = view.read(ressHData1.createInputStream(), HData3.class, 1);
		Assert.assertNotNull(hdata);
		Assert.assertNotNull(hdata.getMetaX());
		Assert.assertEquals("/y", hdata.getMetaX().getHref());
		Assert.assertEquals("application/json", hdata.getMetaX().getContentType());
	}

	@Test
	public void testReadBlankString() {
		Assert.assertNull(view.read("", HyperMap.class, 1));
		Assert.assertNull(view.read((String)null, HyperMap.class, 1));
		Assert.assertNull(view.read("     ", HyperMap.class, 1));
		Assert.assertNull(view.read("\t\n", HyperMap.class, 1));
	}

	@Test
	public void testReadString() {
		
		HyperMap data = view.read("{}", HyperMap.class, 1);
		Assert.assertNotNull(data);
		Assert.assertNull(data.getMetadata());
		Assert.assertEquals(0, data.size());
		
		HyperMap data2 = view.read("{\"_meta\":false}", HyperHashMap.class, 1);
		Assert.assertNotNull(data2);
		Assert.assertNotNull(data2.getMetadata());
		Assert.assertNotNull(data2.getMetadata().get("meta"));
		Assert.assertEquals(Boolean.class, data2.getMetadata().get("meta").getClass());
		Assert.assertEquals(Boolean.FALSE, data2.getMetadata().get("meta"));
	}

	@Test
	public void testReadInvalidString() {

		try {
			view.read("x", HyperMap.class, 1);
			Assert.assertFalse(true);
		} catch (JsonParseException e) {
			
		}

		try {
			view.read("false", HyperMap.class, 1);
			Assert.assertFalse(true);
		} catch (JsonParseException e) {
			
		}
	}

	@Test
	public void testStringWriteNull() {		
		Assert.assertEquals("null", view.write(null, 1));
	}

	@Test
	public void testStringWriteEmpty() {		
		Assert.assertEquals("{}", view.write(new HyperHashMap(), 1));
	}
	
	@Test
	public void testStringWriteHashMap() {
		HyperHashMap map = new HyperHashMap();
		map.setMetadata(new HashMap<String, Object>());
		map.getMetadata().put("href", "/a/1");
		map.put("first-name", "John");
		map.put("last-name", "Doe");
		Assert.assertEquals("{\"_href\":\"/a/1\",\"first-name\":\"John\",\"last-name\":\"Doe\"}", view.write(map, 1));
	}

	@Test
	public void testStringWriteHData1() {
		HData1 hdata = new HData1();
		hdata.setMetadata(new Meta());
		hdata.getMetadata().setHref("/a/2");
		hdata.getMetadata().put("accept", "application/json");
		hdata.setA(11.1);
		hdata.setB(true);
		hdata.setC(new HData1());
		hdata.getC().setA(3d);
		hdata.getC().setB(false);
		Assert.assertEquals("{\"_accept\":\"application/json\",\"_href\":\"/a/2\",\"_profile\":[\"http://sprintapi.org/hyperdata/test/hdata1\"],\"a\":11.1,\"b\":true,\"c\":{\"_profile\":[\"http://sprintapi.org/hyperdata/test/hdata1\"],\"a\":3.0,\"b\":false}}", view.write(hdata, 1));
	}

	@Test
	public void testStringWriteHData4() {
		HData4 hdata = new HData4();
		hdata.setA("x");
		Assert.assertEquals("{\"_profile\":[\"http://sprintapi.org/hyperdata/test/hdata4\",\"http://sprintapi.org/hyperdata/test/hdata4I\"],\"a\":\"x\"}", view.write(hdata, 1));
	}

	@Test
	public void testStringWriteHData3() {
		HData3 hdata = new HData3();
		hdata.setMetaX(new Meta1());
		hdata.getMetaX().setHref("/a/1");
		Assert.assertEquals("{\"_href\":\"/a/1\",\"_profile\":[\"http://sprintapi.org/hyperdata/test/hdata3\",\"http://sprintapi.org/hyperdata/test/hdata3base\"]}", view.write(hdata, 1));
	}
}
