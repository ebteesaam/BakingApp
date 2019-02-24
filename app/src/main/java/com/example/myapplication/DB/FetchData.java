package com.example.myapplication.DB;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.Adapter.AdapterRecipe;
import com.example.myapplication.DB.DBI.AppDatabaseI;
import com.example.myapplication.DB.DBR.AppDatabase;
import com.example.myapplication.MainActivity;
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

public class FetchData extends AsyncTask<Void, Void, List<BakingRecipe>> {
    String data="";
    List<BakingRecipe> BRList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    AppDatabase database;
    AppDatabaseI databaseI;

    Context context;
    public RecyclerView.Adapter adapter;

    public FetchData(Context context) {
        this.context=context;
    }

    @Override
    protected List<BakingRecipe> doInBackground(Void... voids) {
        URL url = null;
        BRList= new ArrayList<>();
        ingredientsList=new ArrayList<>();
        stepsList=new ArrayList<>();
        AppExecutor executor1 = new AppExecutor();

        databaseI=AppDatabaseI.getDatabase(context);
        database = AppDatabase.getDatabase(context);
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
            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(i);
                String name = c.optString("name");
                final String id = c.optString("id");
                String servings = c.optString("servings");
                final BakingRecipe bakingRecipe=new BakingRecipe(name,id,servings);
                AppExecutor executor = new AppExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.RecipeDao().insert(bakingRecipe);

                    }
                });
                Random r = new Random();
                final int randomNumber = r.nextInt(100);
                JSONArray baking = c.getJSONArray("ingredients");
                for (int j=0; j<baking.length();j++ ){
                    JSONObject b = baking.getJSONObject(j);
                    final double quantity=b.optDouble("quantity");
                    final String measure=b.optString("measure");
                    final String ingredient=b.optString("ingredient");
                    ingredientsList.add(new Ingredient(randomNumber,quantity,measure,id,ingredient));
                    final Ingredient ingredient1=new Ingredient(randomNumber,quantity,measure,id,ingredient);



                }
//                executor1.execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        databaseI.IngredientDao().insert(ingredientsList);
//                        //  Toast.makeText(context, "Added to your widget", Toast.LENGTH_SHORT).show();
//                        Log.e("dd",ingredientsList.get(0).getIngredient());
//
//                    }
//                });
                JSONArray steps = c.getJSONArray("steps");
                for (int j=0; j<steps.length();j++ ){
                    JSONObject b = steps.getJSONObject(j);
                    String shortDescription=b.optString("shortDescription");
                    String description=b.optString("description");
                    String videoURL=b.optString("videoURL");
                    String thumbnailURL=b.optString("thumbnailURL");
                    String idstep=b.optString("id");
                    stepsList.add(new Steps(shortDescription,description,videoURL,thumbnailURL,idstep,id));

                }

                BRList.add(new BakingRecipe(name,id,servings));

            }
            adapter = new AdapterRecipe(BRList);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BRList;
    }



    @Override
    protected void onPostExecute(List<BakingRecipe> bakingRecipes) {
        super.onPostExecute(bakingRecipes);
        MainActivity.list.setAdapter(adapter);
    }
}
