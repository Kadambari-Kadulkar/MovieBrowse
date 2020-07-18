package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieListItems implements Parcelable {
    public String id;
    public String title;
    public String poster;
    public String overview;
    public String voteAvg;
    public String releaseDate;
    public String pages;

    public MovieListItems(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        voteAvg = in.readString();
        releaseDate = in.readString();
        pages = in.readString();
    }

    public static final Creator<MovieListItems> CREATOR = new Creator<MovieListItems>() {
        @Override
        public MovieListItems createFromParcel(Parcel in) {
            return new MovieListItems(in);
        }

        @Override
        public MovieListItems[] newArray(int size) {
            return new MovieListItems[size];
        }
    };

    public MovieListItems() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(voteAvg);
        parcel.writeString(releaseDate);
    }
}
