package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    RecyclerNewsAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<ApiModel> modelClassArrayList;
    private static String API_KEY = "df4461389a174f80aea73def485b5f1c";


    public HealthFragment() {
        // Required empty public constructor
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_health, container, false);

         recyclerView = view.findViewById(R.id.recyclerHealth);

         modelClassArrayList = new ArrayList<>();
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         adapter = new RecyclerNewsAdapter(getContext(), modelClassArrayList);
         recyclerView.setAdapter(adapter);

         getApi();

        return view;
    }
    private void getApi() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("country", "in");
        parameters.put("pageSize", "100");
        parameters.put("category", "sports");
        parameters.put("apiKey", API_KEY);

        NewsInstance.getInstance()
                .getApi()
                .getApiModel(parameters)
                .enqueue(new Callback<mainNews>() {
                    @Override
                    public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                        modelClassArrayList.addAll(response.body().getArticles());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<mainNews> call, Throwable t) {

                    }
                });
    }

}