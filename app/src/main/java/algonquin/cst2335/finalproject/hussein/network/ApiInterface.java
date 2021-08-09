package algonquin.cst2335.finalproject.hussein.network;

import algonquin.cst2335.finalproject.hussein.models.MovieDetails;
import algonquin.cst2335.finalproject.hussein.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/")
    Call<SearchResponse> searchMovie(
            @Query("apikey") String apiKey,
            @Query("r") String format,
            @Query("s") String keyword);

    @GET("/")
    Call<MovieDetails> getMovieDetailById(
            @Query("apikey") String apiKey,
            @Query("r") String format,
            @Query("i") String id);


}
