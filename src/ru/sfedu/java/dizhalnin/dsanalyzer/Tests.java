package ru.sfedu.java.dizhalnin.dsanalyzer;

import ru.sfedu.java.dizhalnin.dsanalyzer.analyzer.DataSetTest;
import ru.sfedu.java.dizhalnin.dsanalyzer.analyzer.FilmDataSet;
import ru.sfedu.java.dizhalnin.dsanalyzer.analyzer.domain.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Zhalnin Dmitrii Iforevich KTbo3-8
 * @since 04.10.2021
 */
public class Tests {
    int timer = 0;
    int timer2 = 0;

    @DataSetTest
    public String test1(FilmDataSet filmDataSet) throws IOException {
        int currentFilmId = 0;
        int currentScores = 0;
        int maxScore = 0;
        int maxFilm = 0;
        while (filmDataSet.next()) {
            if (currentFilmId != filmDataSet.getFilmId()) {
                if (currentScores > maxScore) {
                    maxScore = currentScores;
                    maxFilm = currentFilmId;
                }
                currentScores = 0;
                currentFilmId = filmDataSet.getFilmId();
            }
            currentScores++;
            tick("test1 film ID " + filmDataSet.getFilmId());
        }
        if (currentScores > maxScore) maxScore = currentScores;
        StringBuilder builder = new StringBuilder();
        builder.append("Most scored film is ")
                .append(filmDataSet.getFilmNameById(maxFilm))
                .append(" with ")
                .append(maxScore)
                .append(" scores\n");

        return builder.toString();
    }

    @DataSetTest
    public String test2(FilmDataSet filmDataSet) throws IOException {
        HashMap<Integer, Pair<Integer, Double>> map = new HashMap<>();
        while (filmDataSet.next()) {
            var filmId = filmDataSet.getFilmId();
            if (map.containsKey(filmId)) {
                map.get(filmId).setValue1(map.get(filmId).getValue1() + 1);
                map.get(filmId).setValue2(map.get(filmId).getValue2() + filmDataSet.getFilmScore());
            } else {
                map.put(filmId, new Pair<>(1, (double) filmDataSet.getFilmScore()));
            }
            tick("test2 film ID: " + filmDataSet.getFilmId());
        }

        Comparator<Map.Entry<Integer, Pair<Integer, Double>>> comp = (o1, o2) -> {
            Pair<Integer, Double> pair1 = o1.getValue();
            Pair<Integer, Double> pair2 = o2.getValue();
            if (pair1.getValue2() > pair2.getValue2()) {
                return -1;
            } else if (pair1.getValue2() < pair2.getValue2()) {
                return 1;
            } else {
                return pair1.getValue1().compareTo(pair2.getValue1());
            }
        };

        var sortedList = map.entrySet().stream()
                .peek(entry -> entry.getValue().setValue2(entry.getValue().getValue2() / entry.getValue().getValue1()))
                .sorted(comp)
                .collect(Collectors.toList());

        var builder = new StringBuilder();
        builder.append("The most liked film is ")
                .append(filmDataSet.getFilmNameById(sortedList.get(0).getKey()))
                .append(" with [score/score amount] [")
                .append(sortedList.get(0).getValue().getValue2())
                .append("/")
                .append(sortedList.get(0).getValue().getValue1())
                .append("]\n");

        double score = sortedList.get(sortedList.size() - 1).getValue().getValue2();
        int minElement = sortedList.size() - 1;
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            if (sortedList.get(i).getValue().getValue2() != score) {
                minElement = i + 1;
                break;
            }
        }
        builder.append("The lowest liked film is ")
                .append(filmDataSet.getFilmNameById(minElement))
                .append(" with [score/score amount] [")
                .append(sortedList.get(minElement).getValue().getValue2())
                .append("/")
                .append(sortedList.get(minElement).getValue().getValue1())
                .append("]\n");
        return builder.toString();
    }

    @DataSetTest
    public String test3(FilmDataSet filmDataSet) throws IOException {
        HashMap<Integer, Integer> map = new HashMap<>();

        while (filmDataSet.next()) {
            if (map.containsKey(filmDataSet.getUserId())) {
                map.put(filmDataSet.getUserId(), map.get(filmDataSet.getUserId()) + 1);
            } else {
                map.put(filmDataSet.getUserId(), 1);
            }
            tick("test3 film ID: " + filmDataSet.getFilmId());
        }
        var sortedList = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
        var sb = new StringBuilder();

        sb.append("User with the lowest amount of scores is ")
                .append(sortedList.get(sortedList.size() - 1).getKey())
                .append(" with ")
                .append(sortedList.get(sortedList.size() - 1).getValue())
                .append(" scores\n");

        sb.append("User with the largest amount of scores is ")
                .append(sortedList.get(0).getKey())
                .append(" with ")
                .append(sortedList.get(0).getValue())
                .append(" scores\n");

        return sb.toString();
    }

    @DataSetTest
    public String test4(FilmDataSet filmDataSet) throws IOException {
        ArrayList<AtomicInteger> scores = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            scores.add(new AtomicInteger(0));
        }

        while (filmDataSet.next()) {
            scores.get(filmDataSet.getFilmScore()).incrementAndGet();
            tick("test4 film ID: " + filmDataSet.getFilmId());
        }

        var sorted = new ArrayList<>(scores);
        sorted.sort(Comparator.comparing(AtomicInteger::get));
        int score = scores.indexOf(sorted.get(sorted.size()-1));

        var sb = new StringBuilder();
        sb.append("The most popular score is ")
                .append(score)
                .append(" (")
                .append(sorted.get(sorted.size()-1))
                .append(" times)");
        return sb.toString();
    }

    @DataSetTest
    String test5(FilmDataSet filmDataSet) throws IOException {
//        HashMap<Integer, Pair<Integer, Double>> map = new HashMap<>();
//        while (filmDataSet.next()) {
//            var filmId = filmDataSet.getFilmId();
//            if (map.containsKey(filmId)) {
//                map.get(filmId).setValue1(map.get(filmId).getValue1() + 1);
//                map.get(filmId).setValue2(map.get(filmId).getValue2() + filmDataSet.getFilmScore());
//            } else {
//                map.put(filmId, new Pair<>(1, (double) filmDataSet.getFilmScore()));
//            }
//            tick("test5 film ID: " + filmDataSet.getFilmId());
//        }
//
//        Comparator<Map.Entry<Integer, Pair<Integer, Double>>> comp = (o1, o2) -> {
//            Pair<Integer, Double> pair1 = o1.getValue();
//            Pair<Integer, Double> pair2 = o2.getValue();
//            if (pair1.getValue2() > pair2.getValue2()) {
//                return -1;
//            } else if (pair1.getValue2() < pair2.getValue2()) {
//                return 1;
//            } else {
//                return pair1.getValue1().compareTo(pair2.getValue1());
//            }
//        };
//
//        var sortedList = map.entrySet().stream()
//                .peek(entry -> entry.getValue().setValue2(entry.getValue().getValue2() / entry.getValue().getValue1()))
//                .sorted(comp)
//                .collect(Collectors.toList());
// TODO: 10/7/2021 in process
        return "Test 5 in process...";
    }

    void tick(String s) {
        if (timer % 1000000 == 0) {
            System.err.println("Still running... (" + timer2 + ")");
            System.err.println(s);
            timer2++;
            timer = 0;
        }
        timer++;
    }
}
