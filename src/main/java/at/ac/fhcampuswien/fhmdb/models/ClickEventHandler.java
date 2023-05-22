package at.ac.fhcampuswien.fhmdb.models;

@FunctionalInterface
public interface ClickEventHandler {
    void onClick(Movie movie);
}
