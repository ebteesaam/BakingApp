package com.example.myapplication.DB;

import android.os.AsyncTask;
import android.widget.Adapter;

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

public class FetchDataWidget extends AsyncTask<Void, Void, List<BakingRecipe>> {
    String data="";
    List<BakingRecipe> BRList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    public Adapter adapter;
    @Override
    protected List<BakingRecipe> doInBackground(Void... voids) {
        URL url = null;
        BRList= new ArrayList<>();
        ingredientsList=new ArrayList<>();
        stepsList=new ArrayList<>();
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
            //for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(0);
                String name = c.optString("name");
                String id = c.optString("id");
                String servings = c.optString("servings");

                Random r = new Random();
                int randomNumber = r.nextInt(100);
                JSONArray baking = c.getJSONArray("ingredients");
                for (int j=0; j<baking.length();j++ ){
                    JSONObject b = baking.getJSONObject(j);
                    double quantity=b.optDouble("quantity");
                    String measure=b.optString("measure");
                    String ingredient=b.optString("ingredient");
                    ingredientsList.add(new Ingredient(randomNumber,quantity,measure,id,ingredient));

                }

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


            //}
            //adapter = new AdapterIngredientWidget(this,0,ingredientsList);

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
       // WidgetProvider.listView.setAdapter((ListAdapter) adapter);
    }
}
