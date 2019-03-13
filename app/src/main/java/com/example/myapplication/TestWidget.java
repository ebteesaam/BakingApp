package com.example.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.AdapterIngredientWidget;
import com.example.myapplication.Adapter.AdapterIngredientWidgetCurser;
import com.example.myapplication.DB.DBI.AppDatabaseI;
import com.example.myapplication.DB.DBI.MainViewModelI;
import com.example.myapplication.Model.Ingredient;

import java.util.List;

public class TestWidget  extends AppCompatActivity {

ListView listView;
    AppDatabaseI mDatabase;
    TextView widget_emptyView;
AdapterIngredientWidgetCurser adapterIngredientWidgetCurser;
    AdapterIngredientWidget adapterRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_widget);
widget_emptyView=findViewById(R.id.widget_emptyView);

listView=findViewById(R.id.widget_list);
        mDatabase = AppDatabaseI.getDatabase(this);

        MainViewModelI viewModel;
        viewModel = ViewModelProviders.of(this).get(MainViewModelI.class);
        viewModel.getTasks().observe( this , new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> movies2) {

                LiveData<List<Ingredient>> movies=mDatabase.IngredientDao().getAll();
//movies2.get(0).getIdRecipe();
//Log.e("Widget",movies2.get(0).getIdRecipe());
                adapterRecipe=new AdapterIngredientWidget(TestWidget.this,R.layout.list_ingredeint,movies2);
                    listView.setAdapter((ListAdapter) adapterRecipe);



    }
        });

adapterIngredientWidgetCurser=new AdapterIngredientWidgetCurser(this,null);
//        adapterRecipe=new AdapterIngredientWidgetCurser(this,null);
        listView.setAdapter(adapterIngredientWidgetCurser);
        listView.setEmptyView(widget_emptyView);

//        Uri uri=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
//        Cursor cursor = getContentResolver().query(
//                uri,
//                null,
//                null,
//                null,
//                ContractIngredient.Recipe._ID
//        );
//        if (cursor != null && cursor.getCount() > 0) {
//
//        }
}}
