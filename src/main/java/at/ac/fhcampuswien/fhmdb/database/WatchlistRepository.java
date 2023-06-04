package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DBException;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;


    public WatchlistRepository() throws DBException{
        this.dao = Database.getDatabase().getDao();
    }

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws DBException {
        try {
            dao.delete(movie);
            System.out.println("hallo entfertn");
        } catch (SQLException e) {
            throw new DBException("Fehler beim Entfernen des Eintrags in der Datenbank", e);
        }

    }

    public List<WatchlistMovieEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }


    //frage???
    public void addToWatchlist(WatchlistMovieEntity movie) throws DBException {
        try {
            if (dao.queryForEq("title", movie.getTitle()).isEmpty()) {
                try {
                    dao.create(movie);
                } catch (SQLException e) {
                    throw new DBException("Fehler beim Erstellen des Eintrags in der Datenbank", e);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Fehler bei der Anfrage der Datenbank ", e);
        }
    }
}