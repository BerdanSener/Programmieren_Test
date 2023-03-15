package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    HomeController homeController = new HomeController();

    @Test
    void sort_movies_in_asc_order_if_parameter_is_true(){

        homeController.sortMovies(true);

        ArrayList<String> actual = new ArrayList<>();
        for(Movie movie : homeController.getObservableMovies()){
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
}