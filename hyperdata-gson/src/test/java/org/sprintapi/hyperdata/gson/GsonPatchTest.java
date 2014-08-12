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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.sprintapi.hyperdata.Patch;
import org.sprintapi.hyperdata.view.HyperDataView;


@RunWith(JUnit4.class)
public class GsonPatchTest {

	@Rule
	public ResourceFile ressPatch1 = new ResourceFile("patch1.json");	
	
	protected HyperDataView view;
	
	@Before
	public void setup() {
		view = new HyperDataGsonBuilder().create();
	}
	
	@Test
	public void testReadIS() throws IOException {
		Assert.assertTrue(ressPatch1.getFile().exists());
		
		Patch<HData1> patch = view.readPatch(ressPatch1.createInputStream(), HData1.class, 1);
		Assert.assertNotNull(patch);
		
		HData1 hdata1 = new HData1();
		hdata1.setA(1.0);
		hdata1.setB(true);
		
		HData1 hdata2 = new HData1();
		hdata2.setA(2.0);
		hdata2.setB(true);
		
		hdata1.setC(hdata2);
		
		HData1 result = patch.patch(hdata1);
		
		Assert.assertNotNull(result);
		Assert.assertNull(result.getA());
		Assert.assertEquals(true, result.getB());
		Assert.assertNotNull(result.getC());
		Assert.assertEquals((Double)2.0, result.getC().getA());
		Assert.assertNull(result.getC().getB());
		Assert.assertNotNull(result.getD());

	}
	
	@Test
	public void test1() throws IOException {
		
		ABC data = view.read("{\"a\":\"b\"}", ABC.class, 1);
		Assert.assertNotNull(data);
		Patch<ABC> patch = view.readPatch("{\"a\":\"c\"}", ABC.class, 1);
		Assert.assertNotNull(patch);
		
		ABC result = patch.patch(data);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getA());
		Assert.assertEquals("c", result.getA());
	}

	@Test
	public void test2() throws IOException {
		
		ABC data = view.read("{\"a\":\"b\"}", ABC.class, 1);
		Assert.assertNotNull(data);
		Patch<ABC> patch = view.readPatch("{\"b\":\"c\"}", ABC.class, 1);
		Assert.assertNotNull(patch);
		
		ABC result = patch.patch(data);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getA());
		Assert.assertEquals("b", result.getA());
		Assert.assertNotNull(result.getB());
		Assert.assertEquals("c", result.getB());
	}

	@Test
	public void test3() throws IOException {
		
		ABC data = view.read("{\"a\":\"b\"}", ABC.class, 1);
		Assert.assertNotNull(data);
		Patch<ABC> patch = view.readPatch("{\"a\":null}", ABC.class, 1);
		Assert.assertNotNull(patch);
		
		ABC result = patch.patch(data);
		
		Assert.assertNotNull(result);
		Assert.assertNull(result.getA());
	}
	
	@Test
	public void test4() throws IOException {
		
		ABC data = view.read("{\"a\":\"b\",\"b\":\"c\"}", ABC.class, 1);
		Assert.assertNotNull(data);
		Patch<ABC> patch = view.readPatch("{\"a\":null}", ABC.class, 1);
		Assert.assertNotNull(patch);
		
		ABC result = patch.patch(data);
		
		Assert.assertNotNull(result);
		Assert.assertNull(result.getA());
		Assert.assertNotNull(result.getB());
		Assert.assertEquals("c", result.getB());
	}
}
