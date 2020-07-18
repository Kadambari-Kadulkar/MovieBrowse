package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.Model.MovieListItems;
import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.R;
import com.example.myapplication.connectivity.MovieList;
import com.example.myapplication.utils.SingleMovieClickListener;
import com.example.myapplication.utils.SmoothRecyclerViewScrollListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int currentSortMode = 1; // 1 = popularity , 2 = top_rated
    private List<MovieListItems> movieResults = new ArrayList<MovieListItems>();;
    private MovieAdapter movieAdapter;
    public RecyclerView recyclerView;
    public RelativeLayout mNoInternetMessage;
    public Button btnRefresh;
    MovieList movieList = new MovieList(this);
    ProgressDialog progressDialog;
    MaterialSpinner spinner;
    private String[] Sort_by = {
            "Popularity",
            "Top Rated"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movieRecyclerView);
        mNoInternetMessage = findViewById(R.id.tv_no_internet_error);
        spinner = (MaterialSpinner) findViewById(R.id.spSort);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getString(R.string.progress_msg));


        if(!isNetworkAvailable()){
            recyclerView.setVisibility(View.GONE);
            mNoInternetMessage.setVisibility(View.VISIBLE);
        }
        movieAdapter = new MovieAdapter(movieResults, new SingleMovieClickListener() {
                @Override
                public void onSingleMovieClick(MovieListItems movie) {
                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movie", movie);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(movieAdapter);


        spinner.setItems(Sort_by);
        spinner.setSelectedIndex(0);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                currentSortMode = position + 1;
                progressDialog.show();
                movieList.getMovieListing(movieResults,recyclerView,movieAdapter,currentSortMode, progressDialog);
            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);

            progressDialog.show();
            movieList.getMovieListing(movieResults,recyclerView,movieAdapter,currentSortMode, progressDialog);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}