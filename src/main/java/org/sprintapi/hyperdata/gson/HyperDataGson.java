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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.sprintapi.hyperdata.HyperData;
import org.sprintapi.hyperdata.HyperMap;
import org.sprintapi.hyperdata.view.HyperDataView;

import com.google.gson.Gson;

public class HyperDataGson implements HyperDataView {

	protected final Gson gson;
	
	public HyperDataGson(Gson gson) {
		super();
		this.gson = gson;
	}

	@Override
	public <T extends HyperData<?>> T read(InputStream stream, Class<T> clazz) {
		return gson.fromJson(new InputStreamReader(stream), clazz);
	}

	@Override
	public void write(HyperMap hdata, OutputStream stream) {
		gson.toJson(hdata, new OutputStreamWriter(stream));		
	}

	@Override
	public <T extends HyperData<?>> T read(String string, Class<T> clazz) {
		return gson.fromJson(string, clazz);
	}

	@Override
	public String write(HyperData<?> data) {
		return gson.toJson(data);
	}	
}
