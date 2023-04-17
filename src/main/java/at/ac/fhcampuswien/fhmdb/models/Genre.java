package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public enum Genre {

    ACTION, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY,
    CRIME, DRAMA, DOCUMENTARY, FAMILY, FANTASY, HISTORY, HORROR,
    MUSICAL, MYSTERY, ROMANCE, SCIENCE_FICTION, SPORT, THRILLER, WAR,
    WESTERN;

    public static String[] toStringArray(){
        String[] genres = {"ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
                "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
                "WESTERN"};
        return genres;
    }

    public static List<Genre> stringToGenre(ArrayList<String> genreList){
        ArrayList<Genre> genres = new ArrayList<>();
        for (String category:genreList) {
            switch (category){
                case "ACTION":
                    genres.add(ACTION);
                    break;
                case "ADVENTURE":
                    genres.add(ADVENTURE);
                    break;
                case "ANIMATION":
                    genres.add(ANIMATION);
                    break;
                case "BIOGRAPHY":
                    genres.add(BIOGRAPHY);
                    break;
                case "COMEDY":
                    genres.add(COMEDY);
                    break;
                case "CRIME":
                    genres.add(CRIME);
                    break;
                case "DRAMA":
                    genres.add(DRAMA);
                    break;
                case "DOCUMENTARY":
                    genres.add(DOCUMENTARY);
                    break;
                case "FAMILY":
                    genres.add(FAMILY);
                    break;
                case "FANTASY":
                    genres.add(FANTASY);
                    break;
                case "HISTORY":
                    genres.add(HISTORY);
                    break;
                case "HORROR":
                    genres.add(HORROR);
                    break;
                case "MUSICAL":
                    genres.add(MUSICAL);
                    break;
                case "MYSTERY":
                    genres.add(MYSTERY);
                    break;
                case "ROMANCE":
                    genres.add(ROMANCE);
                    break;
                case "SCIENCE_FICTION":
                    genres.add(SCIENCE_FICTION);
                    break;
                case "SPORT":
                    genres.add(SPORT);
                    break;
                case "THRILLER":
                    genres.add(THRILLER);
                    break;
                case "WAR":
                    genres.add(WAR);
                    break;
                case "WESTERN":
                    genres.add(WESTERN);
                    break;
            }
        }
        return genres;
    }
}
