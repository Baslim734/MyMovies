package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Switch switchSort;
    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private TextView textViewPopularity;
    private TextView textViewMostRated;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewMostRated = findViewById(R.id.textViewMostRated);
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter();
        switchSort.setChecked(true);
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
               Movie movie = movieAdapter.getMovies().get(position);
               Intent intent = new Intent(MainActivity.this, DetailActivity.class);
               intent.putExtra("id", movie.getId());
               startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {

            }
        });
        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onClickSetMostRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean isMostRated){
        int methodOfSort;
        if (isMostRated) {
            methodOfSort = NetworkUtils.MOST_RATED;
            textViewMostRated.setTextColor(ContextCompat.getColor(this, R.color.green));
            textViewPopularity.setTextColor(ContextCompat.getColor(this, R.color.white));
            textViewMostRated.setTypeface(null, Typeface.BOLD);
            textViewPopularity.setTypeface(null, Typeface.NORMAL);
        }
        else{
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(ContextCompat.getColor(this, R.color.green));
            textViewMostRated.setTextColor(ContextCompat.getColor(this, R.color.white));
            textViewPopularity.setTypeface(null, Typeface.BOLD);
            textViewMostRated.setTypeface(null, Typeface.NORMAL);
        }
        downloadData(methodOfSort,1);
    }

    private void downloadData(int methodOfSort, int page){
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        List<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        if (movies!=null && !movies.isEmpty()){
            viewModel.deleteAllMovies();
            for(Movie movie: movies){
                viewModel.insertMovie(movie);
            }
        }
    }
}