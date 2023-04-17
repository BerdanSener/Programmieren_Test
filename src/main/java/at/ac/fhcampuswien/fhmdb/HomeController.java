package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.MoviesSchema;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public JFXButton resetBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<Movie> movies = MovieAPI.loadMovies();
            observableMovies.addAll(movies);         // add dummy data to observable list

            // Code to test functions

            for(Movie testmovies : getMoviesBetweenYears(movies, 1999, 2005)){
                System.out.println("Title: " + testmovies.getTitle());
            }
            //System.out.println(getMoviesBetweenYears(movies, 1999, 2005));


            System.out.println(countMoviesFrom(movies, "Frank Darabont"));

            System.out.println(getLongestMovieTitle(movies));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.getItems().addAll(Genre.toStringArray());
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.getItems().add(1994);
        releaseYearComboBox.setPromptText("Filter by Release Year");

        ratingComboBox.getItems().add(10);
        ratingComboBox.getItems().add(9);
        ratingComboBox.getItems().add(8);
        ratingComboBox.getItems().add(7);
        ratingComboBox.getItems().add(6);
        ratingComboBox.getItems().add(5);
        ratingComboBox.getItems().add(4);
        ratingComboBox.getItems().add(3);
        ratingComboBox.getItems().add(2);
        ratingComboBox.getItems().add(1);
        ratingComboBox.setPromptText("Filter by Rating");
        // initialize UI stuff



        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                this.sortMovies(true);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                this.sortMovies(false);
                sortBtn.setText("Sort (asc)");
            }
        });
        searchBtn.setOnAction(actionEvent -> searchMovies());
        resetBtn.setOnAction(actionEvent -> resetMovies());


    }


    public void sortMovies(boolean ascOrDesc){
        // asc -> true
        if(ascOrDesc){
            this.observableMovies.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if(o1.getTitle().toLowerCase().charAt(0) > o2.getTitle().toLowerCase().charAt(0)){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            });
        }

        // desc -> false
        if(!ascOrDesc){
            this.observableMovies.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if(o1.getTitle().toLowerCase().charAt(0) > o2.getTitle().toLowerCase().charAt(0)){
                        return 0;
                    }else {
                        return 1;
                    }
                }
            });
        }
    }

    public void searchMovies() {
        HashMap<String, String> params = new HashMap<>();
        if(this.genreComboBox.getValue() != null){
            params.put("genre", this.genreComboBox.getValue() + "");
        }
        if(this.ratingComboBox.getValue() != null){
            params.put("ratingFrom", this.ratingComboBox.getValue() + "");
        }
        if(this.releaseYearComboBox.getValue() != null){
            params.put("releaseYear", this.releaseYearComboBox.getValue() + "");
        }
        if (!this.searchField.getText().isEmpty()){
            params.put("query", this.searchField.getText());
        }

        try {
            resetMovies(MovieAPI.loadMovies(params));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetMovies(ArrayList<Movie> movies){
        this.observableMovies.removeAll(observableMovies);
        this.observableMovies.addAll(movies);
    }

    public void resetMovies(){
        this.observableMovies.removeAll(observableMovies);
        try {
            this.observableMovies.addAll(MovieAPI.loadMovies());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.genreComboBox.getSelectionModel().clearSelection();
        this.ratingComboBox.getSelectionModel().clearSelection();
        this.releaseYearComboBox.getSelectionModel().clearSelection();
        sortBtn.setText("Sort (asc)");
        searchField.setText("");
    }

    public String getMostPopularActor(List<Movie> movies){
        Stream.of(movies).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);


        return null;
    }

    public int getLongestMovieTitle(List<Movie> movies){
        Optional<String> movieTitle = movies.stream()
                .map(title -> title.getTitle())
                .max(Comparator.comparingInt(String::length));


        return movieTitle.get().length();
    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream()
                .flatMap(movie -> movie.getDirectors().stream())
                .filter(direc -> direc.equals(director)).count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        return movies.stream()
                .filter(movie -> movie.getYear() >= startYear && movie.getYear() <= endYear)
                .collect(Collectors.toList());
    }
}