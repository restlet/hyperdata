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

import org.sprintapi.hyperdata.HyperHashMap;
import org.sprintapi.hyperdata.HyperMap;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;

public class HyperDataGsonBuilder extends GsonBuilder {

	@Override
	protected void doCreate(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
		registerTypeAdapterFactory(new HyperDataAdapterFactory(constructorConstructor, fieldNamingPolicy, excluder));
		registerTypeAdapter(HyperMap.class, new GsonHyperDataDeserializer());
		registerTypeAdapter(HyperMap.class, new GsonHyperDataSerializer());
		registerTypeAdapter(HyperHashMap.class, new GsonHyperDataDeserializer());
		registerTypeAdapter(HyperHashMap.class, new GsonHyperDataSerializer());
	}
	
	public HyperDataGson build() {
		return new HyperDataGson(create());
	}
	
}
