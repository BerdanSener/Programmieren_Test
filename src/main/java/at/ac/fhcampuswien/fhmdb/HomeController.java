package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

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
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        filteredMovies = searchForText(allMovies, this.searchField.getText());
        if (this.genreComboBox.getValue() != null){
            filteredMovies = filterByGenre(filteredMovies, this.genreComboBox.getValue().toString());
        }
        resetMovies(filteredMovies);
    }

    public ArrayList<Movie> searchForText(List<Movie> movies, String searchText){
        ArrayList<Movie> foundMovies = new ArrayList<>();
        if (!searchText.isEmpty()){
            for (Movie m : movies) {
                if (m.getTitle().toLowerCase().contains(searchText.toLowerCase())){
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
    }

    public void resetMovies(ArrayList<Movie> movies){
        this.observableMovies.removeAll(observableMovies);
        this.observableMovies.addAll(movies);
    }

    public void resetMovies(){
        this.observableMovies.removeAll(observableMovies);
        this.observableMovies.addAll(allMovies);
        this.genreComboBox.getSelectionModel().clearSelection();
        sortBtn.setText("Sort (asc)");
        searchField.setText("");
    }
}