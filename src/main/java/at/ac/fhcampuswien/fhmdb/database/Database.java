package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistdb";



    public static final String username = "user";
    public static final String password = "pw123";

    private static ConnectionSource connectionSource;

    private Dao<WatchlistMovieEntity, Long> dao;


    private static Database instance;

    private Database(){
        try{
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Database getDatabase(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, username, password);
    }

    public void testDB() throws SQLException {
        WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity("123", "test", "halo123", "comedy", 1999, "blablabal", 120, 9.2);
        dao.create(watchlistMovieEntity);
    }

    public void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    public Dao<WatchlistMovieEntity, Long> getDao() {
        return dao;
    }

    public static ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}