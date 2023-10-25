package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.newsapp.ApiService.NewsInstance;
import com.example.newsapp.modles.ApiModel;
import com.example.newsapp.modles.mainNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HealthFragment extends Fragment {
    SearchView searchView;
    RecyclerNewsAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<ApiModel> modelClassArrayList;
    private static String API_KEY = "df4461389a174f80aea73def485b5f1c";
    ArrayList<ApiModel> backup;
    private int page = 1;

    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;
    private boolean isLoading = false;
    private boolean isLastPage = false;


    public HealthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        recyclerView = view.findViewById(R.id.recyclerHealth);
        searchView = view.findViewById(R.id.search_menuu);
        modelClassArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.healthProgressBar);
        backup = new ArrayList<>();
        adapter = new RecyclerNewsAdapter(getContext(), modelClassArrayList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });


        getApi();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        isLoading = true;
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        getApi();

                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    private void getApi() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("country", "in");
        parameters.put("pageSize", "5");
        parameters.put("category", "health");
        parameters.put("apiKey", API_KEY);
        parameters.put("page", String.valueOf(page));

        NewsInstance.getInstance()
                .getApi()
                .getApiModel(parameters)
                .enqueue(new Callback<mainNews>() {
                    @Override
                    public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                        if (response.body() != null) {
                            modelClassArrayList.addAll(response.body().getArticles());
                            adapter.notifyDataSetChanged();
                        } else {

                            isLastPage = true;

                        }

                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<mainNews> call, Throwable t) {

                    }
                });
    }

    public void filterList(String newText) {
        ArrayList<ApiModel> filterData = new ArrayList<>();
        for (ApiModel apiModel : modelClassArrayList) {
            if (apiModel.getTitle().toString().toLowerCase().contains(newText.toString().toLowerCase())) {
                filterData.add(apiModel);

            }
            if (filterData.isEmpty()) {
//                Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                filterData.addAll(backup);
            } else {
                adapter.setfilterData(filterData);
            }
        }
    }

}