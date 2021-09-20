package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * Created by Nikita Biryukov on 15.09.2021.
 */
@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int uniqueId, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backDropPath, double voteAverage, String releaseDate) {
        super(uniqueId,id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backDropPath, voteAverage, releaseDate);
    }

    @Ignore
    public FavoriteMovie(Movie movie){
        super(movie.getUniqueId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBigPosterPath(),movie.getVoteAverage(), movie.getReleaseDate());
    }
}
