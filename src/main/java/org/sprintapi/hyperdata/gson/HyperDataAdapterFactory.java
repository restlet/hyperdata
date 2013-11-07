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

import java.lang.reflect.Method;

import org.apache.commons.lang.WordUtils;
import org.sprintapi.hyperdata.HyperData;
import org.sprintapi.hyperdata.HyperMap;
import org.sprintapi.hyperdata.Metadata;

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

	    if (!Object.class.isAssignableFrom(raw) || HyperMap.class.isAssignableFrom(raw)) {
	    	return null; // it's a primitive or HyperMap
	    }

	    if (!raw.isAnnotationPresent(HyperData.class)) {
	    	return null; // it's not hyperdata
	    }

	    MetadataAccess metadataAccess = new MetadataAccess();
	    
	    for (Method method : raw.getMethods()) {
	    	if (method.isAnnotationPresent(Metadata.class)) {
	    		if (method.getName().startsWith("get")) {
	    			metadataAccess.getter = method;
	    			metadataAccess.fieldName = method.getName().substring(3);
	    			
	    		} else if (method.getName().startsWith("set")) {
	    			metadataAccess.setter = method;
	    			metadataAccess.fieldName = method.getName().substring(3);	    			
	    		}	    		
	    	}
	    }
	    
	    if (metadataAccess.fieldName != null) {
	    	if (metadataAccess.getter == null) {
	    		for (Method method : raw.getMethods()) {
	    			if (method.getName().equals("get" + metadataAccess.fieldName)) {
	    				metadataAccess.getter = method;
	    				break;
	    			}
	    		}
	    	} else if (metadataAccess.setter == null) {
	    		for (Method method : raw.getMethods()) {
	    			if (method.getName().equals("set" + metadataAccess.fieldName)) {
	    				metadataAccess.setter = method;
	    				break;
	    			}
	    		}	    		
	    	}
	    	metadataAccess.fieldName = WordUtils.uncapitalize(metadataAccess.fieldName);
	    }

	    ObjectConstructor<T> constructor = constructorConstructor.get(type);	    
	    return (TypeAdapter<T>) new HyperDataTypeAdapter(metadataAccess, constructorConstructor, (ObjectConstructor)constructor, getBoundFields(gson, type, raw), gson, this);
	}
	
	protected class MetadataAccess {
		public String fieldName;
		public Method getter;
		public Method setter;
	}
}
