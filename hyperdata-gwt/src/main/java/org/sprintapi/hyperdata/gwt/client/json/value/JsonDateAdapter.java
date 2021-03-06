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
package org.sprintapi.hyperdata.gwt.client.json.value;

import java.util.Date;

import org.sprintapi.hyperdata.gwt.client.AdapterException;
import org.sprintapi.hyperdata.gwt.client.json.JsonValueAdapter;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonString;
import org.sprintapi.hyperdata.gwt.client.json.lang.JsonValue;
import org.sprintapi.hyperdata.gwt.client.json.lang.impl.JsonStringImpl;

import com.google.gwt.i18n.client.DateTimeFormat;

public class JsonDateAdapter implements JsonValueAdapter<Date> {

	protected static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	private final String mask;
	
	public JsonDateAdapter() {
		this(ISO_DATETIME);
	}
	
	public JsonDateAdapter(final String mask) {
		super();
		this.mask = mask;
	}
	
	@Override
	public Date read(JsonValue value) throws AdapterException {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		JsonString jsonString = value.isString();
		if (jsonString == null) {
			throw new IllegalArgumentException("The value '" + value + "' argument is not " + JsonString.class);
		}
		try {
			return DateTimeFormat.getFormat(mask).parse(jsonString.stringValue());
		} catch (IllegalArgumentException e) {
			throw new AdapterException(e);
		}
	}

	@Override
	public JsonValue write(Date value) {
		if (value == null) {
			throw new IllegalArgumentException("The value argument cannot be a null.");
		}
		return new JsonStringImpl(DateTimeFormat.getFormat(mask).format(value));
	}

	@Override
	public Class<Date> getJavaClass() {
		return Date.class;
	}
}
