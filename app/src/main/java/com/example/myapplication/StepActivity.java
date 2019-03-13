package com.example.myapplication;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Fragment.StepsFragment;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.Model.Steps;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.ButterKnife;

//import android.support.v7.app.NotificationCompat;

public class StepActivity extends AppCompatActivity {
    private static final String RECIPE_NUMBER = "recipe_num";
    String data = "";
    List<BakingRecipe> BRList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    private boolean isFullScreen = false;

    private long exo_current_position = 0;

    private static final String STEPS_LIST = "sList";
    private static final String POSITION = "position";
    private static final String IS_FULLSCREEN = "is_fullscreen";
    private static final String IS_NULL = "is_null";
    private static final String EXO_CURRENT_POSITION = "current_position";
    TextView describtion;
    private NotificationManager mNotificationManager;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    public static TextView shortDes;



        public static ImageView backB;


    public static ImageView forwardB;
    public static String video;
    int idInt;
    int idRecipeInt;
    public static String id = null;
    public static int size ;

    public static String idRecipe;
    public static String des;
    public static String shortdes;
    int idStep;
    FragmentManager fragmentManager;
    private static final String STEP_NUMBER = "step_num";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_fragment);
        ButterKnife.bind(this);
        // checkFullScreen();
    shortDes=findViewById(R.id.directions_toolbarTextV);
    backB=findViewById(R.id.directions_toolbar_back_button);
        forwardB=findViewById(R.id.directions_toolbar_forward_button);


        if(savedInstanceState == null) {
            idRecipe = getIntent().getExtras().getString("idRecipe");
            id = getIntent().getExtras().getString("id");
            idStep = Integer.valueOf(id);
            if (getIntent().hasExtra("Describtion")) {

                shortdes = getIntent().getExtras().getString("shortDescribtion");
//                Log.e("ddd", video);

                des = getIntent().getExtras().getString("Describtion");
                video = getIntent().getExtras().getString("video");

                size = getIntent().getExtras().getInt("size");


            }

        } else {
            idStep = savedInstanceState.getInt(STEP_NUMBER);
            idRecipe=savedInstanceState.getString(RECIPE_NUMBER);
            Log.e("Back","null");

        }
    shortDes.setText(shortdes);
    fragmentManager = getSupportFragmentManager();
    StepsFragment fragment = new StepsFragment(idStep, idRecipe);
    fragmentManager.beginTransaction()
            .replace(R.id.fragment_recipe, fragment)
            .commit();



    if (idStep == 0) {
        backB.setVisibility(View.INVISIBLE);
    }else {
        backB
                .setVisibility(View.VISIBLE);

    }

    backB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Ffffff","kkkk");
             idStep=idStep-1;
            if (idStep == 0) {
                backB.setVisibility(View.INVISIBLE);
            }else {
                backB
                        .setVisibility(View.VISIBLE);

            }
            StepsFragment fragment = new StepsFragment(idStep,idRecipe);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_recipe, fragment)
                    .commit();

        }
    });
        Log.e("Size", String.valueOf(size));

        if (idStep == size) {
            forwardB.setVisibility(View.INVISIBLE);
        }else {
            forwardB.setVisibility(View.VISIBLE);

        }
        forwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("S", String.valueOf(idStep));
                if (idStep == size) {
                    forwardB.setVisibility(View.INVISIBLE);
                }else {
                    forwardB.setVisibility(View.VISIBLE);
                    idStep=idStep+1;
                    StepsFragment fragment = new StepsFragment(idStep,idRecipe);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_recipe, fragment)
                            .commit();

                }

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_NUMBER,idStep);
        outState.putString(RECIPE_NUMBER, idRecipe);

    }
        @Override
        protected void onRestart() {
            super.onRestart();
        }
    public void setData(int id, int idRecipe){

        URL url = null;

        try {
            url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            Log.e("FS","button");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder builder = new StringBuilder();

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                builder.append(line).append("\n");
                data = data + line;
            }

            JSONArray json = new JSONArray(builder.toString());
            JSONObject c = json.getJSONObject(idRecipe-1);
            String nameJ = c.optString("name");
            String idJ = c.optString("id");
            String servingsJ = c.optString("servings");

            Log.e("FS",nameJ);
            JSONArray steps = c.getJSONArray("steps");

            JSONObject b = steps.getJSONObject(id);
            String shortDescriptionJ=b.optString("shortDescription");

            Log.e("FS",shortDescriptionJ);

            String descriptionJ=b.optString("description");
            String videoURLJ=b.optString("videoURL");
            String thumbnailURLJ=b.optString("thumbnailURL");
            String idstepJ=b.optString("id");
            final  boolean videoboo=b.optString("videoURL").isEmpty();

            stepsList.add(new Steps(shortDescriptionJ,descriptionJ,videoURLJ,thumbnailURLJ,idstepJ,idJ));

            BRList.add(new BakingRecipe(nameJ,idJ,servingsJ));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
