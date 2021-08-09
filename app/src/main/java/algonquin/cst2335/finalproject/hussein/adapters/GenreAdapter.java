package algonquin.cst2335.finalproject.hussein.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.LockViewHolder> {

    Activity activity;
    List<String> list;

    public GenreAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public LockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LockViewHolder(LayoutInflater.from(activity).inflate(R.layout.design_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LockViewHolder holder, int position) {
        String genre = list.get(position);

        if (!TextUtils.isEmpty(genre)) {
            holder.tvGenre.setText(genre);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public static class LockViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenre;

        public LockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tv_genre);
        }
    }
}
