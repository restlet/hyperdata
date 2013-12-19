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
package org.sprintapi.gwt.converter.client.json.testdata;

import org.sprintapi.gwt.converter.client.array.ArrayAdapter;
import org.sprintapi.gwt.converter.client.bean.BeanAdapter;

import com.google.gwt.core.client.GWT;


public class TestMapping {

	public interface BasicTypesAdapter extends BeanAdapter<TestObject> {}
	public static final BasicTypesAdapter BASIC_TYPES_ADAPTER = GWT.create(BasicTypesAdapter.class);
	
	public interface StringArrayAdapter extends ArrayAdapter<String[][][][]> {};
	public static final StringArrayAdapter STRING_ARRAY_ADAPTER = GWT.create(StringArrayAdapter.class);

	public interface BasicBeanArrayAdapter extends ArrayAdapter<TestObject[][]> {};
	public static final BasicBeanArrayAdapter BASIC_BEAN_ARRAY_ADAPTER = GWT.create(BasicBeanArrayAdapter.class);
}
