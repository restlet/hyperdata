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
import java.util.Map;

import org.sprintapi.hyperdata.HyperData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class HyperDataTypeAdapter extends TypeAdapter<HyperData<Object>> {

	private final ConstructorConstructor constructorConstructor;
    private final ObjectConstructor<HyperData<Object>> constructor;
    private final Map<String, BoundField> boundFields;
    private final Gson gson;
    private final ReflectiveTypeAdapterFactory reflectiveFactory;

	public HyperDataTypeAdapter(ConstructorConstructor constructorConstructor, ObjectConstructor<HyperData<Object>> constructor, Map<String, BoundField> boundFields, Gson gson, ReflectiveTypeAdapterFactory reflectiveFactory) {
		super();
		this.constructorConstructor = constructorConstructor;
		this.constructor = constructor;
		this.boundFields = boundFields;
		this.gson = gson;
		this.reflectiveFactory = reflectiveFactory;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void write(JsonWriter out, HyperData<Object> value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		
		out.beginObject();
		try {
			
			if (value.setMetadata() != null) {
				
		        BoundField metadataField = boundFields.get("metadata");
		        Map<String, BoundField> metaBoundFields = null;
				if (!Map.class.isAssignableFrom(value.setMetadata().getClass())) {
					metaBoundFields = reflectiveFactory.getBoundFields(gson, metadataField.type, metadataField.type.getRawType());
				}

    			if (metaBoundFields != null) {
    				for (BoundField boundField : metaBoundFields.values()) {
    					if (boundField.serialized) {
    						out.name("@".concat(boundField.name));
    						boundField.write(out, value.setMetadata());
    					}
    				}

    			} else {
    				Map values = (Map)value.setMetadata();
    				for (Object key : values.keySet()) {
    					Object v = values.get(key);
    					if (v != null) {
    						out.name("@".concat(key.toString()));
    	    				TypeAdapter ta = gson.getAdapter(v.getClass());
    	    				ta.write(out, v);
    					}
    				}
    			}
			}
			
			for (BoundField boundField : boundFields.values()) {
				if (boundField.serialized && !"metadata".equals(boundField.name)) {
					out.name(boundField.name);
					boundField.write(out, value);
				}
			}
		} catch (IllegalAccessException e) {
			throw new AssertionError();
		}
		out.endObject();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HyperData<Object> read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}

        HyperData<Object> instance = constructor.construct();
        
        BoundField metadataField = boundFields.get("metadata");
        
        Object meta = null;
        Map<String, BoundField> metaBoundFields = null;
        
        try {
        	in.beginObject();
        	while (in.hasNext()) {
        		String name = in.nextName();
	    		if ((name != null) && (name.startsWith("@"))) {
	    			if (meta == null) {
	    				meta = constructorConstructor.get(metadataField.type).construct();
	    				if (!Map.class.isAssignableFrom(meta.getClass())) {
	    					metaBoundFields = reflectiveFactory.getBoundFields(gson, metadataField.type, metadataField.type.getRawType());
	    				}
	    			}
	    			
	    			if (metaBoundFields != null) {
		    			BoundField field = metaBoundFields.get(name.substring(1));
		    			if (field == null || !field.deserialized) {
		    				in.skipValue();
		    			} else {
		    				field.read(in, meta);
		    			}
	    			} else {		    			
	    				TypeAdapter<Object > ta = gson.getAdapter(Object.class);
	    				Object value = ta.read(in);
	    				((Map)meta).put(name.substring(1), value);
	    			}
	    			
	    		} else if ((name != null) && (!name.equals("metadata"))) {
		            BoundField field = boundFields.get(name);
		            if (field == null || !field.deserialized) {
		              in.skipValue();
		            } else {
		              field.read(in, instance);
		            }
	    		}
        	}
        } catch (IllegalStateException e) {
        	throw new JsonSyntaxException(e);
        } catch (IllegalAccessException e) {
        	throw new AssertionError(e);
        }
        in.endObject();
        instance.getMetadata(meta);
        return instance;
	}
}
