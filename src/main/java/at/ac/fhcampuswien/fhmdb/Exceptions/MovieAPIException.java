package at.ac.fhcampuswien.fhmdb.Exceptions;


import java.io.IOException;

public class MovieAPIException extends IOException {
    public MovieAPIException(String message){
        super(message);
    }

    public MovieAPIException(String message, Throwable cause) {
        super(String.format("Exception here:\n%s\n Exception cause:\n%s", message) , cause);
    }

}
