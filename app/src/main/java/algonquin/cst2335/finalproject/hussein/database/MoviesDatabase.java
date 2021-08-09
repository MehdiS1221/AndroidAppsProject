package algonquin.cst2335.finalproject.hussein.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MovieEntity.class, exportSchema = false, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public static MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MoviesDatabase.class, "moviesDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract MovieDao movieDao();
}
