package ru.sfedu.java.dizhalnin.dsanalyzer.analyzer;

import java.io.IOException;

/**
 * @author Zhalnin Dmitrii Iforevich KTbo3-8
 * @since  04.10.2021
 */
public interface FilmDataSet {
    /**
     * @return ID of the current user from the dataset
     */
    int getFilmId();

    /**
     * @return ID of the current film from the dataset
     */
    int getUserId();

    /**
     * @return User's score of the current film
     */
    int getFilmScore();

    /**
     * @return Official name of the film with the current id from the dataset
     */
    String getFilmName();

    /**
     * Changes current processing film to the next one from the dataset
     * @return True if there is a next film in the dataset or False if there isn't
     */
    boolean next() throws IOException;

    String getScoreDate();

    String getFilmYear();

    String getFilmNameById(int id);

    String getFilmYearById(int id);
}
