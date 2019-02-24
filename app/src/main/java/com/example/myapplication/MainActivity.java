package com.example.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterRecipe;
import com.example.myapplication.DB.DBR.AppDatabase;
import com.example.myapplication.DB.DBR.MainViewModel;
import com.example.myapplication.DB.FetchData;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.Model.Ingredient;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String data="";
    public static RecyclerView list;
    private RecyclerView.LayoutManager mLayoutManager;
    AppDatabase mDatabase;
    AdapterRecipe adapterRecipe;
    public static List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = AppDatabase.getDatabase(this);
Button test=findViewById(R.id.test);
test.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,TestWidget.class);
        startActivity(intent);
    }
});
        list = findViewById(R.id.list);
        list.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            FetchData fetchData=new FetchData(this);
            fetchData.execute();
            Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_LONG).show();

        } else {



            MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


            //final LiveData<List<Movie>> movies=mDb.movieDao().getAll();
            viewModel.getTasks().observe(this, new Observer<List<BakingRecipe>>() {
                @Override
                public void onChanged(@Nullable List<BakingRecipe> movies2) {
                    adapterRecipe=new AdapterRecipe(movies2);
                    list.setAdapter(adapterRecipe);
                    Log.e("listBR",movies2.get(0).getName());

                    Log.e("retrieveData", "Done");

                }
            });

            Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();

        }
//
//        MainViewModelI viewModel;
//        viewModel = ViewModelProviders.of(this).get(MainViewModelI.class);
//        viewModel.getTasks().observe( this , new Observer<List<Ingredient>>() {
//            @Override
//            public void onChanged(@Nullable List<Ingredient> movies2) {
//
//                ingredientList=movies2;
////                    adapterRecipe=new AdapterIngredientWidget(context1,R.layout.list_ingredeint,movies2);
////                    listView.setAdapter((ListAdapter) adapterRecipe);
//                Log.e("retrieveData", "Done");
//                Log.e("list",movies2.toString());
//
//            }
//        });
//widget();

    }



}
