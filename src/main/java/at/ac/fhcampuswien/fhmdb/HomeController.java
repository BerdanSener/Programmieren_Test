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
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

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
        observableMovies.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //movieListView.getItems().clear();
                System.out.println("Something changed");
                movieListView.setItems(observableMovies);   // set data of observable list to list view
                movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data*/
            }
        });



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

        //searchBtn.setDisable(true);

        searchBtn.setOnAction(actionEvent -> {searchMovies();});
    }

    public void sortMovies(boolean ascOrDesc){
        // asc -> true
        if(ascOrDesc){

        }

        // desc -> false
        if(!ascOrDesc){

        }

    }

    public void searchMovies(){
        boolean flag = true; // reset List
        List<Movie> sortedList = new ArrayList<>();
        for(int i = 0; i < observableMovies.size(); i++){
            if(searchField.getText().contains(observableMovies.get(i).getTitle())){
                sortedList.add(observableMovies.get(i));
                System.out.println("test " + i + "ist dabei");
            }
        }
        if(!sortedList.isEmpty() && !(searchField.getText().isEmpty())){
            observableMovies.removeAll(observableMovies);
            observableMovies.addAll(sortedList);
            flag = false;
        }else{
            System.out.println("kein text aber leer");
            observableMovies.removeAll(observableMovies);
            sortedList = Movie.initializeMovies();
            observableMovies.addAll(sortedList);
        }
        //observableMovies.addAll(sortedList);
            /*List<Genre> genrelisttest = new ArrayList<>();
            genrelisttest.add(Genre.ACTION);
            observableMovies.add(new Movie("test", "test2", genrelisttest));*/
    }

    public void disableSearchButton(){
        if(searchField.getText().isEmpty()){
            searchBtn.setDisable(true);
        }
        else{
            searchBtn.setDisable(false);
        }
    }
}