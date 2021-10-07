package ru.sfedu.java.dizhalnin.dsanalyzer.analyzer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Zhalnin Dmitrii Iforevich KTbo3-8
 * @since  04.10.2021
 */

public final class FilmDataSetTestAnalyzer<T> {
    private final File titlesCSV;
    private final File[] data;

    public FilmDataSetTestAnalyzer(File titlesCSV, File... data){
        this.titlesCSV = titlesCSV;
        this.data = data;
    }

    /**
     *  Invoke every annotated @DataSetTest methods with a loaded 'DataSet' parameter
     * @param clazz Class that contains annotated @DataSetTest methods to invoke
     * @return String that contains all invoked methods' results
     */
    public String analyze(Class<T> clazz) throws MethodNotSupportedException, InvocationTargetException, IllegalAccessException, InstantiationException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Result:\n");

        var methods = clazz.getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(DataSetTest.class)) {
                continue;
            }

            if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(FilmDataSet.class) || !method.getReturnType().equals(String.class)) {
                throw new MethodNotSupportedException("Annotated method is invalid. Should be single 'ru.sfedu.java.dizhalnin.dsanalyzer.DataSet' parameter and 'java.lang.String' return type");
            }

            var result = method.invoke(clazz.newInstance(), getDataSet());
            stringBuilder.append((String) result);
        }
        
        return stringBuilder.toString();
    }

    private FilmDataSet getDataSet() {
        return FilmDataSetFactory.getDataSet(titlesCSV, data);
    }

    public static class MethodNotSupportedException extends Throwable {
        MethodNotSupportedException(String message) {
            super(message);
        }
    }
}
