package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.DBI.AppDatabaseI;
import com.example.myapplication.DB.DBI.ContractIngredient;
import com.example.myapplication.DB.FetchDataDetails;
import com.example.myapplication.DB.FetchDataDetailsTwoPane;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.Model.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {
    public static RecyclerView list;
    private RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView listStep;
     ImageButton fav;
    public static TextView title;
    ImageButton back;
    private boolean mTwoPane;

    public static final String ACTION_DATA_UPDATED = "com.example.myapplication.ACTION_DATA_UPDATE";
    public static final String Id_Recipe_Shared_p="My Id Recipe";

    public static FragmentManager fragmentManager;
    AppDatabaseI appDatabaseI;
    private RecyclerView.LayoutManager mLayoutManagerStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fav=findViewById(R.id.favorite_button);
        back=findViewById(R.id.directions_toolbar_back_button);
        title=findViewById(R.id.toolbarTextV);
        list = findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);
        // use a linear layout manager

            mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);

        listStep = findViewById(R.id.recyclerView2);
        listStep.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerStep = new LinearLayoutManager(this);
        listStep.setLayoutManager(mLayoutManagerStep);
        final String intentExtra = getIntent().getStringExtra("id");

        if(findViewById(R.id.line1) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            FetchDataDetailsTwoPane fetchDataDetails=new FetchDataDetailsTwoPane(this,Integer.parseInt(intentExtra));
            fetchDataDetails.execute();
            mTwoPane = true;
            fragmentManager = getSupportFragmentManager();
//            StepsFragmentTowPane fragment = new StepsFragmentTowPane(0, "0");
//            fragmentManager.beginTransaction()
//                    .add(R.id.fragment_recipe, fragment)
//                    .commit();

        }else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
            FetchDataDetails fetchDataDetails=new FetchDataDetails(this,Integer.parseInt(intentExtra));
            fetchDataDetails.execute();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
//        if(Utility.recipeExist(this,recipeId)){
//            fav.setColorFilter(getApplication().getResources().getColor(R.color.red));
//        }
//        final ArrayList<Ingredient> iList = savedInstanceState
//                .getParcelableArrayList(getString(R.string.ingredientsList_key));
//        if( Utility.recipeExist(this,Integer.parseInt(intentExtra))){
//           // fav.setChecked(true);
//        } else {
//         //   fav.setChecked(false);
//        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fav.setColorFilter(getApplication().getResources().getColor(R.color.red));
                appDatabaseI=AppDatabaseI.getDatabase(DetailsActivity.this);


                appDatabaseI.IngredientDao().delete();

                FetchDataFav fetchDataFav=new FetchDataFav(DetailsActivity.this,Integer.parseInt(intentExtra));
                fetchDataFav.execute();
                SharedPreferences.Editor editor = getSharedPreferences(Id_Recipe_Shared_p,0).edit();
                editor.putInt("id", fetchDataFav.id);
               // editor.putInt("idName", 12);
                Log.e("idRShF", String.valueOf(fetchDataFav.id));

                editor.apply();
                Toast.makeText(getApplication(), "Added to your widget", Toast.LENGTH_SHORT).show();

            }
        });
        SharedPreferences prefs = getSharedPreferences(Id_Recipe_Shared_p, 0);
        int restoredText = prefs.getInt("id",0);
        Log.e("idRSh", String.valueOf(restoredText));

    }

        public class FetchDataFav extends AsyncTask<Void, Void, Void> {
            String data="";
            List<BakingRecipe> BRList;
            List<Ingredient> ingredientsList;
            List<Steps> stepsList;
            public RecyclerView.Adapter adapter;
            public RecyclerView.Adapter adapterSteps;
            String name;
            String idRecipeWidget;
            String idRecipeWidget2;

            int id;

            Context context;
            AppDatabaseI mDatabase;

            public FetchDataFav(Context context,int data) {
                this.context=context;
                    this.id = data;
            }
            public FetchDataFav(int data) {
                this.id = data;
            }
             AppDatabaseI appDatabaseI;
            @SuppressLint("WrongThread")
            @Override
        protected Void doInBackground(Void... voids) {
                mDatabase = AppDatabaseI.getDatabase(DetailsActivity.this);
            URL url = null;
            appDatabaseI=AppDatabaseI.getDatabase(context);

                ingredientsList = new ArrayList<>();
            try {
                url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder builder = new StringBuilder();

                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    builder.append(line).append("\n");
                    data = data + line;
                }

                JSONArray json = new JSONArray(builder.toString());
//            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(id - 1);
                name = c.optString("name");
                final String id = c.optString("id");
                String servings = c.optString("servings");
                Log.e("Recipe", name + id + servings);

                Random r = new Random();
                final int randomNumber = r.nextInt(100);
                JSONArray baking = c.getJSONArray("ingredients");
                for (int j = 0; j < baking.length(); j++) {
                    JSONObject b = baking.getJSONObject(j);
                    final double quantity = b.optDouble("quantity");
                    final String measure = b.optString("measure");
                    final String ingredient = b.optString("ingredient");
                    ingredientsList.add(new Ingredient(randomNumber, quantity, measure, id, ingredient));
                    final Ingredient ingredient1=new Ingredient(randomNumber, quantity, measure, id, ingredient);
                    Log.e("Ingredient", quantity + measure + ingredient);
//                    fav.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
//                    AppExecutor executor = new AppExecutor();
//                    executor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            appDatabaseI.IngredientDao().insert(ingredient1);
//                            //  Toast.makeText(context, "Added to your widget", Toast.LENGTH_SHORT).show();
//                            //     Log.e("dd",ingredient1.getIngredient());
//
//                        }
//                    });


//                    MainViewModelI viewModel;
//                    viewModel = ViewModelProviders.of(DetailsActivity.this).get(MainViewModelI.class);
//                    viewModel.getTasks().observe( DetailsActivity.this , new Observer<List<Ingredient>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Ingredient> movies2) {
//
//                            LiveData<List<Ingredient>> movies=mDatabase.IngredientDao().getAll();
//                         //   idRecipeWidget=movies2.get(0).getIdRecipe();
//                            Log.e("Widgetid",movies2.get(0).getIdRecipe());}});
//                    idRecipeWidget2=idRecipeWidget;
//                    Log.e("Widgetid",id);


                    ContentValues values=new ContentValues();
                    values.put(ContractIngredient.Recipe._ID_Recipe,id);
                    values.put(ContractIngredient.Recipe.INGREDIENT,ingredient);
                    values.put(ContractIngredient.Recipe.QUANTITY,String.valueOf(quantity));
                    values.put(ContractIngredient.Recipe.MEASURE,measure);
                    Uri newUri = getContentResolver().insert(ContractIngredient.Recipe.CONTENT_URI, values);




                }





                //}

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;}
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
