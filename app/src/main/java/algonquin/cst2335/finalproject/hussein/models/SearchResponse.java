package algonquin.cst2335.finalproject.hussein.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("Response")
    private String response;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("error")
    private String error;

    @SerializedName("Search")
    private List<MovieModel> search;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieModel> getSearch() {
        return search;
    }

    public void setSearch(List<MovieModel> search) {
        this.search = search;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}