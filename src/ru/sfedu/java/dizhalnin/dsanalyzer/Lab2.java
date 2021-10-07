package ru.sfedu.java.dizhalnin.dsanalyzer;

import ru.sfedu.java.dizhalnin.dsanalyzer.analyzer.FilmDataSetTestAnalyzer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) throws FilmDataSetTestAnalyzer.MethodNotSupportedException, InvocationTargetException, IllegalAccessException, InstantiationException {
        var scanner = new Scanner(System.in);

        System.out.print("Enter dir path: ");
        var path = scanner.nextLine();

        var dir = new File(path);

        System.out.print("Enter unique part of the data set files' name (if data_1 - data_): ");
        var dataName = scanner.nextLine();

        var data = Arrays.stream(dir.listFiles())
                .filter(file -> file.getName().startsWith(dataName))
                .sorted(Comparator.comparing(File::getName))
                .toArray(File[]::new);

        System.out.print("Enter filename of the tittles file: ");
        var titlesName = scanner.nextLine();

        var titles = new File(dir, titlesName);

        System.out.println(new FilmDataSetTestAnalyzer<Tests>(titles, data).analyze(Tests.class));
    }

}
