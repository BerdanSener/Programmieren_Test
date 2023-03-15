package at.ac.fhcampuswien.fhmdb.models;

public enum Genre {

    ACTION, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY,
    CRIME, DRAMA, DOCUMENTARY, FAMILY, FANTASY, HISTORY, HORROR,
    MUSICAL, MYSTERY, ROMANCE, SCIENCE_FICTION, SPORT, THRILLER, WAR,
    WESTERN;

    public static String[] toStringArray(){
        String[] genres = {"---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
                "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
                "WESTERN"};
        return genres;
    }
}
