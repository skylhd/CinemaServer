package com.five.filmSession.service;

import com.five.filmSession.model.FilmSession;
import com.five.user.model.MyMessage;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by msi on 2017/6/6.
 */
public interface FilmSessionService {
    Object getFilmSession(int filmId, int cinemaId, String time);
    FilmSession findById(int id);
    List<Integer> findFilmIdByTime(Timestamp bt, Timestamp et);
    List<Integer> findFilmIdByCinemaAndTime(int cinemaId, Timestamp bt, Timestamp et);
    List<Integer> findFilmIdByCinemasAntTime(List<Integer> cinemaId, Timestamp bt, Timestamp et);
}
