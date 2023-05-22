package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.*;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
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
    public TextField releaseYearField;

    @FXML
    public TextField ratingField;

    @FXML
    public JFXButton sortBtn;


    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private WatchlistRepository watchlistRepository = new WatchlistRepository();

    private final ClickEventHandler onAddToWatchlistClicked = (movie, b) -> {
        WatchlistMovieEntity m = new WatchlistMovieEntity(movie);
        try {
            if(!b) {
                watchlistRepository.addToWatchlist(m);
                //System.out.println("add bednigs");
            }else{
                watchlistRepository.removeFromWatchlist(m);
                //System.out.println("remove bedingung: " + m.getTitle());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<Movie> movies = MovieAPI.loadMovies();
            observableMovies.addAll(movies);         // add dummy data to observable list

            // Code to test functions

            for(Movie testmovies : getMoviesBetweenYears(movies, 1999, 2005)){
                System.out.println("Title: " + testmovies.getTitle());
            }


            System.out.println(countMoviesFrom(movies, "Frank Darabont"));

            System.out.println(getLongestMovieTitle(movies));
            System.out.println(getMostPopularActor(movies));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data

        genreComboBox.getItems().addAll(Genre.toStringArray());
        genreComboBox.setPromptText("Filter by Genre");



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
        if(!this.ratingField.getText().isEmpty() && this.ratingField.getText().matches("^[1-9](\\.[0-9])?$|^10(\\.0)?$")){
            params.put("ratingFrom", this.ratingField.getText());
        }
        if(!this.releaseYearField.getText().isEmpty() && this.releaseYearField.getText().matches("^(19[0-9]{2}|20[0-1][0-9]|202[0-3])$")){
            params.put("releaseYear", this.releaseYearField.getText());
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
        this.ratingField.setText("");
        this.releaseYearField.setText("");
        sortBtn.setText("Sort (asc)");
        searchField.setText("");
    }

    public String getMostPopularActor(List<Movie> movies){
        Map<String, Long> movieActor = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<String, Long>> highestAmount = movieActor.entrySet().stream().max(Map.Entry.comparingByValue());


        return highestAmount.get().getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies){
        Optional<String> movieTitle = movies.stream()
                .map(movie -> movie.getTitle())
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
                .filter(movie -> (movie.getYear() >= startYear) && (movie.getYear() <= endYear))
                .collect(Collectors.toList());
    }

    public void toHomeScreen(Event event) {
        resetMovies();
        MovieCell.setCheck(false);
    }

    public void toWatchlistScreen(Event event) throws SQLException {
        this.resetMovies(Movie.watchlistToMovie(this.watchlistRepository.getAll()));
        MovieCell.setCheck(true);
    }

    public void toAboutScreen(Event event) {
        this.observableMovies.removeAll(observableMovies);
    }
}