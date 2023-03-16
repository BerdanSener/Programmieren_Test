package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    HomeController homeController = new HomeController();


    // Failed test for question to prof.
    @Test
    void sort_movies_in_asc_order_if_parameter_is_true(){

        homeController.sortMovies(true);

        ArrayList<String> actual = new ArrayList<>();
        for(Movie movie : homeController.allMovies){
            actual.add(movie.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Life is Beautiful");
        expected.add("Puss in Boots");
        expected.add("Wolf of Wallstreet");
        for(String s : expected){
            System.out.println(s);
        }
        System.out.println("------------");
        for(String s : actual){
            System.out.println(s);
        }
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void filter_by_genre_adventure(){
        List<Movie> movies = Movie.initializeMovies();
        ArrayList<Movie> filteredByGenre = homeController.filterByGenre((ArrayList<Movie>) movies, Genre.ADVENTURE.toString());
        ArrayList<String> actual = new ArrayList<>();
        for (Movie m : filteredByGenre) {
            actual.add(m.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Life is Beautiful");
        expected.add("Puss in Boots");

        assertEquals(expected, actual);
    }

    @Test
    void return_no_movies_if_no_movie_is_listed_in_given_genre(){
        List<Movie> movies = Movie.initializeMovies();

        //No Movie is listed in drama
        ArrayList<Movie> filteredByGenre = homeController.filterByGenre((ArrayList<Movie>) movies, Genre.DRAMA.toString());
        ArrayList<String> actual = new ArrayList<>();
        for (Movie m : filteredByGenre) {
            actual.add(m.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void do_not_return_anything_if_movie_genre_is_null_when_filtering_by_genre(){
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Test","description test", null));

        ArrayList<Movie> filteredByGenre = homeController.filterByGenre((ArrayList<Movie>) movies, Genre.DRAMA.toString());
        ArrayList<String> actual = new ArrayList<>();
        for (Movie m : filteredByGenre) {
            actual.add(m.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void search_for_all_movies_that_contain_the_text_Puss(){
        List<Movie> movies = Movie.initializeMovies();

        ArrayList<Movie> searchedForText = homeController.searchForText(movies, "Puss");
        ArrayList<String> actual = new ArrayList<>();
        for (Movie m : searchedForText) {
            actual.add(m.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Puss in Boots");

        assertEquals(expected, actual);
    }

    @Test
    void when_text_query_is_empty_return_all_movies(){
        List<Movie> movies = Movie.initializeMovies();

        ArrayList<Movie> searchedForText = homeController.searchForText(movies, "");
        ArrayList<String> actual = new ArrayList<>();
        for (Movie m : searchedForText) {
            actual.add(m.getTitle());
        }

        ArrayList<String> expected = new ArrayList<>();
        for(Movie m : movies){
            expected.add(m.getTitle());
        }

        assertEquals(expected, actual);
    }
}