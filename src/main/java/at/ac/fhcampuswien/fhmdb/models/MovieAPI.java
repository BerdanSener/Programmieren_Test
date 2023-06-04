package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieAPIException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovieAPI {

    private static String url = "https://prog2.fh-campuswien.ac.at/movies";

    public static ArrayList<Movie> loadMovies(HashMap<String, String> params) throws MovieAPIException {
        try{
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String, String> e:params.entrySet()) {
                urlBuilder.addQueryParameter(e.getKey(), e.getValue());
            }

            String customUrl = urlBuilder.build().toString();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(customUrl)
                    .addHeader("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(responseBody.byteStream()));
            String content = input.readLine();
            input.close();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<MoviesSchema> movies = objectMapper.readValue(content, new TypeReference<ArrayList<MoviesSchema>>() {});
            if(!response.isSuccessful()){
                throw new MovieAPIException("Fehler beim Laden der Filme. Statuscode: " + response.code());
            }
            return Movie.schemaToMovie(movies);

        }catch (IOException e){
            throw new MovieAPIException("Fehler beim Laden der Filme.", e);
        }

    }

    public static ArrayList<Movie> loadMovies() throws IOException{
        try{
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(responseBody.byteStream()));
            String content = input.readLine();
            input.close();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<MoviesSchema> movies = objectMapper.readValue(content, new TypeReference<ArrayList<MoviesSchema>>() {});
            if(!response.isSuccessful()){
                throw new MovieAPIException("Fehler beim Laden der Filme. Statuscode: " + response.code());
            }
            return Movie.schemaToMovie(movies);
        }catch (IOException e){
            throw new MovieAPIException("Fehler beim Laden der Filme.", e);
        }

    }
}
