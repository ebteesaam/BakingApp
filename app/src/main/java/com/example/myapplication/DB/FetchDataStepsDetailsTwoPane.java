package com.example.myapplication.DB;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myapplication.Fragment.StepsFragmentTowPane;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.Model.Steps;
import com.google.android.exoplayer2.SimpleExoPlayer;

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


public class FetchDataStepsDetailsTwoPane extends AsyncTask<Void, Void, List<BakingRecipe>>  {


    public FetchDataStepsDetailsTwoPane(int id, int idRecipe) {
        this.id = id;
        this.idRecipe= idRecipe;
    }
    public static SimpleExoPlayer mExoPlayer;
   // public static SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;
    int id;
    int idRecipe;
    String idstep;
    private NotificationManager mNotificationManager;
    String shortDescription;
    String description;
    public static String videoURL;
    String data="";
    List<BakingRecipe> BRList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    public RecyclerView.Adapter adapter;
    public RecyclerView.Adapter adapterSteps;
    public static String thumbnailURL;
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
//            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(idRecipe-1);
                String name = c.optString("name");
               // String id = c.optString("id");
                String servings = c.optString("servings");
            Log.e("Recipe",name+id+servings);


                JSONArray steps = c.getJSONArray("steps");
              //  for (int j=0; j<steps.length();j++ ){
                    JSONObject b = steps.getJSONObject(id);
                    shortDescription=b.optString("shortDescription");
                    description=b.optString("description");
                    videoURL=b.optString("videoURL");
                    thumbnailURL=b.optString("thumbnailURL");
                    idstep=b.optString("id");
                    Log.e("thum",thumbnailURL+idstep);
                  //  stepsList.add(new Steps(shortDescription,description,videoURL,thumbnailURL,idstep,id));
Log.e("Fetch",description);
               // }

              //  BRList.add(new BakingRecipe(name,id,servings));


            //}
//            adapter = new AdapterIngredient(ingredientsList,stepsList);
//            adapterSteps=new AdapterSteps(stepsList)
;
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
       StepsFragmentTowPane.describtion.setText(description);




    }

}
