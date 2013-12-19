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
package org.sprintapi.hyperdata.gwt;

import java.io.PrintWriter;

import org.sprintapi.hyperdata.gwt.client.array.ArrayAdapter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;


public class ArrayAdapterGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String requestedClass) throws UnableToCompleteException {
		
		TypeOracle typeOracle = context.getTypeOracle( );
		
        JClassType objectType = typeOracle.findType(requestedClass);
        if (objectType == null) {
            logger.log(TreeLogger.ERROR, "Could not find type: " + requestedClass);
            throw new UnableToCompleteException();
        }

        String implTypeName = objectType.getSimpleSourceName() + "Impl";
        
        String implPackageName = objectType.getPackage().getName();
 
        JClassType[] implementedTypes = objectType.getImplementedInterfaces();
 
        // Can only implement one interface
        if ((implementedTypes == null) 
        		|| (implementedTypes.length != 1) 
        		|| !implementedTypes[0].getQualifiedSourceName().equals(ArrayAdapter.class.getName())
        		) {
            logger.log(TreeLogger.ERROR, "The type: " + requestedClass + " must implement only one interface: " + ArrayAdapter.class.getName());
            throw new UnableToCompleteException();
        }
 
        // Get parameterized type
        JParameterizedType parameterType = implementedTypes[0].isParameterized();
        
        if (parameterType == null) {
            logger.log(TreeLogger.ERROR, "The type: " + parameterType + " must implement only one parameterized interface: " + ArrayAdapter.class.getName());
            throw new UnableToCompleteException();
        }
        
        JClassType parameterizedType = parameterType.getTypeArgs()[0];
        
        JArrayType arrayType = parameterizedType.isArray();
        if (arrayType == null) {
            logger.log(TreeLogger.ERROR, "The type: " + parameterType + " is not an array.");
            throw new UnableToCompleteException();        	
        }

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(implPackageName, implTypeName);
 
        composerFactory.addImplementedInterface(objectType.getQualifiedSourceName());
 
        PrintWriter printWriter = context.tryCreate(logger, implPackageName,implTypeName);
        
        if (printWriter != null) {
            SourceWriter sourceWriter = composerFactory.createSourceWriter(context, printWriter);
            
            composeDimesionMethod(sourceWriter, arrayType);
            composeSetMethod(sourceWriter, arrayType);
            composeGetMethod(sourceWriter, parameterizedType);
            composeGetArrayClassMethod(sourceWriter, arrayType);
            composeCreateInstanceMethod(sourceWriter, arrayType);
            sourceWriter.commit(logger);
        }
        return implPackageName + "." + implTypeName;
	}

	//void set(Object array, int dim, int index, Object value) throws ConverterException;
    private void composeSetMethod(SourceWriter sourceWriter, JArrayType parameterizedType) {    	 
        sourceWriter.print("public void set(java.lang.Object array, int dimension, int index, java.lang.Object value) throws org.sprintapi.gwt.converter.client.ConverterException {");
        
        sourceWriter.print("	if (dimension == 0) {");
        sourceWriter.print(" 		((" + parameterizedType.getLeafType().getQualifiedSourceName() + "[])array)[index] = (" + parameterizedType.getLeafType().getQualifiedSourceName() + ")value;" );
        String suffix = "";
        for (int i=0; i < parameterizedType.getRank(); i++) {
        	suffix += "[]";
            sourceWriter.print("	} else if (dimension == " + (i+1) + ") {");
            sourceWriter.print(" 		((" + parameterizedType.getLeafType().getQualifiedSourceName() + "[]" + suffix + ")array)[index] = (" + parameterizedType.getLeafType().getQualifiedSourceName() + suffix + ")value;" );
        }        	
        sourceWriter.print("	}");
        sourceWriter.print("}");  
        
        Object o = new Integer[10][];
        
        ((Integer[][])o)[0] = new Integer[4];
        
    }
    
	//Object get(Object array, int dim, int index) throws ConverterException;
    private void composeGetMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public java.lang.Object get(java.lang.Object object, int dim, int index) throws org.sprintapi.gwt.converter.client.ConverterException {");

//        if (parameterizedType.getMethods() != null) {
//        	int count = 0;
//        	for (JMethod method : parameterizedType.getMethods()) {
//        		if (method.isAnnotationPresent(Transient.class)) {
//        			continue;
//        		}
//        		if (method.getName().startsWith("get")) {
//        			if (count > 0) {
//        				sourceWriter.print(" else ");
//        			}
//        			sourceWriter.print("name = java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);");
//        			sourceWriter.print("if (\"" + method.getName().substring("get".length()) + "\".equals(name)) {");
//        			//TODO check the return type
//        			//TODO isPrimitive
//        			sourceWriter.print("  return object." + method.getName() + "();");
//        			sourceWriter.print("}"); 
//        			count += 1;
//        		}
//        	}
//        }
        sourceWriter.print(" return null;");
        sourceWriter.print("}"); 
    }

	//Class<?> getArrayClass(int dimension);
    private void composeGetArrayClassMethod(SourceWriter sourceWriter, JArrayType parameterizedType) {    	 
        sourceWriter.print("public java.lang.Class<?> getArrayClass(int dimension) {");
        sourceWriter.print("	if (dimension == 0) {");
        sourceWriter.print("		return " + parameterizedType.getLeafType().getQualifiedSourceName() + ".class;");
        
        String suffix = "";
        for (int i=0; i < parameterizedType.getRank(); i++) {
        	suffix += "[]";
            sourceWriter.print("	} else if (dimension == " + (i+1) + ") {");
            sourceWriter.print("		return " + parameterizedType.getLeafType().getQualifiedSourceName() + suffix + ".class;");
        }        	

        sourceWriter.print("	}");
        sourceWriter.print("  return null;");
        sourceWriter.print("}");  
    }
    
	//<A> A createInstance(int length, int dimension);
    private void composeCreateInstanceMethod(SourceWriter sourceWriter, JArrayType parameterizedType) {    	 
        sourceWriter.print("public java.lang.Object createInstance(int length, int dimension) {");
        
        sourceWriter.print("	if (dimension == 0) {");
        
        String suffix = "";
        for (int i=0; i < parameterizedType.getRank(); i++) {
            sourceWriter.print("	} else if (dimension == " + (i+1) + ") {");
            sourceWriter.print("		return new " + parameterizedType.getLeafType().getQualifiedSourceName() +  "[length]" + suffix + ";");
        	suffix += "[]";
        }        

        sourceWriter.print("	}");      
        sourceWriter.print("  return null;"); 
        sourceWriter.print("}");  
    }

	//int dimension();
    private void composeDimesionMethod(SourceWriter sourceWriter, JArrayType parameterizedType) {    	 
        sourceWriter.print("public int maxDimension() {");
        sourceWriter.print("  return " + parameterizedType.getRank() + ";");
        sourceWriter.print("}");  
    }

}
