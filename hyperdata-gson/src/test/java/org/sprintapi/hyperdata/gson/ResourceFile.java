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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.rules.ExternalResource;


public class ResourceFile extends ExternalResource {
    
	String res;
    File file = null;
    InputStream stream;

    public ResourceFile(String res) {
        this.res = res;
    }

    public File getFile() throws IOException {
        if (file == null) {
            createFile();
        }
        return file;
    }

    public InputStream getInputStream() {
        return stream;
    }

    public InputStream createInputStream() {
        return getClass().getResourceAsStream(res);
    }

    public String getContent() throws IOException {
        return getContent("utf-8");
    }

    public String getContent(String charSet) throws IOException {
    	InputStreamReader reader = new InputStreamReader(createInputStream(), Charset.forName(charSet));
    	
    	char[] tmp = new char[4096];
        StringBuilder b = new StringBuilder();
        
        try {
            while (true) {
                int len = reader.read(tmp);
                if (len < 0) {
                    break;
                }
                b.append(tmp, 0, len);
            }
            reader.close();
        } finally {
            reader.close();
        }
        return b.toString();
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        stream = getClass().getResourceAsStream(res);
    }

    @Override
    protected void after() {
        try {
        	if (stream != null) {
        		stream.close();
        	}
        } catch (IOException e) {
            // ignore
        }
        
        if (file != null) {
            file.delete();
        }
        super.after();
    }

    private void createFile() throws IOException {
        InputStream stream = getClass().getResourceAsStream(res);
        if (stream == null) {
        	return;
        }
        file = new File(".",res);
        
        try {
            file.createNewFile();
            FileOutputStream ostream = null;
            try {
                ostream = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                while (true) {
                    int len = stream.read(buffer);
                    if (len < 0) {
                        break;
                    }
                    ostream.write(buffer, 0, len);
                }
            } finally {
                if (ostream != null) {
                    ostream.close();
                }
            }
        } finally {
            stream.close();
        }
    }
}