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
import java.lang.annotation.Annotation;

import org.sprintapi.hyperdata.HyperDataContainer;
import org.sprintapi.hyperdata.HyperDataIgnore;
import org.sprintapi.hyperdata.MetadataContainer;
import org.sprintapi.hyperdata.gwt.client.bean.BeanAdapter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;


public class BeanAdapterGenerator extends Generator {

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
        		|| !implementedTypes[0].getQualifiedSourceName().equals(BeanAdapter.class.getName())
        		) {
            logger.log(TreeLogger.ERROR, "The type: " + requestedClass + " must implement only one interface: " + BeanAdapter.class.getName());
            throw new UnableToCompleteException();
        }
 
        // Get parameterized type
        JParameterizedType parameterType = implementedTypes[0].isParameterized();
        
        if (parameterType == null) {
            logger.log(TreeLogger.ERROR, "The type: " + requestedClass + " must implement only one parameterized interface: " + BeanAdapter.class.getName());
            throw new UnableToCompleteException();
        }
        
        JClassType parameterizedType = parameterType.getTypeArgs()[0];

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(implPackageName, implTypeName);
 
        composerFactory.addImplementedInterface(objectType.getQualifiedSourceName());
 
        PrintWriter printWriter = context.tryCreate(logger, implPackageName, implTypeName);
        
        if (printWriter != null) {
            SourceWriter sourceWriter = composerFactory.createSourceWriter(context, printWriter);
            
            composeSetPropertyValueMethod(sourceWriter, parameterizedType);
            composeGetPropertyValueMethod(sourceWriter, parameterizedType);
            composeGetPropertiesMethod(sourceWriter, parameterizedType);
            composeGetBeanClassMethod(sourceWriter, parameterizedType);
            composeGetBeanAttributesMethod(sourceWriter, parameterizedType);
            composeCreateInstanceMethod(sourceWriter, parameterizedType);
            sourceWriter.commit(logger);
        }
        return implPackageName + "." + implTypeName;
	}
	
    private void composeSetPropertyValueMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public void setPropertyValue(" + parameterizedType.getQualifiedSourceName() + " object, String name, java.lang.Object value) throws org.sprintapi.hyperdata.gwt.client.AdapterException {");
        if (parameterizedType.getInheritableMethods() != null) {
        	int count = 0;
        	for (JMethod method : parameterizedType.getInheritableMethods()) {
        		if (method.isAnnotationPresent(HyperDataIgnore.class)) {
        			continue;
        		}
        		if (method.isPublic() && method.getName().startsWith("set")) {
        			if (count > 0) {
        				sourceWriter.print(" else ");
        			}
        			sourceWriter.print("name = java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);");
        			sourceWriter.print("if (\"" + method.getName().substring("get".length()) + "\".equals(name)) {");
        			//TODO check value type
        			//TODO isPrimitive
        			sourceWriter.print("  object." + method.getName() + "((" + method.getParameterTypes()[0].getQualifiedSourceName() + ")value);");
        			sourceWriter.print("  return;");
        			sourceWriter.print("}"); 
        			count += 1;
        		}
        	}
        }
//        sourceWriter.print("  throw new org.sprintapi.gwt.converter.client.ConverterException(\"There is no getter for '\" + name  + \"'.\");");
        sourceWriter.print("}"); 
    }
    
    private void composeGetPropertyValueMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public java.lang.Object getPropertyValue(" + parameterizedType.getQualifiedSourceName() + " object, String name) throws org.sprintapi.hyperdata.gwt.client.AdapterException {");

        if (parameterizedType.getInheritableMethods() != null) {
        	int count = 0;
        	for (JMethod method : parameterizedType.getInheritableMethods()) {
        		if (method.isAnnotationPresent(HyperDataIgnore.class)) {
        			continue;
        		}
        		if (method.isPublic() && method.getName().startsWith("get")) {
        			if (count > 0) {
        				sourceWriter.print(" else ");
        			}
        			sourceWriter.print("name = java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);");
        			sourceWriter.print("if (\"" + method.getName().substring("get".length()) + "\".equals(name)) {");
        			//TODO check the return type
        			//TODO isPrimitive
        			sourceWriter.print("  return object." + method.getName() + "();");
        			sourceWriter.print("}"); 
        			count += 1;
        		}
        	}
        }
        sourceWriter.print(" return null;");
        //sourceWriter.print("  throw new org.sprintapi.gwt.converter.client.ConverterException(\"There is no getter for '\" + name  + \"'.\");");
        sourceWriter.print("}"); 
    }

    private void composeGetPropertiesMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptor[] getProperties() {");
    	sourceWriter.print("  return new org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptor[]{");
    	
    	int count = 0;
        if (parameterizedType.getInheritableMethods() != null) {
        	for (JMethod method : parameterizedType.getInheritableMethods()) {
        		count = doComposeGetPropertiesMethod(sourceWriter, method, count);
        	}
        }

        sourceWriter.print("  };");
        sourceWriter.print("}");  
    }

    private int doComposeGetPropertiesMethod(SourceWriter sourceWriter, JMethod method, int count) {
		if (method.isAnnotationPresent(HyperDataIgnore.class) || method.getName().equals("getClass")) {
			return count;
		}
		if (method.isPublic() && method.getName().startsWith("get")) {
			if (count > 0) {
				sourceWriter.print(",");
			}
			String methodName = method.getName().substring("get".length());
			if (methodName.trim().isEmpty()) {
				return count;
			}
			methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1); 
			
			sourceWriter.print("new org.sprintapi.hyperdata.gwt.client.bean.BeanPropertyDescriptorImpl("
					+ "\"" + methodName + "\", "
					+ method.getReturnType().getQualifiedSourceName() +".class"
					+ ", null");		//TODO kind
			
			MetadataContainer meta = method.getAnnotation(MetadataContainer.class);
			if (meta != null) {
			
				sourceWriter.print(", new org.sprintapi.hyperdata.gwt.client.bean.HyperBeanPropertyAttributes(");
				sourceWriter.print(")");
			} else {
				sourceWriter.print(", null");
			}
			
			sourceWriter.print(")");
			count += 1;
		}
		return count;
    }
    
    private void composeGetBeanClassMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public Class<" + parameterizedType.getQualifiedSourceName()+ "> getBeanClass() {");
        sourceWriter.print("  return " + parameterizedType.getQualifiedSourceName() + ".class;"); 
        sourceWriter.print("}");  
    }

    private void composeGetBeanAttributesMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public org.sprintapi.hyperdata.gwt.client.bean.HyperBeanAttributes getAttributes() {");
        
        HyperDataContainer hyperbean = parameterizedType.getAnnotation(HyperDataContainer.class); 
        if (hyperbean != null) {
        	sourceWriter.print("  return new org.sprintapi.hyperdata.gwt.client.bean.HyperBeanAttributes(");
        	if (hyperbean.profile() != null) {
        		sourceWriter.print("new String[]{");
        		boolean next = false;
        		for (String profile : hyperbean.profile()) {
        			if (next) {
        				sourceWriter.print(",");
        			}
        			sourceWriter.print("\"" + profile +"\"");
        			next = true;
        		}
        		sourceWriter.print("}");
        	}
        	sourceWriter.print(");");
        } else {
            sourceWriter.print("  return null;");
        }
        sourceWriter.print("}");  
    }

    private void composeCreateInstanceMethod(SourceWriter sourceWriter, JClassType parameterizedType) {    	 
        sourceWriter.print("public " + parameterizedType.getQualifiedSourceName()+ " createInstance() {");
        sourceWriter.print("  return new " + parameterizedType.getQualifiedSourceName() + "();");
        sourceWriter.print("}");  
    }

}
