package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.MovieListItems;
import com.example.myapplication.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.myapplication.activities.MainActivity.movieImagePathBuilder;

public class MovieActivity extends AppCompatActivity {
    public TextView mMovieTitle;
    public ImageView mMoviePoster;
    public ImageView mPosterImage;
    public TextView mMovieOverview;
    public  TextView mMovieReleaseDate;
    public TextView mMovieRating;
    private MovieListItems mMovie;

    CollapsingToolbarLayout detailsCollapsingToolbar;
    CoordinatorLayout detailsCoordinatorLayout;
    AppBarLayout detailsAppBar;
    Toolbar myProfileToolbar;
    NestedScrollView schoolDetailsNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mMovieTitle = (TextView) findViewById(R.id.movie_activity_title);

        mPosterImage = (ImageView) findViewById(R.id.movie_image);
        mMovieOverview = (TextView) findViewById(R.id.movie_activity_plot);
        mMovieReleaseDate = (TextView) findViewById(R.id.movie_activity_release_date);
        mMovieRating = (TextView) findViewById(R.id.movie_activity_rating);

        detailsCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.userDetailsCollapsingToolbar);
        detailsCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.detailsCoordinatorLayout);
        detailsAppBar = (AppBarLayout) findViewById(R.id.detailsAppBar);
        myProfileToolbar = (Toolbar) findViewById(R.id.myProfileToolbar);
        schoolDetailsNestedScrollView = (NestedScrollView) findViewById(R.id.detailsNestedScrollView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) detailsAppBar.getLayoutParams();
        layoutParams.height = getResources().getDisplayMetrics().widthPixels;

        detailsAppBar.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = getResources().getDisplayMetrics().heightPixels / 4;
                setAppBarOffset(heightPx);
            }
        });

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            mMovie = (MovieListItems) bundle.getParcelable("movie");
            populateActivity(mMovie);
        }


    }

    private void populateActivity(MovieListItems mMovie){

        RequestOptions options1 = new RequestOptions().error(R.drawable.movie_placeholder);
        Glide.with(mPosterImage.getContext())
                .load(movieImagePathBuilder(mMovie.getPoster()))
                .placeholder(R.drawable.movie_placeholder)
                .fitCenter()
                .apply(options1)
                .transition(withCrossFade())
                .into(mPosterImage);
        mMovieTitle.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieReleaseDate.setText(mMovie.getReleaseDate());
        String userRatingText = String.valueOf(mMovie.getVoteAvg()) + "/10";
        mMovieRating.setText(userRatingText);
    }

    private void setAppBarOffset(int i) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) detailsAppBar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(detailsCoordinatorLayout, detailsAppBar, null, 0, i, new int[]{0, 0});
    }
}