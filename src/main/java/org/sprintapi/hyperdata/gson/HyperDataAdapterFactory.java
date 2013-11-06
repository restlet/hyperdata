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

import org.sprintapi.hyperdata.HyperData;
import org.sprintapi.hyperdata.HyperMap;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class HyperDataAdapterFactory extends ReflectiveTypeAdapterFactory implements TypeAdapterFactory {

	ConstructorConstructor constructorConstructor;
	FieldNamingStrategy fieldNamingPolicy;
	Excluder excluder;
	
	public HyperDataAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
		super(constructorConstructor, fieldNamingPolicy, excluder);
		this.constructorConstructor = constructorConstructor;
		this.fieldNamingPolicy = fieldNamingPolicy;
		this.excluder = excluder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		
		Class<? super T> raw = type.getRawType();

	    if (!Object.class.isAssignableFrom(raw) || !HyperData.class.isAssignableFrom(raw) || HyperMap.class.isAssignableFrom(raw)) {
	    	return null; // it's a primitive or isn't hyperdata
	    }
	        
	    ObjectConstructor<T> constructor = constructorConstructor.get(type);	    
	    return (TypeAdapter<T>) new HyperDataTypeAdapter(constructorConstructor, (ObjectConstructor<HyperData<Object>>)constructor, getBoundFields(gson, type, raw), gson, this);
	}
}
