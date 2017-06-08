package com.five.Util;

import com.five.cinema.model.Cinema;
import com.five.cinemaPic.model.CinemaPic;
import com.five.cinemaRemark.model.CinemaRemark;
import com.five.film.Util.FilmUtil;
import com.five.film.model.Film;
import com.five.filmPic.model.FilmPic;
import com.five.filmRemark.model.FilmRemark;
import com.five.filmSession.model.FilmSession;
import com.five.hallSitting.model.HallSitting;
import com.five.hallSitting.model.OneSit;
import com.five.hallSitting.model.Sits;
import com.five.hallSitting.utils.SitUtil;
import com.five.order.model.Reservation;
import com.five.payment.model.Wallet;
import com.five.user.model.MyMessage;
import com.five.user.model.User;
import net.sf.json.JSONArray;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by msi on 2017/6/8.
 */
public class DataCreator {

    private static Random random = new Random();

    public static List<Cinema> prepareCinema(int n) {
        List<Cinema> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String name = "cinema" + Integer.toString(i);
            String address = "address" + Integer.toString(i);
            String phone = Integer.toString(i);
            double lo = random.nextDouble();
            double la = random.nextDouble();
            int cityCode = random.nextInt(n);
            Cinema cinema = new Cinema(name, address,phone, lo, la, cityCode);
            ans.add(cinema);
        }
        return ans;
    }

    public static List<Cinema> prepareCinemaWithSameCity(int n, int city_code) {
        List<Cinema> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String name = "cinema" + Integer.toString(i);
            String address = "address" + Integer.toString(i);
            String phone = Integer.toString(i);
            double lo = random.nextDouble();
            double la = random.nextDouble();
            int cityCode = city_code;
            Cinema cinema = new Cinema(name, address,phone, lo, la, cityCode);
            ans.add(cinema);
        }
        return ans;
    }

    public static List<CinemaPic> prepareCinemaPic(int n, int cinemaIdMax) {
        List<CinemaPic> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String path = "path" + Integer.toString(i);
            int type = random.nextDouble()>0.5?1:0;
            int cinemaId = random.nextInt(cinemaIdMax);
            CinemaPic cinemaPic = new CinemaPic(path, type, cinemaId);
            ans.add(cinemaPic);
        }
        return ans;
    }

    public static List<CinemaRemark> prepareCinemaRemark(int n) {
        List<CinemaRemark> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int userId = random.nextInt(n);
            int cinemaId = random.nextInt(n);
            String content = "content" + Integer.toString(i);
            CinemaRemark cinemaRemark = new CinemaRemark(userId, cinemaId, content);
            ans.add(cinemaRemark);
        }
        return ans;
    }

    public static List<Film> prepareFilm(int n) {
        List<Film> ans = new ArrayList<>();
        Date d = new Date();
        for (int i = 0; i < n; i++) {
            String name = "film" + Integer.toString(i);
            String actor = "actor" + Integer.toString(i);
            String category = "category" + Integer.toString(i);
            String director = "director" + Integer.toString(i);
            String language = "language" + Integer.toString(i);
            long a = random.nextLong() > 0?random.nextLong()%31536000000L:(-random.nextLong())%31536000000L;
            Timestamp publishTime = new Timestamp(d.getTime() + a);
            double lastTime = random.nextDouble()*1.5*3600;
            String nation = "nation" + Integer.toString(i);
            String summary = "summary" + Integer.toString(i);
            double score = random.nextDouble() * 5;
            Film film = new Film( name, summary, score, category, nation, publishTime, lastTime, actor, director, language);
            ans.add(film);
        }
        return ans;
    }

    public static List<FilmPic> prepareFilmPic(int n) {
        List<FilmPic> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String path = "path" + Integer.toString(i);
            int type = random.nextDouble()>0.5?1:0;
            int filmId = random.nextInt(n);
            FilmPic filmPic = new FilmPic(path, type, filmId);
            ans.add(filmPic);
        }
        return ans;
    }

    public static List<FilmRemark> prepareFilmRemark(int n) {
        List<FilmRemark> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int userId = random.nextInt(n);
            int filmId = random.nextInt(n);
            String content = "content" + Integer.toString(i);
            FilmRemark filmRemark = new FilmRemark(userId, filmId, content);
            ans.add(filmRemark);
        }
        return ans;
    }

    public static List<FilmSession> prepareFilmSession(int n) {
        List<FilmSession> ans = new ArrayList<>();
        Date d = new Date();
        for (int i = 0; i < n; i++) {
            String hall = "hall" + Integer.toString(i);
            String classification = "classification" + Integer.toString(random.nextInt(n));
            long a = random.nextLong() > 0?random.nextLong()%86400000L:(-random.nextLong())%86400000L;
            Timestamp beginTime = new Timestamp(d.getTime() + a);
            Timestamp endTime = new Timestamp(beginTime.getTime() + 1296000000L);
            int cinemaId = random.nextInt(n);
            int hallSittingId = random.nextInt(n);
            int filmId = random.nextInt(n);
            double price = random.nextDouble() * 15;
            FilmSession filmSession = new FilmSession(hall, beginTime, endTime, classification, price, cinemaId, hallSittingId, filmId);
            ans.add(filmSession);
        }
        return ans;
    }

    public static List<HallSitting> prepareHallSitting(int n, int x, int y) {
        List<HallSitting> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<OneSit> oneSits = new ArrayList<>();
            for (int j = 0; j < x*y; j++) {
                int ax = j/y, ay = j%x;
                oneSits.add(new OneSit(ax, ay, true));
            }
            JSONArray array = JSONArray.fromObject(oneSits.toArray());
            HallSitting hallSitting = new HallSitting();
            hallSitting.setSit(array.toString());
            ans.add(hallSitting);
        }
        return ans;
    }

    public static List<Reservation> prepareOrder(int n) {
        List<Reservation> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            OneSit oneSit = new OneSit(i, i, true);
            String sitting = '{' + "\"sit\":[{\"x\":"+Integer.toString(i)+",\"y\":"+Integer.toString(i)+"}]" +'}';
            double price = random.nextDouble()*15;
            int filmSessionId = random.nextInt(n);
            int userId = random.nextInt(n);
            ans.add(new Reservation(sitting, price, filmSessionId, userId));
        }
        return ans;
    }

    public static List<User> prepareUser(int n) {
        List<User> ans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String username = "username" + Integer.toString(i);
            String password = "password" + Integer.toString(i);
            User user = new User(username, password);
            ans.add(user);
        }
        return ans;
    }
}