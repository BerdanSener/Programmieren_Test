package at.ac.fhcampuswien.fhmdb.Exceptions;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Exception cause){
        super(String.format("Exception here:\n%s\n Exception cause:\n%s", message) , cause);
    }
}
