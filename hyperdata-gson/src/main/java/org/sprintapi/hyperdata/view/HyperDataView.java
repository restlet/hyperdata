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
package org.sprintapi.hyperdata.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import org.sprintapi.hyperdata.Patch;


public interface HyperDataView {

	<T> T read(InputStream stream, Type type, int depth);
	<T> T read(InputStream stream, Class<T> clazz, int depth);
	<T> T read(String string, Class<T> clazz, int depth);
	<T> T read(Reader reader, Class<T> clazz, int depth);
	
	<T> Patch<T> readPatch(InputStream stream, Type type, int depth) throws IOException;
	<T> Patch<T> readPatch(InputStream stream, Class<T> clazz, int depth) throws IOException;
	<T> Patch<T> readPatch(String string, Class<T> clazz, int depth);
	<T> Patch<T> readPatch(Reader reader, Class<T> clazz, int depth) throws IOException;
	
	void write(Object hdata, OutputStream stream, int depth);
	void write(Object hdata, Writer writer, int depth);
	
	String write(Object data, int depth);
}
