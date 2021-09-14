package com.example.mymovies.utils;

import com.example.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nikita Biryukov on 13.09.2021.
 */
public class JSONUtils {
    private int id;
    private int voteCount;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String backDropPath;
    private double voteAverage;
    private String releaseDate;

    private static String KEY_RESULTS = "results";
    private static String KEY_VOTE_COUNT = "vote_count";
    private static String KEY_ID = "id";
    private static String KEY_TITLE = "title";
    private static String KEY_ORIGINAL_TITLE = "original_title";
    private static String KEY_OVERVIEW = "overview";
    private static String KEY_POSTER_PATH = "poster_path";
    private static String KEY_BACKDROP_PATH = "backdrop_path";
    private static String KEY_VOTE_AVERAGE = "vote_average";
    private static String KEY_RELEASE_DATE = "release_date";


    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject){
        ArrayList<Movie> result = new ArrayList<>();
        if(jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                Double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id,voteCount, title, originalTitle, overview, posterPath,backdropPath,voteAverage,releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }
}
