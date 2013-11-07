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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.sprintapi.hyperdata.gson.HyperDataAdapterFactory.MetadataAccess;

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

public class HyperDataTypeAdapter extends TypeAdapter<Object> {

	private final ConstructorConstructor constructorConstructor;
    private final ObjectConstructor<Object> constructor;
    private final Map<String, BoundField> boundFields;
    private final Gson gson;
    private final ReflectiveTypeAdapterFactory reflectiveFactory;
    private final MetadataAccess metadataAccess;

	public HyperDataTypeAdapter(MetadataAccess metaAccess, ConstructorConstructor constructorConstructor, ObjectConstructor<Object> constructor, Map<String, BoundField> boundFields, Gson gson, ReflectiveTypeAdapterFactory reflectiveFactory) {
		super();
		this.constructorConstructor = constructorConstructor;
		this.constructor = constructor;
		this.boundFields = boundFields;
		this.gson = gson;
		this.reflectiveFactory = reflectiveFactory;
		this.metadataAccess = metaAccess;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void write(JsonWriter out, Object value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		
		out.beginObject();
		try {
			if ((metadataAccess != null) && (metadataAccess.fieldName != null)) {

				if (metadataAccess.getter == null) {
					//TODO
					throw new AssertionError();
				}
				
				Object metadata = metadataAccess.getter.invoke(value);

				
		        BoundField metadataField = boundFields.get(metadataAccess.fieldName);
		        if (metadataField != null && metadata != null) {
			        Map<String, BoundField> metaBoundFields = null;
					if (!Map.class.isAssignableFrom(metadata.getClass())) {
						metaBoundFields = reflectiveFactory.getBoundFields(gson, metadataField.type, metadataField.type.getRawType());
					}
					
	    			if (metaBoundFields != null) {
	    				for (BoundField boundField : metaBoundFields.values()) {
	    					if (boundField.serialized) {
	    						out.name("@".concat(boundField.name));
	    						boundField.write(out, metadata);
	    					}
	    				}
	
	    			} else {
	    				Map values = (Map)metadata;
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
			}
			
			for (BoundField boundField : boundFields.values()) {
				if (boundField.serialized && !boundField.name.equals(metadataAccess.fieldName)) {
					out.name(boundField.name);
					boundField.write(out, value);
				}
			}
		} catch (IllegalAccessException e) {
			throw new AssertionError();
		} catch (IllegalArgumentException e) {
			throw new AssertionError();
		} catch (InvocationTargetException e) {
			throw new AssertionError();
		}
		out.endObject();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}

        Object instance = constructor.construct();
        
        BoundField metadataField = null;
        if (metadataAccess != null) {
        	metadataField = boundFields.get(metadataAccess.fieldName);
        }
        
        Object meta = null;
        Map<String, BoundField> metaBoundFields = null;

        try {
        	in.beginObject();
        	while (in.hasNext()) {
        		String name = in.nextName();
	    		if ((name != null) && (metadataField != null) && (name.startsWith("@"))) {
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
	    			
	    		} else if ((name != null) && (!name.equals(metadataAccess.fieldName))) {
		            BoundField field = boundFields.get(name);
		            if (field == null || !field.deserialized) {
		              in.skipValue();
		            } else {
		              field.read(in, instance);
		            }
	    		}
        	}
        	if (metadataAccess.setter == null) {
        		//TODO
        		throw new AssertionError();
        	}
        	metadataAccess.setter.invoke(instance, meta);
        	
        } catch (IllegalStateException e) {
        	throw new JsonSyntaxException(e);
        } catch (IllegalAccessException e) {
        	throw new AssertionError(e);
        } catch (IllegalArgumentException e) {
        	throw new AssertionError(e);
		} catch (InvocationTargetException e) {
        	throw new AssertionError(e);
		}
        in.endObject();
        return instance;
	}
}
