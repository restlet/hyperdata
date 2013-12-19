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
package org.sprintapi.gwt.converter.client.array.impl;

import java.util.Date;

import org.sprintapi.gwt.converter.client.array.ArrayAdapter;

import com.google.gwt.core.client.GWT;

public class BuiltinArrayMapping {

	public interface StringArrayAdapter extends ArrayAdapter<String[][][][][][][][][][]> {};
	public static final StringArrayAdapter STRING_ARRAY_ADAPTER = GWT.create(StringArrayAdapter.class);

	public interface IntegerArrayAdapter extends ArrayAdapter<Integer[][][][][][][][][][]> {};
	public static final IntegerArrayAdapter INTEGER_ARRAY_ADAPTER = GWT.create(IntegerArrayAdapter.class);

	public interface LongArrayAdapter extends ArrayAdapter<Long[][][][][][][][][][]> {};
	public static final LongArrayAdapter LONG_ARRAY_ADAPTER = GWT.create(LongArrayAdapter.class);

	public interface FloatArrayAdapter extends ArrayAdapter<Float[][][][][][][][][][]> {};
	public static final FloatArrayAdapter FLOAT_ARRAY_ADAPTER = GWT.create(FloatArrayAdapter.class);

	public interface DoubleArrayAdapter extends ArrayAdapter<Double[][][][][][][][][][]> {};
	public static final DoubleArrayAdapter DOUBLE_ARRAY_ADAPTER = GWT.create(DoubleArrayAdapter.class);

	public interface BooleanArrayAdapter extends ArrayAdapter<Boolean[][][][][][][][][][]> {};
	public static final BooleanArrayAdapter BOOLEAN_ARRAY_ADAPTER = GWT.create(BooleanArrayAdapter.class);

	public interface DateArrayAdapter extends ArrayAdapter<Date[][][][][][][][][][]> {};
	public static final DateArrayAdapter DATE_ARRAY_ADAPTER = GWT.create(DateArrayAdapter.class);

}
