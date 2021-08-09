package algonquin.cst2335.finalproject.hussein.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.hussein.adapters.MovieAdapter;
import algonquin.cst2335.finalproject.hussein.database.MovieEntity;
import algonquin.cst2335.finalproject.hussein.database.MoviesDatabase;
import algonquin.cst2335.finalproject.hussein.models.MovieModel;
import algonquin.cst2335.finalproject.hussein.util.ConverterClass;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<MovieModel> adapterList;

    EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_movies, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_saved);
        etSearch = view.findViewById(R.id.et_search);

        initAdapter();
        getMoviesFromDatabase();
        setupSearch();
        return view;
    }


    private void initAdapter() {
        adapterList = new ArrayList<>();
        adapter = new MovieAdapter(requireActivity(), adapterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void getMoviesFromDatabase() {
        List<MovieEntity> moviesFromDB = MoviesDatabase.getInstance(getContext()).movieDao().getAllMovies();
        for (int i = 0; i < moviesFromDB.size(); i++) {
            adapterList.add(ConverterClass.getMovieModelFromEntity(moviesFromDB.get(i)));
        }
        adapter.setList(adapterList);
    }


    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    searchList(s.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchList(String keyword) {
        List<MovieModel> filteredList = new ArrayList<>();
        for (int i = 0; i < adapterList.size(); i++) {
            MovieModel movie = adapterList.get(i);
            if (movie.getTitle().toLowerCase().contains(keyword)
                    || movie.getType().toLowerCase().contains(keyword)
                    || movie.getYear().toLowerCase().contains(keyword)) {
                filteredList.add(movie);
            }
        }
        adapter.setList(filteredList);
    }
}