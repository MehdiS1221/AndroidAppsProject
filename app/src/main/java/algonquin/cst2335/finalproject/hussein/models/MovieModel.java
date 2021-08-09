package algonquin.cst2335.finalproject.hussein.models;

import com.google.gson.annotations.SerializedName;

public class MovieModel {

    boolean showHeading;
    String category;

    @SerializedName("Type")
    private String type;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Title")
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowHeading() {
        return showHeading;
    }

    public void setShowHeading(boolean showHeading) {
        this.showHeading = showHeading;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}