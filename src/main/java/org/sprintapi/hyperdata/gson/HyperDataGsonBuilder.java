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

import java.lang.reflect.Type;

import org.sprintapi.hyperdata.HyperHashMap;
import org.sprintapi.hyperdata.HyperMap;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;

public class HyperDataGsonBuilder {

	private GsonBuilder builder = new GsonBuilder() {
		@Override
		protected void doCreate(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
			registerTypeAdapterFactory(new HyperDataAdapterFactory(constructorConstructor, fieldNamingPolicy, excluder));
			registerTypeAdapter(HyperMap.class, new GsonHyperDataDeserializer());
			registerTypeAdapter(HyperMap.class, new GsonHyperDataSerializer());
			registerTypeAdapter(HyperHashMap.class, new GsonHyperDataDeserializer());
			registerTypeAdapter(HyperHashMap.class, new GsonHyperDataSerializer());
		}
	};
	
	public HyperDataGson create() {
		return new HyperDataGson(builder.create());
	}

	/**
	 * Configures Gson to enable versioning support.
	 *
	 * @param ignoreVersionsAfter any field or type marked with a version higher than this value
     * are ignored during serialization or deserialization.
     * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
     */
	public HyperDataGsonBuilder setVersion(double ignoreVersionsAfter) {
		builder.setVersion(ignoreVersionsAfter);
		return this;
	}

	/**
	 * Configures Gson to excludes all class fields that have the specified modifiers. By default,
	 * Gson will exclude all fields marked transient or static. This method will override that
	 * behavior.
     *
     * @param modifiers the field modifiers. You must use the modifiers specified in the
     * {@link java.lang.reflect.Modifier} class. For example,
     * {@link java.lang.reflect.Modifier#TRANSIENT},
     * {@link java.lang.reflect.Modifier#STATIC}.
     * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
     */
	public HyperDataGsonBuilder excludeFieldsWithModifiers(int... modifiers) {
		builder.excludeFieldsWithModifiers(modifiers);
		return this;
	}

	/**
	 * Makes the output JSON non-executable in Javascript by prefixing the generated JSON with some
	 * special text. This prevents attacks from third-party sites through script sourcing. See
	 * <a href="http://code.google.com/p/google-gson/issues/detail?id=42">Gson Issue 42</a>
	 * for details.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder generateNonExecutableJson() {
		builder.generateNonExecutableJson();
		return this;
	}

	/**
	 * Configures Gson to exclude all fields from consideration for serialization or deserialization
	 * that do not have the {@link com.google.gson.annotations.Expose} annotation.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 */
	public HyperDataGsonBuilder excludeFieldsWithoutExposeAnnotation() {
		builder.excludeFieldsWithoutExposeAnnotation();
		return this;
	}

	/**
	 * Configure Gson to serialize null fields. By default, Gson omits all fields that are null
	 * during serialization.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.2
	 */
	public HyperDataGsonBuilder serializeNulls() {
		builder.serializeNulls();
		return this;
	}

	/**
	 * Enabling this feature will only change the serialized form if the map key is
	 * a complex type (i.e. non-primitive) in its <strong>serialized</strong> JSON
	 * form. The default implementation of map serialization uses {@code toString()}
	 * on the key; however, when this is called then one of the following cases
	 * apply:
	 *
	 * <h3>Maps as JSON objects</h3>
	 * For this case, assume that a type adapter is registered to serialize and
	 * deserialize some {@code Point} class, which contains an x and y coordinate,
	 * to/from the JSON Primitive string value {@code "(x,y)"}. The Java map would
	 * then be serialized as a {@link JsonObject}.
	 *
	 * <p>Below is an example:
	 * <pre>  {@code
	 *   Gson gson = new HyperDataGsonBuilder()
	 *       .register(Point.class, new MyPointTypeAdapter())
	 *       .enableComplexMapKeySerialization()
	 *       .create();
	 *
	 *   Map<Point, String> original = new LinkedHashMap<Point, String>();
	 *   original.put(new Point(5, 6), "a");
	 *   original.put(new Point(8, 8), "b");
	 *   System.out.println(gson.toJson(original, type));
	 * }</pre>
	 * The above code prints this JSON object:<pre>  {@code
	 *   {
	 *     "(5,6)": "a",
	 *     "(8,8)": "b"
	 *   }
	 * }</pre>
	 *
	 * <h3>Maps as JSON arrays</h3>
	 * For this case, assume that a type adapter was NOT registered for some
	 * {@code Point} class, but rather the default Gson serialization is applied.
	 * In this case, some {@code new Point(2,3)} would serialize as {@code
	 * {"x":2,"y":5}}.
	 *
	 * <p>Given the assumption above, a {@code Map<Point, String>} will be
	 * serialize as an array of arrays (can be viewed as an entry set of pairs).
	 *
	 * <p>Below is an example of serializing complex types as JSON arrays:
	 * <pre> {@code
	 *   Gson gson = new HyperDataGsonBuilder()
	 *       .enableComplexMapKeySerialization()
	 *       .create();
	 *
	 *   Map<Point, String> original = new LinkedHashMap<Point, String>();
	 *   original.put(new Point(5, 6), "a");
	 *   original.put(new Point(8, 8), "b");
	 *   System.out.println(gson.toJson(original, type));
	 * }
	 *
	 * The JSON output would look as follows:
	 * <pre>   {@code
	 *   [
	 *     [
	 *       {
	 *         "x": 5,
	 *         "y": 6
	 *       },
	 *       "a"
	 *     ],
	 *     [
	 *       {
	 *         "x": 8,
	 *         "y": 8
	 *       },
	 *       "b"
	 *     ]
	 *   ]
	 * }</pre>
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.7
	 */
	public HyperDataGsonBuilder enableComplexMapKeySerialization() {
		builder.enableComplexMapKeySerialization();
		return this;
	}

