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
package org.sprintapi.gwt.converter.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.sprintapi.gwt.converter.client.json.impl.GwtJsonReaderTest;

import com.google.gwt.junit.tools.GWTTestSuite;

public class GwtJsonConverterSuite extends GWTTestSuite {

	public static Test suite() {
		 TestSuite suite = new TestSuite("GWT tests for JSON Converter");
	
		 suite.addTestSuite(GwtBeanAdapterGeneratorTest.class);
		 suite.addTestSuite(GwtStringArrayAdapterGeneratorTest.class);
		 suite.addTestSuite(GwtBeanArrayAdapterGeneratorTest.class);
		 suite.addTestSuite(GwtJsonReaderTest.class);
		 return suite;
	}
}
