package at.ac.fhcampuswien.fhmdb.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;

    public String id;
    public int releaseYear;
    public String imgUrl;
    public int lengthInMinutes;
    public ArrayList<String> directors;
    public ArrayList<String> writers;
    public ArrayList<String> mainCast;
    public double rating;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public Movie(String title, String description, List<Genre> genres, String id, int releaseYear, String imgUrl, int lengthInMinutes, ArrayList<String> directors, ArrayList<String> writers, ArrayList<String> mainCast, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.id = id;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
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

        //Film 3 -> Puss in Boots
        genres = new ArrayList<>();
        genres.add(Genre.CRIME);
        genres.add(Genre.FAMILY);
        genres.add(Genre.ADVENTURE);
        movies.add(new Movie("Puss in Boots", "The puss in boots is a cool film about a puss in boots", genres));

        return movies;
    }

    public static ArrayList<Movie> schemaToMovie(ArrayList<MoviesSchema> movies){
        ArrayList<Movie> list = new ArrayList<>();
        for (MoviesSchema movie:movies) {
            Movie m = new Movie(movie.getTitle(), movie.getDescription(), Genre.stringToGenre(movie.getGenres()), movie.id, movie.releaseYear, movie.imgUrl, movie.lengthInMinutes, movie.directors, movie.writers, movie.mainCast, movie.rating);
            list.add(m);
        }
        return list;
    }

    public static boolean checkMovies(List<Movie> movies){
        boolean flag = true;
        outerloop:
        for(Movie m : movies){
            if(flag == true){
                if(!((m.getTitle() != null) && (m.getDescription() != null) && (m.getGenres() != null))){
                    flag = false;
                    break outerloop;
                }
            }
        }
        return flag;
    }
}
