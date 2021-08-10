package algonquin.cst2335.finalproject.hussein.adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.hussein.activities.MovieDetailActivity;
import algonquin.cst2335.finalproject.hussein.activities.SavedMovieDetailActivity;
import algonquin.cst2335.finalproject.hussein.database.MovieDao;
import algonquin.cst2335.finalproject.hussein.database.MoviesDatabase;
import algonquin.cst2335.finalproject.hussein.models.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.LockViewHolder> {

    Activity activity;
    List<MovieModel> list;

    public MovieAdapter(Activity activity, List<MovieModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public LockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LockViewHolder(LayoutInflater.from(activity).inflate(R.layout.design_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LockViewHolder holder, int position) {
        MovieModel current = list.get(position);

        if (current.isShowHeading()) {
            holder.tvHeading.setVisibility(View.VISIBLE);
            holder.tvHeading.setText(current.getCategory());
        } else {
            holder.tvHeading.setVisibility(View.GONE);
        }

        final String name = current.getTitle();
        if (!TextUtils.isEmpty(name)) {
            holder.tvName.setText(name);
        }
        final String genre = current.getType();
        if (!TextUtils.isEmpty(genre)) {
            holder.tvGenre.setText(genre);
        }

        final String year = current.getYear();
        if (!TextUtils.isEmpty(year)) {
            holder.tvYear.setText("Released in " + year);
        }
        String image = current.getPoster();
        Glide.with(activity)
                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.bg_image_placeholder).error(R.drawable.bg_image_placeholder))
                .load(image)
                .into(holder.ivPoster);

        holder.itemView.setOnClickListener(v -> {
            final String imdbID = current.getImdbID();
            final MovieDao movieDao = MoviesDatabase.getInstance(activity).movieDao();
            Intent intent = new Intent(activity, MovieDetailActivity.class);
            if (movieDao.movieExists(imdbID)) {//if movie is present in the database
                //open the saved movie activity that will show movie details from the database
                intent = new Intent(activity, SavedMovieDetailActivity.class);
            }
            intent.putExtra("ID", imdbID);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<MovieModel> newList) {
        for (int i = 0; i < newList.size(); i++) {
            list.add(newList.get(i));
            notifyItemInserted(list.size() - 1);
        }
    }

    public void setList(List<MovieModel> adapterList) {
        this.list = adapterList;
        notifyDataSetChanged();
    }

    public static class LockViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvYear, tvGenre, tvHeading;
        ImageView ivPoster;

        public LockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.tv_heading);
            tvName = itemView.findViewById(R.id.tv_movie_name);
            tvYear = itemView.findViewById(R.id.tv_movie_year);
            tvGenre = itemView.findViewById(R.id.tv_movie_type);
            ivPoster = itemView.findViewById(R.id.iv_poster);
        }
    }
}
