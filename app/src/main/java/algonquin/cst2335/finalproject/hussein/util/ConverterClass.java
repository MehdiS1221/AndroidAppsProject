package algonquin.cst2335.finalproject.hussein.util;

import algonquin.cst2335.finalproject.hussein.database.MovieEntity;
import algonquin.cst2335.finalproject.hussein.models.MovieDetails;
import algonquin.cst2335.finalproject.hussein.models.MovieModel;

//this class will convert the MovieEntity class to MovieDetails class and vice versa
public class ConverterClass {

    public static MovieEntity getEntityFromModel(MovieDetails model) {
        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setActors(model.getActors());
        movieEntity.setAwards(model.getAwards());
        movieEntity.setBoxOffice(model.getBoxOffice());
        movieEntity.setCountry(model.getCountry());
        movieEntity.setDirector(model.getDirector());
        movieEntity.setDVD(model.getDVD());
        movieEntity.setGenre(model.getGenre());
        movieEntity.setImdbID(model.getImdbID());
        movieEntity.setImdbRating(model.getImdbRating());
        movieEntity.setImdbVotes(model.getImdbVotes());
        movieEntity.setLanguage(model.getLanguage());
        movieEntity.setMetascore(model.getMetascore());
        movieEntity.setPlot(model.getPlot());
        movieEntity.setPoster(model.getPoster());
        movieEntity.setProduction(model.getProduction());
        movieEntity.setRated(model.getRated());
        movieEntity.setReleased(model.getReleased());
        movieEntity.setTitle(model.getTitle());
        movieEntity.setType(model.getType());
        movieEntity.setWebsite(model.getWebsite());
        movieEntity.setWriter(model.getWriter());
        movieEntity.setYear(model.getYear());

        return movieEntity;
    }

    public static MovieDetails getMovieDetailModelFromEntity(MovieEntity entity) {
        MovieDetails movieModel = new MovieDetails();

        movieModel.setActors(entity.getActors());
        movieModel.setAwards(entity.getAwards());
        movieModel.setBoxOffice(entity.getBoxOffice());
        movieModel.setCountry(entity.getCountry());
        movieModel.setDirector(entity.getDirector());
        movieModel.setDVD(entity.getDVD());
        movieModel.setGenre(entity.getGenre());
        movieModel.setImdbID(entity.getImdbID());
        movieModel.setImdbRating(entity.getImdbRating());
        movieModel.setImdbVotes(entity.getImdbVotes());
        movieModel.setLanguage(entity.getLanguage());
        movieModel.setMetascore(entity.getMetascore());
        movieModel.setPlot(entity.getPlot());
        movieModel.setPoster(entity.getPoster());
        movieModel.setProduction(entity.getProduction());
        movieModel.setRated(entity.getRated());
        movieModel.setReleased(entity.getReleased());
        movieModel.setTitle(entity.getTitle());
        movieModel.setType(entity.getType());
        movieModel.setWebsite(entity.getWebsite());
        movieModel.setWriter(entity.getWriter());
        movieModel.setYear(entity.getYear());

        return movieModel;
    }

    public static MovieModel getMovieModelFromEntity(MovieEntity entity) {
        MovieModel movieModel = new MovieModel();

        movieModel.setCategory("");
        movieModel.setPoster(entity.getPoster());
        movieModel.setTitle(entity.getTitle());
        movieModel.setType(entity.getType());
        movieModel.setImdbID(entity.getImdbID());
        movieModel.setYear(entity.getYear());

        return movieModel;
    }
}
