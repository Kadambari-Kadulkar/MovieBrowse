package com.example.myapplication.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myapplication.Model.MovieListItems;
import com.example.myapplication.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MovieList {

    private static List<MovieListItems> MovieItemsArray;
    private static Context context;
    private static ProgressDialog progressDialog;
    private static String webUrlName;
    private static ProgressDialog pDialog;
    private static String totPage;

    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;

    public MovieList(MainActivity mainActivity) {
        context = mainActivity;
    }

    public void getMovieListing(List<MovieListItems> movieItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, int sortMode, ProgressDialog progressDialog) {

        pDialog = progressDialog;
        adapterForAsyncTask = reviewAdapter;
        recyclerViewForAsyncTask = recyclerView;
        MovieItemsArray = movieItems;

        if(sortMode == 1)
        {
            webUrlName = "http://api.themoviedb.org/3/movie/popular?api_key=64fea2e5766a2302c9782fed4edbdc4a";
        }
        else
        {
            webUrlName = "http://api.themoviedb.org/3/movie/top_rated?api_key=64fea2e5766a2302c9782fed4edbdc4a";
        }

        AndroidNetworking.get(webUrlName)
                .setTag(webUrlName)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        MovieItemsArray.clear();
                        adapterForAsyncTask.notifyDataSetChanged();
                        try {
                            JSONArray jArr = response.getJSONArray("results");
                            Log.d("movie_resp", String.valueOf(jArr));
                            if(jArr.length() > 0)
                            {
                                for (int count = 0; count < jArr.length(); count++) {
                                    JSONObject obj = jArr.getJSONObject(count);
                                    MovieListItems movie = new MovieListItems();
                                    movie.setPoster(obj.getString("poster_path"));
                                    movie.setTitle(obj.getString("title"));
                                    movie.setVoteAvg(obj.getString("vote_average"));
                                    movie.setOverview(obj.getString("overview"));
                                    movie.setReleaseDate(obj.getString("release_date"));
                                    movie.setId(obj.getString("id"));
                                    movie.setPages(totPage);

                                    MovieItemsArray.add(movie);
                                }
                                adapterForAsyncTask.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.getMessage();
                            Toast.makeText(context, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        error.getErrorDetail();
                        Toast.makeText(context, "Error" + error.getErrorDetail(), Toast.LENGTH_LONG).show();

                    }
                });
    }
}
