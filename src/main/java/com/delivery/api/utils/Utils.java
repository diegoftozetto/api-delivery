package com.delivery.api.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static void merge(Object original, Map<String, Object> changes) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

		Object origem = objectMapper.convertValue(changes, original.getClass());

		changes.forEach((propriedade, value) -> {
			Field field = ReflectionUtils.findField(original.getClass(), propriedade);
			field.setAccessible(true);

			Object newValue = ReflectionUtils.getField(field, origem);
			ReflectionUtils.setField(field, original, newValue);
		});
	}
}
