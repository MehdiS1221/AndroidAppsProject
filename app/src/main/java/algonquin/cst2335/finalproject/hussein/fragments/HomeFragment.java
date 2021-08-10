package algonquin.cst2335.finalproject.hussein.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.hussein.activities.SearchActivity;
import algonquin.cst2335.finalproject.hussein.adapters.MovieAdapter;
import algonquin.cst2335.finalproject.hussein.models.MovieModel;
import algonquin.cst2335.finalproject.hussein.models.MoviesList;
import algonquin.cst2335.finalproject.hussein.models.SearchResponse;
import algonquin.cst2335.finalproject.hussein.network.ApiClient;
import algonquin.cst2335.finalproject.hussein.network.ApiInterface;
import algonquin.cst2335.finalproject.hussein.util.Constant;
import algonquin.cst2335.finalproject.hussein.util.UtilClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    final int MAX_MOVIES = 4;

    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<MovieModel> adapterList;

    List<MoviesList> moviesLists;
    TextView etSearch;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_movies, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_home);
        etSearch = view.findViewById(R.id.et_search);

        setupMoviesList();
        initAdapter();

        if (UtilClass.isConnected(requireContext())) {//check if there is internet
            getMoviesFromAPI();//get the movies from API
        } else {//otherwise show "no internet" alert dialog
            UtilClass.showNoInternetAlertDialog(requireContext());
        }

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivity(new Intent(requireActivity(), SearchActivity.class));
                }
                return false;
            }
        });

        return view;
    }

    private void initAdapter() {
        adapterList = new ArrayList<>();
        adapter = new MovieAdapter(requireActivity(), adapterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void getMoviesFromAPI() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        final GetMoviesTask getMoviesTask = new GetMoviesTask();
//        getMoviesTask.execute(moviesLists);
        getMovies(moviesLists);

    }

    private void setupMoviesList() {
        moviesLists = new ArrayList<>();
        String[] marvelMovies = {"Avengers", "Iron man", "Captain America", "Thor", "Ant-Man", "Spider-Man", "Guardians of the Galaxy", "X-Men"};
        String[] dcMovies = {"Batman", "Superman", "Aquaman", "Wonder Woman", "Flash", "Justice League", "Suicide Squad"};
        String[] actionMovies = {"Mission Impossible", "John Wick", "James Bond"};
        String[] fantasyMovies = {"Jurassic Park", "Jurassic World", "Pirates of the Caribbean", "Star Trek", "Star Wars", "Harry Potter", "Twilight", "Planet of the Apes"};
        String[] animatedMovies = {"Toy Story", "Cars", "The Incredibles", "Frozen", "Ice Age", "Shrek", "Despicable Me", "How to Train Your Dragon"};
        String[] horrorMovies = {"Jaws", "Nun", "Conjuring"};
        String[] scifiMovies = {"Matrix", "Hunger Games", "Godzilla", "Transformers", "Men in Black", "Terminator", "Back to the Future"};
        moviesLists.add(new MoviesList("Marvel Movies", marvelMovies));
        moviesLists.add(new MoviesList("Action", actionMovies));
        moviesLists.add(new MoviesList("DC Movies", dcMovies));
        moviesLists.add(new MoviesList("Science Fiction", scifiMovies));
        moviesLists.add(new MoviesList("Fantasy", fantasyMovies));
        moviesLists.add(new MoviesList("Animated", animatedMovies));
        moviesLists.add(new MoviesList("Horror", horrorMovies));
    }

    private void getMovies(List<MoviesList> moviesLists) {
        progressDialog = new ProgressDialog(requireActivity(), R.style.MyDialogTheme);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        for (int i = 0; i < moviesLists.size(); i++) {
            final String[] movies = moviesLists.get(i).getList();
            final String title = moviesLists.get(i).getTitle();
            for (int j = 0; j < movies.length; j++) {
                String movieName = movies[j];
                int finalI = i;
                apiInterface.searchMovie(Constant.API_KEY, Constant.FORMAT, movieName).enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            if (TextUtils.equals(response.body().getResponse(), "True")) {
                                List<MovieModel> newList = response.body().getSearch();
                                if (newList != null && !newList.isEmpty()) {
                                    newList.get(0).setShowHeading(true);
                                    newList.get(0).setCategory(movieName);
                                    if (newList.size() > MAX_MOVIES) {
                                        for (int k = 0; k < MAX_MOVIES; k++) {//Limit to only 4 movies of same title
                                            adapterList.add(newList.get(k));
                                        }
                                    } else {
                                        adapterList.addAll(newList);
                                    }

                                    if (finalI == moviesLists.size() - 1) {
                                        adapter.setList(adapterList);
                                        //hide the progress dialog after 3 seconds
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        }, 3000);
                                    }
                                }
                            } else {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    public class GetMoviesTask extends AsyncTask<List<MoviesList>, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("YAM", "Pre Execute");

        }

        @Override
        protected Void doInBackground(List<MoviesList>... moviesLists) {
            Log.d("YAM", "onBackground");

//            getMovies(moviesLists);
            return null;
        }

    }
}