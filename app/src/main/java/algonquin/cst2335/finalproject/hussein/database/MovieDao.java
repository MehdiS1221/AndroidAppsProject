package algonquin.cst2335.finalproject.hussein.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from movie")
    List<MovieEntity> getAllMovies();

    @Query("select * from movie where id = :id")
    MovieEntity getMovie(int id);

    @Query("select * from movie where imdbID = :imdbID")
    MovieEntity getMovie(String imdbID);

    @Query("DELETE from movie where imdbID = :imdbID")
    void deleteMovie(String imdbID);

    @Query("select EXISTS (select * from movie where imdbID = :imdbID)")
    boolean movieExists(String imdbID);

    @Insert
    void insertMovie(MovieEntity movieEntity);

    @Update
    void updateMovie(MovieEntity movieEntity);

    @Delete
    void deleteMovie(MovieEntity movieEntity);
}
