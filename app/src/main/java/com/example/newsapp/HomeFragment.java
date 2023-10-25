package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;


import com.example.newsapp.ApiService.NewsInstance;
import com.example.newsapp.modles.ApiModel;
import com.example.newsapp.modles.mainNews;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    Button button;
   SearchView searchView;
    RecyclerNewsAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<ApiModel> modelClassArrayList;
    ArrayList<ApiModel> backup;
    private static String API_KEY = "df4461389a174f80aea73def485b5f1c";
    private int page = 1;

    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;
    private TextView textView;

    public HomeFragment() {
        // Required empty public constructor
    }

    private boolean isLoading = false;
    private boolean isLastPage = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        button = view.findViewById(R.id.button);
        searchView = view.findViewById(R.id.search_menu);
        recyclerView = view.findViewById(R.id.recyclerHome);
        layoutManager = new LinearLayoutManager(getContext());
        modelClassArrayList = new ArrayList<>();
        backup = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerNewsAdapter(getContext(), modelClassArrayList);
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.homeProgressBar);
        textView = view.findViewById(R.id.textView);

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

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               textView.setVisibility(View.GONE);
               searchView.setVisibility(View.VISIBLE);
               button.setVisibility(View.GONE);
               getApi();
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

    public void filterList(String newText){
            ArrayList<ApiModel>filterData = new ArrayList<>();
            for (ApiModel apiModel : modelClassArrayList){
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

    private void getApi() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("country", "in");
        parameters.put("pageSize", "5");
        parameters.put("apiKey", API_KEY);
        parameters.put("page", String.valueOf(page));


        NewsInstance.getInstance()
                .getApi()
                .getApiModel(parameters)
                .enqueue(new Callback<mainNews>() {
                    @Override
                    public void onResponse(@NonNull Call<mainNews> call, @NonNull Response<mainNews> response) {
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


                        Log.d("Apna Sapna", "Ho gya ");
                        if (page == 1) {
                            textView.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);


                        }

                    }
                });


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_KEY)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        GetDataService api = retrofit.create(GetDataService.class);
//
//        Call<mainNews> call = api.getApiModel(parameters);
//        call.enqueue(new Callback<mainNews>() {
//            @Override
//            public void onResponse(Call<mainNews> call, Response<mainNews> response) {
//                mainNews data = response.body();
//                modelClassArrayList.addAll(response.body().getArticles());
//                        adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onFailure(Call<mainNews> call, Throwable t) {
//
//            }
//        });


    }
}