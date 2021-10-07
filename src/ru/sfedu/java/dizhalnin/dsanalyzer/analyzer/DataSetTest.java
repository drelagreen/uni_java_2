package ru.sfedu.java.dizhalnin.dsanalyzer.analyzer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * @author Zhalnin Dmitrii Iforevich KTbo3-8
 * @since  04.10.2021
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSetTest{
}
