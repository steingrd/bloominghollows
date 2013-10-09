package com.github.steingrd.bloominghollows.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

public class Wiring<T> {

	private final T target;
	private final List<Field> assignedFields;

	public Wiring(T target, List<Field> assignedFields) {
		this.target = target;
		this.assignedFields = assignedFields;
	}

	public static <T> WiringInProgress<T> wire(T obj) {
		return new WiringInProgress<T>(obj);
	}
	
	public static class WiringInProgress<U> {
		private final U obj;

		public WiringInProgress(U obj) {
			this.obj = obj;
		}

		public Wiring<U> withFieldsFrom(ApplicationContext context) {
			List<Field> assignedFields = new ArrayList<>();
			
			for (Field field : this.obj.getClass().getDeclaredFields()) {
				if (!isAnnotatedWith(field, Resource.class, Autowired.class)) {
					continue;
				}
				
				try {
					Object bean = context.getBean(field.getType());
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, obj, bean);
					assignedFields.add(field);
				} catch (NoSuchBeanDefinitionException e) {
					continue;
				}
			}
			
			return new Wiring<U>(obj, assignedFields);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private boolean isAnnotatedWith(Field field, Class... classes) {
			for (Class clazz : classes) {
				Annotation annotation = field.getAnnotation(clazz);
				if (annotation != null) {
					return true;
				}
			}
			
			return false;
		}
	}
	
}
