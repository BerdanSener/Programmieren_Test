package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;

public class MoviesSchema{
    public String id;
    public String title;
    public ArrayList<String> genres;
    public int releaseYear;
    public String description;
    public String imgUrl;
    public int lengthInMinutes;
    public ArrayList<String> directors;
    public ArrayList<String> writers;
    public ArrayList<String> mainCast;
    public double rating;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public ArrayList<String> getWriters() {
        return writers;
    }

    public ArrayList<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }
}
