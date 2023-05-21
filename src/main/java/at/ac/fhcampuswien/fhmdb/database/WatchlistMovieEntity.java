package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "Watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String apiId;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String genres;

    @DatabaseField()
    private int releaseYear;

    @DatabaseField()
    private String imgUrl;

    @DatabaseField()
    private int lengthInMinutes;

    @DatabaseField()
    private double rating;


    public WatchlistMovieEntity(){
    }

    public WatchlistMovieEntity(String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public WatchlistMovieEntity(Movie movie){
        this.apiId = movie.id;
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genres = genresToString(movie.getGenres());
        this.releaseYear = movie.releaseYear;
        this.imgUrl = movie.imgUrl;
        this.lengthInMinutes = movie.lengthInMinutes;
        this.rating = movie.rating;
    }

    public String genresToString(List<Genre> genres) {
        String genreString = null;
        for(int i = 0; i < genres.size(); i++){
            if(i < (genres.size() - 1)){
                genreString += String.valueOf(genres.get(i)) + ", ";
            }
            genreString += String.valueOf(genres.get(i));
        }

        return genreString;
    }
}