	/**
	 * Configures Gson to exclude inner classes during serialization.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder disableInnerClassSerialization() {
		builder.disableInnerClassSerialization();
		return this;
	}

	/**
	 * Configures Gson to apply a specific serialization policy for {@code Long} and {@code long}
	 * objects.
	 *
	 * @param serializationPolicy the particular policy to use for serializing longs.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
		builder.setLongSerializationPolicy(serializationPolicy);
		return this;
	}

	/**
	 * Configures Gson to apply a specific naming policy to an object's field during serialization
	 * and deserialization.
	 *
	 * @param namingConvention the JSON field naming convention to use for serialization and
	 * deserialization.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 */
	public HyperDataGsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
		builder.setFieldNamingPolicy(namingConvention);
		return this;
	}

	/**
	 * Configures Gson to apply a specific naming policy strategy to an object's field during
	 * serialization and deserialization.
	 *
	 * @param fieldNamingStrategy the actual naming strategy to apply to the fields
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
		builder.setFieldNamingStrategy(fieldNamingStrategy);
		return this;
	}

	/**
	 * Configures Gson to apply a set of exclusion strategies during both serialization and
	 * deserialization. Each of the {@code strategies} will be applied as a disjunction rule.
	 * This means that if one of the {@code strategies} suggests that a field (or class) should be
	 * skipped then that field (or object) is skipped during serializaiton/deserialization.
	 *
	 * @param strategies the set of strategy object to apply during object (de)serialization.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.4
	 */
	public HyperDataGsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
		builder.setExclusionStrategies(strategies);
		return this;
	}

	/**
	 * Configures Gson to apply the passed in exclusion strategy during serialization.
	 * If this method is invoked numerous times with different exclusion strategy objects
	 * then the exclusion strategies that were added will be applied as a disjunction rule.
	 * This means that if one of the added exclusion strategies suggests that a field (or
	 * class) should be skipped then that field (or object) is skipped during its
	 * serialization.
	 *
	 * @param strategy an exclusion strategy to apply during serialization.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.7
	 */
	public HyperDataGsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
		builder.addSerializationExclusionStrategy(strategy);
		return this;
	}

	/**
	 * Configures Gson to apply the passed in exclusion strategy during deserialization.
	 * If this method is invoked numerous times with different exclusion strategy objects
	 * then the exclusion strategies that were added will be applied as a disjunction rule.
	 * This means that if one of the added exclusion strategies suggests that a field (or
	 * class) should be skipped then that field (or object) is skipped during its
	 * deserialization.
	 *
	 * @param strategy an exclusion strategy to apply during deserialization.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.7
	 */
	public HyperDataGsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
		builder.addDeserializationExclusionStrategy(strategy);
		return this;
	}

	/**
	 * Configures Gson to output Json that fits in a page for pretty printing. This option only
	 * affects Json serialization.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 */
	public HyperDataGsonBuilder setPrettyPrinting() {
		builder.setPrettyPrinting();
		return this;
	}

	/**
	 * By default, Gson escapes HTML characters such as &lt; &gt; etc. Use this option to configure
	 * Gson to pass-through HTML characters as is.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder disableHtmlEscaping() {
		builder.disableHtmlEscaping();
		return this;
	}

	/**
	 * Configures Gson to serialize {@code Date} objects according to the pattern provided. You can
	 * call this method or {@link #setDateFormat(int)} multiple times, but only the last invocation
	 * will be used to decide the serialization format.
	 *
	 * <p>The date format will be used to serialize and deserialize {@link java.util.Date}, {@link
	 * java.sql.Timestamp} and {@link java.sql.Date}.
	 *
	 * <p>Note that this pattern must abide by the convention provided by {@code SimpleDateFormat}
	 * class. See the documentation in {@link java.text.SimpleDateFormat} for more information on
	 * valid date and time patterns.</p>
	 *
	 * @param pattern the pattern that dates will be serialized/deserialized to/from
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.2
	 */
	public HyperDataGsonBuilder setDateFormat(String pattern) {
		builder.setDateFormat(pattern);
		return this;
	}

	/**
	 * Configures Gson to to serialize {@code Date} objects according to the style value provided.
	 * You can call this method or {@link #setDateFormat(String)} multiple times, but only the last
	 * invocation will be used to decide the serialization format.
	 *
	 * <p>Note that this style value should be one of the predefined constants in the
	 * {@code DateFormat} class. See the documentation in {@link java.text.DateFormat} for more
	 * information on the valid style constants.</p>
	 *
	 * @param style the predefined date style that date objects will be serialized/deserialized
	 * to/from
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.2
	 */
	public HyperDataGsonBuilder setDateFormat(int style) {
		builder.setDateFormat(style);
		return this;
	}

	/**
	 * Configures Gson to to serialize {@code Date} objects according to the style value provided.
	 * You can call this method or {@link #setDateFormat(String)} multiple times, but only the last
	 * invocation will be used to decide the serialization format.
	 *
	 * <p>Note that this style value should be one of the predefined constants in the
	 * {@code DateFormat} class. See the documentation in {@link java.text.DateFormat} for more
	 * information on the valid style constants.</p>
	 *
	 * @param dateStyle the predefined date style that date objects will be serialized/deserialized
	 * to/from
	 * @param timeStyle the predefined style for the time portion of the date objects
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.2
	 */
	public HyperDataGsonBuilder setDateFormat(int dateStyle, int timeStyle) {
		builder.setDateFormat(dateStyle, timeStyle);
		return this;
	}

	/**
	 * Configures Gson for custom serialization or deserialization. This method combines the
	 * registration of an {@link TypeAdapter}, {@link InstanceCreator}, {@link JsonSerializer}, and a
	 * {@link JsonDeserializer}. It is best used when a single object {@code typeAdapter} implements
	 * all the required interfaces for custom serialization with Gson. If a type adapter was
	 * previously registered for the specified {@code type}, it is overwritten.
	 *
	 * <p>This registers the type specified and no other types: you must manually register related
	 * types! For example, applications registering {@code boolean.class} should also register {@code
	 * Boolean.class}.
	 *
	 * @param type the type definition for the type adapter being registered
	 * @param typeAdapter This object must implement at least one of the {@link TypeAdapter},
	 * {@link InstanceCreator}, {@link JsonSerializer}, and a {@link JsonDeserializer} interfaces.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public HyperDataGsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
		builder.registerTypeAdapter(type, typeAdapter);
		return this;
	}

	/**
	 * Register a factory for type adapters. Registering a factory is useful when the type
	 * adapter needs to be configured based on the type of the field being processed. Gson
	 * is designed to handle a large number of factories, so you should consider registering
	 * them to be at par with registering an individual type adapter.
	 *
	 * @since 2.1
	 */
	public HyperDataGsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
		builder.registerTypeAdapterFactory(factory);
		return this;
	}

	/**
	 * Configures Gson for custom serialization or deserialization for an inheritance type hierarchy.
	 * This method combines the registration of a {@link TypeAdapter}, {@link JsonSerializer} and
	 * a {@link JsonDeserializer}. If a type adapter was previously registered for the specified
	 * type hierarchy, it is overridden. If a type adapter is registered for a specific type in
	 * the type hierarchy, it will be invoked instead of the one registered for the type hierarchy.
	 *
	 * @param baseType the class definition for the type adapter being registered for the base class
	 *        or interface
	 * @param typeAdapter This object must implement at least one of {@link TypeAdapter},
	 *        {@link JsonSerializer} or {@link JsonDeserializer} interfaces.
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.7
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public HyperDataGsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
		builder.registerTypeHierarchyAdapter(baseType, typeAdapter);
		return this;
	}

	/**
	 * Section 2.4 of <a href="http://www.ietf.org/rfc/rfc4627.txt">JSON specification</a> disallows
	 * special double values (NaN, Infinity, -Infinity). However,
	 * <a href="http://www.ecma-international.org/publications/files/ECMA-ST/Ecma-262.pdf">Javascript
	 * specification</a> (see section 4.3.20, 4.3.22, 4.3.23) allows these values as valid Javascript
	 * values. Moreover, most JavaScript engines will accept these special values in JSON without
	 * problem. So, at a practical level, it makes sense to accept these values as valid JSON even
	 * though JSON specification disallows them.
	 *
	 * <p>Gson always accepts these special values during deserialization. However, it outputs
	 * strictly compliant JSON. Hence, if it encounters a float value {@link Float#NaN},
	 * {@link Float#POSITIVE_INFINITY}, {@link Float#NEGATIVE_INFINITY}, or a double value
	 * {@link Double#NaN}, {@link Double#POSITIVE_INFINITY}, {@link Double#NEGATIVE_INFINITY}, it
	 * will throw an {@link IllegalArgumentException}. This method provides a way to override the
	 * default behavior when you know that the JSON receiver will be able to handle these special
	 * values.
	 *
	 * @return a reference to this {@code HyperDataGsonBuilder} object to fulfill the "Builder" pattern
	 * @since 1.3
	 */
	public HyperDataGsonBuilder serializeSpecialFloatingPointValues() {
		builder.serializeSpecialFloatingPointValues();
		return this;
	}
}
