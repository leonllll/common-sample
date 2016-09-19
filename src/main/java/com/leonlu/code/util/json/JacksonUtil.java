package com.leonlu.code.util.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;

/**
 * Maven dependency:
 * 		<dependency>
 *			<groupId>com.fasterxml.jackson.core</groupId>
 *			<artifactId>jackson-databind</artifactId>
 *			<version>2.8.2</version>
 *		</dependency>
 *
 */
public class JacksonUtil {
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	public static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	
	static {
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
		OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);

	}

	public static <T> T toBean(String json, Class<T> beanClass) {
		try {
			return OBJECT_MAPPER.readValue(json, beanClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object bean) {
		try {
			return OBJECT_MAPPER.writeValueAsString(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
