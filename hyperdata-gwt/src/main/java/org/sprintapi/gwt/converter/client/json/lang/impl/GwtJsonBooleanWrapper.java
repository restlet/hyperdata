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
package org.sprintapi.gwt.converter.client.json.lang.impl;

import org.sprintapi.gwt.converter.client.json.lang.JsonBoolean;

import com.google.gwt.json.client.JSONBoolean;

public class GwtJsonBooleanWrapper extends GwtJsonValueWrapper implements JsonBoolean {

	private final JSONBoolean bool;
	
	public GwtJsonBooleanWrapper(JSONBoolean bool) {
		super(bool);
		this.bool = bool;
	}

	@Override
	public Boolean booleanValue() {
		return (bool != null) ? bool.booleanValue() : null;
	}
}
