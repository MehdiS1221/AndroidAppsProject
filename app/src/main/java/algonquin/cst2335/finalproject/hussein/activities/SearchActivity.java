package algonquin.cst2335.finalproject.hussein.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.hussein.adapters.MovieAdapter;
import algonquin.cst2335.finalproject.hussein.models.MovieModel;
import algonquin.cst2335.finalproject.hussein.models.SearchResponse;
import algonquin.cst2335.finalproject.hussein.network.ApiClient;
import algonquin.cst2335.finalproject.hussein.network.ApiInterface;
import algonquin.cst2335.finalproject.hussein.util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText etSearch;
    Button btnSearch;

    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<MovieModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.recycler_view_home);
        list = new ArrayList<>();

        getEditTextDataFromSharedPref();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etSearch.getText().toString();
                if (!text.isEmpty()) {
                    search(text);
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                storedEditTextToSharedPref(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void storedEditTextToSharedPref(String toString) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("edit_text", toString);
        editor.apply();
    }

    private void getEditTextDataFromSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String storedText = sharedPreferences.getString("edit_text", "");
        etSearch.setText(storedText);
    }

    private void search(String keyword) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.searchMovie(Constant.API_KEY, Constant.FORMAT, keyword).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    if (TextUtils.equals(response.body().getResponse(), "True")) {
                        list = response.body().getSearch();
                        setAdapter();
                    } else {
                        Log.e("YAM FAlse respnse : ", response.message());
                    }
                } else {
                    Log.e("YAM noSuccessful : ", response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("YAM Failed : ", t.getMessage());
                t.printStackTrace();
            }
        });

    }

    private void setAdapter() {
        adapter = new MovieAdapter(SearchActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setAdapter(adapter);
    }
}