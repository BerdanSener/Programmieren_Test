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
/*
    public ArrayList<Movie> searchForText(List<Movie> movies, String searchText){
        ArrayList<Movie> foundMovies = new ArrayList<>();
        if (!searchText.isEmpty()){
            for (Movie m : movies) {
                if (m.getDescription().toLowerCase().contains(searchText.toLowerCase())){
                    foundMovies.add(m);
                }
            }
        }else {
            foundMovies.addAll(allMovies);
        }
        return foundMovies;
    }

    public ArrayList<Movie> filterByGenre(ArrayList<Movie> movies, String genre){
        ArrayList<Movie> filteredList = new ArrayList<>();
        for (Movie m : movies) {
            if(m.getGenres() != null){
                for (Genre g : m.getGenres()) {
                    if (g.toString().equals(genre)){
                        filteredList.add(m);
                        break;
                    }
                }
            }
        }
        return filteredList;
    }*/

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
        sortBtn.setText("Sort (asc)");
        searchField.setText("");
    }

    public String getMostPopularActor(List<Movie> movies){
        return null;
    }

    public int getLongestMovieTitle(List<Movie> movies){
        return 1;
    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return 1;
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        return null;
    }
}