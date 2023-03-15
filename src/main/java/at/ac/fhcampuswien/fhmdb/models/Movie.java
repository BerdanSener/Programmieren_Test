package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres(){return genres;}

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();

        //Film 1 -> Wolf of Wallstreet
        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.COMEDY);
        genres.add(Genre.ACTION);
        movies.add(new Movie("Wolf of Wallstreet", "A film about a Wolf", genres));

        //Film 2 -> Life is Beautiful
        genres = new ArrayList<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.ROMANCE);
        movies.add(new Movie("Life is Beautiful", "A film about a beautiful life", genres));

        //Film 2 -> Life is Beautiful
        genres = new ArrayList<>();
        genres.add(Genre.CRIME);
        genres.add(Genre.FAMILY);
        genres.add(Genre.ADVENTURE);
        movies.add(new Movie("Puss in Boots", "The puss in boots is a cool film about a puss in boots", genres));

        return movies;
    }
}
