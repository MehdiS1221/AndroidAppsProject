package algonquin.cst2335.finalproject.hussein.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.hussein.adapters.GenreAdapter;
import algonquin.cst2335.finalproject.hussein.database.MovieEntity;
import algonquin.cst2335.finalproject.hussein.database.MoviesDatabase;
import algonquin.cst2335.finalproject.hussein.util.UtilClass;

import java.util.Arrays;
import java.util.List;

public class SavedMovieDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GenreAdapter adapter;
    List<String> list;

    TextView tvName, tvCategory, tvYear, tvRated, tvRuntime, tvPlot, tvRating, tvReviews, tvMetaScore,
            tvDirectors, tvWriters, tvActors, tvAwards, tvLanguages, tvCountry;
    ImageView ivCover, ivPoster, ivSave;

    MovieEntity movieEntity;
    String imdbId;
    boolean isSaved;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        rootView = findViewById(R.id.root_view_movie_details);
        ivSave = findViewById(R.id.iv_save);
        tvName = findViewById(R.id.tv_movie_name);
        tvCategory = findViewById(R.id.tv_category);
        tvYear = findViewById(R.id.tv_year);
        tvRated = findViewById(R.id.tv_rated);
        tvRuntime = findViewById(R.id.tv_runtime);
        ivCover = findViewById(R.id.iv_cover);
        ivPoster = findViewById(R.id.iv_poster);
        recyclerView = findViewById(R.id.recycler_view_genre);
        tvPlot = findViewById(R.id.tv_plot);
        tvRating = findViewById(R.id.tv_rating);
        tvReviews = findViewById(R.id.tv_reviews);
        tvMetaScore = findViewById(R.id.tv_meta_score);
        tvDirectors = findViewById(R.id.tv_directors);
        tvWriters = findViewById(R.id.tv_writers);
        tvActors = findViewById(R.id.tv_actors);
        tvAwards = findViewById(R.id.tv_awards);
        tvLanguages = findViewById(R.id.tv_languages);
        tvCountry = findViewById(R.id.tv_country);

        setTitle("Movie Details");
        isSaved = true;
        setDrawable();

        imdbId = getIntent().getStringExtra("ID");
        if (TextUtils.isEmpty(imdbId)) return;//return if the imdbId is empty
        getMovieDetail(imdbId);

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPoster();
            }
        });

        ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPoster();
            }
        });

        ivSave.setOnClickListener(v -> {
            saveMovie();
            isSaved = !isSaved;
            setDrawable();
        });
    }

    private void saveMovie() {
        if (isSaved) {
            //remove the movie from the saved database
            MoviesDatabase.getInstance(SavedMovieDetailActivity.this).movieDao().deleteMovie(movieEntity.getImdbID());
            Snackbar.make(rootView, "Movie is removed from the Database!", Snackbar.LENGTH_SHORT).show();
        } else {
            //add the movie in the database
            MoviesDatabase.getInstance(SavedMovieDetailActivity.this).movieDao().insertMovie(movieEntity);
            Snackbar.make(rootView, "Movie is saved in database successfully!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setDrawable() {
        if (isSaved) {
            ivSave.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            ivSave.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    private void viewPoster() {
        if (movieEntity != null && movieEntity.getPoster() != null) {
            Intent intent = new Intent(SavedMovieDetailActivity.this, ImageViewerActivity.class);
            intent.putExtra("IMAGE", movieEntity.getPoster());
            startActivity(intent);
        }
    }

    private void setDataToViews(MovieEntity movieDetails) {

        //Movie Details
        tvName.setText(movieDetails.getTitle());
        tvCategory.setText(movieDetails.getType());
        tvYear.setText(movieDetails.getYear());
        tvRated.setText(movieDetails.getRated());
        tvRuntime.setText(movieDetails.getRuntime());
        tvPlot.setText(movieDetails.getPlot());
        tvRating.setText(movieDetails.getImdbRating());
        String reviewsCount = UtilClass.getShortAmount(movieDetails.getImdbVotes());
        tvReviews.setText(reviewsCount + " Reviews");
        tvMetaScore.setText(movieDetails.getMetascore());
        tvDirectors.setText(movieDetails.getDirector());
        tvWriters.setText(movieDetails.getWriter());
        tvActors.setText(movieDetails.getActors());
        tvAwards.setText(movieDetails.getAwards());
        tvLanguages.setText(movieDetails.getLanguage());
        tvCountry.setText(movieDetails.getCountry());

        //Images
        final String poster = movieDetails.getPoster();
        Glide.with(SavedMovieDetailActivity.this).load(poster).into(ivCover);
        Glide.with(SavedMovieDetailActivity.this).load(poster).into(ivPoster);

        // List of Genres
        final String genreString = movieDetails.getGenre();
        list = Arrays.asList(genreString.split(","));
        adapter = new GenreAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void getMovieDetail(String imdbID) {
        movieEntity = MoviesDatabase.getInstance(SavedMovieDetailActivity.this).movieDao().getMovie(imdbID);
        setDataToViews(movieEntity);
    }

}