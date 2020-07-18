package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.MovieListItems;
import com.example.myapplication.R;
import com.example.myapplication.utils.SingleMovieClickListener;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.myapplication.activities.MainActivity.getMeasuredPosterHeight;
import static com.example.myapplication.activities.MainActivity.getScreenWidth;
import static com.example.myapplication.activities.MainActivity.movieImagePathBuilder;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final SingleMovieClickListener movieClickListener;
    private final List<MovieListItems> movieList;

    public MovieAdapter(List<MovieListItems> movieList, SingleMovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieListItems movie = this.movieList.get(position);
        holder.bind(movie, movieClickListener);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMoviePoster;
        private CardView mMovieCard;
        private LinearLayout mMovieRoot;

        public MovieViewHolder(final View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            mMovieCard = (CardView)itemView.findViewById(R.id.movie_card);

        }

        public void bind(final MovieListItems movie, final SingleMovieClickListener movieClickListener) {
            mMovieCard.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));
            //mMovieRoot.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));
            RequestOptions options = new RequestOptions().error(R.drawable.movie_placeholder);
            Glide.with(mMoviePoster.getContext())
                    .load(movieImagePathBuilder(movie.getPoster()))
                    .placeholder(R.drawable.movie_placeholder)
                    .fitCenter()
                    .apply(options)
                    .transition(withCrossFade())
                    .into(mMoviePoster);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieClickListener.onSingleMovieClick(movie);
                }
            });
        }
    }


}
