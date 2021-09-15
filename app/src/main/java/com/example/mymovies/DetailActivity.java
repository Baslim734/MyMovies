package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.FavoriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;
    private Movie movie;
    private ImageView favoriteButton;
    private FavoriteMovie favoriteMovie;

    private int id;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewDescription);
        favoriteButton = findViewById(R.id.imageViewAddToFavorite);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        }
        else{
            finish();
        }
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
 if(viewModel.getFavoriteMovieById(id)!=null){
     favoriteButton.setImageResource(R.drawable.favorite_on);
     }
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewDescription.setText(movie.getOverview());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
    }

    public void onClickChangeFavorite(View view) {
            favoriteMovie = viewModel.getFavoriteMovieById(id);
            if (favoriteMovie==null){
                viewModel.insertFavoriteMovie(new FavoriteMovie(movie));
                Toast.makeText(this, R.string.added_to_favorite,Toast.LENGTH_SHORT).show();
              favoriteButton.setImageResource(R.drawable.favorite_on);
            } else{
                viewModel.deleteFavoriteMovie(favoriteMovie);
                Toast.makeText(this, R.string.deleted_from_favorite,Toast.LENGTH_SHORT).show();
             favoriteButton.setImageResource(R.drawable.favorite_off);
            }
    }
}