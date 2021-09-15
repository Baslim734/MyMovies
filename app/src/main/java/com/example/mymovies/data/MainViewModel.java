package com.example.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nikita Biryukov on 15.09.2021.
 */
public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
        favoriteMovies = database.movieDao().getAllFavoriteMovies();
    }

    public Movie getMovieById(int id){
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavoriteMovie getFavoriteMovieById(int id){
        try {
            return new GetFavoriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies(){
        return favoriteMovies;
    }

    public void deleteAllMovies(){
        new DeleteMovieTask().execute();
    }

    public void insertMovie(Movie movie){
        new InsertMovieTask().execute(movie);
    }

    public void deleteMovie(Movie movie){
        new DeleteMovieTask().execute();
    }

    public void insertFavoriteMovie(FavoriteMovie movie){
        new InsertFavoriteTask().execute(movie);
    }

    public void deleteFavoriteMovie(FavoriteMovie movie){
        new DeleteFavoriteTask().execute(movie);
    }

    private static class DeleteTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void>{

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().deleteFavoriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void>{

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().insertFavoriteMovie(movies[0]);
            }
            return null;
        }
    }


    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void setMovies(LiveData<List<Movie>> movies) {
        this.movies = movies;
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0){
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... integers) {
                database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie>{

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetFavoriteMovieTask extends AsyncTask<Integer, Void, FavoriteMovie>{

        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return database.movieDao().getFavoriteMovieById(integers[0]);
            }
            return null;
        }
    }
}
