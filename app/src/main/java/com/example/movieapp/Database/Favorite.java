package com.example.movieapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieName;
    private int movieId;
    private String posterUrl;
    private Float voteAverage;
    private String overview;
    private String releaseDate;


    public void setId(int id){
        this.id = id;
    }

    public void setMovieName(String movieName){
        this.movieName = movieName;
    }

    public void setMovieId(int movieId){
        this.movieId = movieId;
    }

    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl;}

    public void setVoteAverage(Float voteAverage) { this.voteAverage = voteAverage;}

    public void setOverview(String overview) {this.overview = overview;}

    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}

    public int getId(){
        return id;
    }

    public String getMovieName(){
        return movieName;
    }

    public int getMovieId(){
        return movieId;
    }

    public String getPosterUrl() { return posterUrl;}

    public Float getVoteAverage() { return voteAverage;}

    public String getOverview() {return overview;}

    public String getReleaseDate() { return releaseDate;}
}
