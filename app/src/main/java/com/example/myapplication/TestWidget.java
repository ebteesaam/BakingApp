package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myapplication.Adapter.AdapterIngredientWidgetCurser;
import com.example.myapplication.DB.DBI.AppDatabaseI;

public class TestWidget  extends AppCompatActivity {

ListView listView;
    AppDatabaseI mDatabase;

    AdapterIngredientWidgetCurser adapterRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_widget);


listView=findViewById(R.id.widget_list);
//        mDatabase = AppDatabaseI.getDatabase(this);
//
//        MainViewModelI viewModel;
//        viewModel = ViewModelProviders.of(this).get(MainViewModelI.class);
//        viewModel.getTasks().observe( this , new Observer<List<Ingredient>>() {
//            @Override
//            public void onChanged(@Nullable List<Ingredient> movies2) {
//
//                LiveData<List<Ingredient>> movies=mDatabase.IngredientDao().getAll();
//movies2.get(0).getIdRecipe();
//Log.e("Widget",movies2.get(0).getIdRecipe());
//                adapterRecipe=new AdapterIngredientWidget(TestWidget.this,R.layout.list_ingredeint,movies2);
//                    listView.setAdapter((ListAdapter) adapterRecipe);



//    }
//        });


        adapterRecipe=new AdapterIngredientWidgetCurser(this,null);
        listView.setAdapter(adapterRecipe);

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
