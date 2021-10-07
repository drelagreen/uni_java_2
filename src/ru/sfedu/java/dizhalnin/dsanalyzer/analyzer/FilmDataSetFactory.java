package ru.sfedu.java.dizhalnin.dsanalyzer.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhalnin Dmitrii Iforevich KTbo3-8
 * @since  04.10.2021
 */
public final class FilmDataSetFactory {

    static FilmDataSet getDataSet(File titlesCSV, File[] data) {
        return new MultiFileDataSet(titlesCSV, data);
    }

    private static class MultiFileDataSet implements FilmDataSet {
        private final File titlesFile;
        private final File[] dataFiles;
        private int fileIndex = 0;
        private boolean end = false;

        private BufferedReader dataReader = null;
        private HashMap<Integer, Map.Entry<String, String>> titles = null;

        private String currentLine = "";

        private int filmId;
        private int userId;
        private int score;
        private String date;

        public MultiFileDataSet(File titlesFile, File[] dataFiles) {
            this.titlesFile = titlesFile;
            this.dataFiles = dataFiles;
        }

        private void loadReader() throws IOException {
            dataReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFiles[fileIndex])));
        }

        private void nextLine() throws IOException {
            if ((currentLine = dataReader.readLine()) == null) {
                dataReader.close();
                fileIndex++;
                if (fileIndex >= dataFiles.length) {
                    end = true;
                } else {
                    loadReader();
                }
            }else {
                parseLine();
            }
        }

        private void parseLine() throws IOException {
            var data = currentLine.split(",");
            if (data.length == 1) {
                filmId = Integer.parseInt(data[0].replace(":", "").strip());
                nextLine();
            } else {
                userId = Integer.parseInt(data[0].strip());
                score = Integer.parseInt(data[1].strip());
                date = data[2].strip();
            }
        }

        private void loadTitles() throws IOException {
            titles = new HashMap<>();
            var input = new FileInputStream(titlesFile);
            var reader = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = reader.readLine()) != null) {
                var strings = line.split(",");
                titles.put(Integer.parseInt(strings[0]), Map.entry(strings[1], strings[2]));
            }
        }

        @Override
        public int getFilmId() {
            return filmId;
        }

        @Override
        public int getUserId() {
            return userId;
        }

        @Override
        public int getFilmScore() {
            return score;
        }

        @Override
        public String getFilmName() {
            return titles.get(userId).getValue();
        }

        @Override
        public boolean next() throws IOException {
            if (titles == null || dataReader == null){
                loadTitles();
                loadReader();
            }
            if (!end){
                nextLine();
            }

            return !end;
        }

        @Override
        public String getScoreDate() {
            return date;
        }

        @Override
        public String getFilmYear() {
            return titles.get(userId).getKey();
        }

        @Override
        public String getFilmNameById(int id) {
            return titles.get(id).getValue();
        }

        @Override
        public String getFilmYearById(int id) {
            return titles.get(id).getKey();
        }
    }
}
