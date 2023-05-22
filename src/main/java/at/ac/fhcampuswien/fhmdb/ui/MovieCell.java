package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Button watchlist = new Button();
    private final Button details = new Button("Show Details");
    private final VBox layout = new VBox(title, detail, genre, watchlist, details);
    private static boolean check = false;

    public MovieCell(ClickEventHandler clickEventHandler){
        super();
        watchlist.setOnAction( (event) -> {

            if(((Button) event.getSource()).getText().equals("Add to Watchlist")){
                clickEventHandler.onClick(getItem(), false);
            }
            if (((Button) event.getSource()).getText().equals("Remove")){
                clickEventHandler.onClick(getItem(), true);
            }
        });

    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            genre.setText(movie.getGenres().toString());

            if(check == false){
                watchlist.setText("Add to Watchlist");
            }
            else{
                watchlist.setText("Remove");
            }

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }


    public static void setCheck(boolean b){
        MovieCell.check = b;

    }
}

