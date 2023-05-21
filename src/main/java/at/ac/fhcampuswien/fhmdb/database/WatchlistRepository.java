package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;


    public WatchlistRepository(){
        this.dao = Database.getDatabase().getDao();
    }

    void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException {
        dao.delete(movie);
    }

    List<WatchlistMovieEntity> getAll() throws SQLException {
        return dao.queryForAll();

    }

    void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        dao.create(movie);
    }
}