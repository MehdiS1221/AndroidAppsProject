package algonquin.cst2335.finalproject.hussein.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Metascore")
    private String metascore;

    @ColumnInfo(name = "BoxOffice")
    private String boxOffice;

    @ColumnInfo(name = "Website")
    private String website;

    @ColumnInfo(name = "imdbRating")
    private String imdbRating;

    @ColumnInfo(name = "imdbVotes")
    private String imdbVotes;

    @ColumnInfo(name = "Runtime")
    private String runtime;

    @ColumnInfo(name = "Language")
    private String language;

    @ColumnInfo(name = "Rated")
    private String rated;

    @ColumnInfo(name = "Production")
    private String production;

    @ColumnInfo(name = "Released")
    private String released;

    @ColumnInfo(name = "imdbID")
    private String imdbID;

    @ColumnInfo(name = "Plot")
    private String plot;

    @ColumnInfo(name = "Director")
    private String director;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Actors")
    private String actors;

    @ColumnInfo(name = "Response")
    private String response;

    @ColumnInfo(name = "Type")
    private String type;

    @ColumnInfo(name = "Awards")
    private String awards;

    @ColumnInfo(name = "DVD")
    private String dVD;

    @ColumnInfo(name = "Year")
    private String year;

    @ColumnInfo(name = "Poster")
    private String poster;

    @ColumnInfo(name = "Country")
    private String country;

    @ColumnInfo(name = "Genre")
    private String genre;

    @ColumnInfo(name = "Writer")
    private String writer;

    @ColumnInfo(name = "Error")
    private String error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getDVD() {
        return dVD;
    }

    public void setDVD(String dVD) {
        this.dVD = dVD;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}