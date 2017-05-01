package com.example.sashok.task_;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sashok.task_.Answer.Answer;
import com.example.sashok.task_.Answer.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sashok on 30.4.17.
 */

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    CategoriesAdapter adapter;
    ViewStub viewStub;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewStub = (ViewStub) findViewById(R.id.viewStub);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        getAllCategories();}

    public void getAllCategories() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Answer> answerCall = MyApplication.getServiceApi().GetAllCategories();
        answerCall.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                if (response.body().getData() != null) {
                    onResponseSuccess(response.body().getData().getCategories());
                } else {
                    onResponseError(response.body().getError().getErrorMessage());
                }

            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                onResponseError(t.getMessage());
            }
        });
    }

    public void onResponseSuccess(List<Category> categories) {
        progressBar.setVisibility(View.GONE);
        viewStub.inflate();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout_manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout_manager);
        adapter = new CategoriesAdapter(this, categories);
        recyclerView.setAdapter(adapter);

    }

    public void onResponseError(String error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
    }
}
