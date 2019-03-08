package com.example.myapplication.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.myapplication.Adapter.AdapterIngredientWidget;
import com.example.myapplication.Adapter.WidjetCurserAdapter;
import com.example.myapplication.DB.DBI.AppDatabaseI;
import com.example.myapplication.DetailsActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.NewWidget.HTTPDataHandler;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class WidgetProvider extends AppWidgetProvider {

    public Adapter adapter;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.widget_list)
    public ListView listView;

    @BindView(R.id.widget_emptyView)
    TextView empty;
    private RecyclerView.LayoutManager mLayoutManager;
    AppDatabaseI mDb;
    WidjetCurserAdapter widjetCurserAdapter;

    Context context1;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        context1 = context;
//
//        listView.setHasFixedSize(true);
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(context);
//        listView.setLayoutManager(mLayoutManager);
//            mDb.IngredientDao().getAll();
//                    adapterRecipe=new AdapterIngredientWidget(context1,R.layout.list_ingredeint, (List<Ingredient>) mDb.IngredientDao().getAll());
//                    listView.setAdapter((ListAdapter) adapterRecipe);
        //final LiveData<List<Movie>> movies=mDb.movieDao().getAll();
//listView.setEmptyView(empty);
//        widjetCurserAdapter=new WidjetCurserAdapter(context,null);
//        listView.setAdapter(widjetCurserAdapter);\
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        ComponentName watchWidget = new ComponentName(context, WidgetProvider.class);

        ProcessJSONData processJSONData= (ProcessJSONData) new ProcessJSONData(appWidgetManager,watchWidget,views).execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

        String recipeName = processJSONData.name;
//
       // if (recipeName.equals("")) {
           // views.setTextViewText(R.id.appwidget_text, "Baking App");
       // } else {
        //    views.setTextViewText(R.id.appwidget_text, recipeName);

       // }
//        FetchDataWidget fetchDataWidget=new FetchDataWidget();
//        fetchDataWidget.execute();

//      adapter = new AdapterIngredientWidget(context1,0,MainActivity.ingredientList);
//Log.e("KK",MainActivity.ingredientList.toString());

//            mDb = AppDatabaseI.getDatabase(context);
//            MainViewModelI viewModel;
//            viewModel = ViewModelProviders.of((FragmentActivity) context.getApplicationContext()).get(MainViewModelI.class);
//            viewModel.getTasks().observe((LifecycleOwner) this, new Observer<List<Ingredient>>() {
//                @Override
//                public void onChanged(@Nullable List<Ingredient> movies2) {
//
//                    adapterRecipe=new AdapterIngredientWidget(context1,R.layout.list_ingredeint,movies2);
//                    listView.setAdapter((ListAdapter) adapterRecipe);
//                    Log.e("retrieveData", "Done");
//
//                }
//            });


        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        //  RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.linearLayout, pendingIntent);
//        setRemoteAdapter(context, views);
        views.setEmptyView(R.id.widget_list, R.id.widget_emptyView);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, WidgetRemoteViewsService.class));

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        if (DetailsActivity.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appwidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass())
            );
            appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.widget_list);
            onUpdate(context, appWidgetManager, appwidgetIds);
        }
    }

    public static void setWidgetText(Context context, String name) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        views.setTextViewText(R.id.appwidget_text, name);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetRemoteViewsService.class));
    }

    private class ProcessJSONData extends AsyncTask<String, Void, String> {
        AdapterIngredientWidget adapterRecipe;

        private AppWidgetManager appWidgetManager;
        private ComponentName watchWidget;
        private RemoteViews remoteViews;
        List<Ingredient> ingredientsList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public ProcessJSONData(AppWidgetManager appWidgetManager, ComponentName watchWidget, RemoteViews remoteViews) {
            // Do something
            this.appWidgetManager = appWidgetManager;
            this.watchWidget = watchWidget;
            this.remoteViews = remoteViews;
        }
        String name;
        @Override
        protected String doInBackground(String... strings) {
            String stream;
            String urlString = strings[0];

            // Get jason data from web
            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream) {
            super.onPostExecute(stream);

            if (stream != null) {
                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuilder builder = new StringBuilder();
//
//                    String line = "";
//                    while (line != null) {
//                        line = bufferedReader.readLine();
//                        builder.append(line).append("\n");
//                      //  data = data + line;
//                    }

                    JSONArray json = new JSONArray(stream);
                    //for (int i = 0; i < json.length(); i++) {
//                    Log.e("Nmae", DetailsActivity.idRecipeWidget);

                    JSONObject c = json.getJSONObject(0
                    );
                    name = c.optString("name");
                    String id = c.optString("id");
                    String servings = c.optString("servings");

                    Random r = new Random();
                    int randomNumber = r.nextInt(100);
                    JSONArray baking = c.getJSONArray("ingredients");
                    for (int j = 0; j < baking.length(); j++) {
                        JSONObject b = baking.getJSONObject(j);
                        double quantity = b.optDouble("quantity");
                        String measure = b.optString("measure");
                        String ingredient = b.optString("ingredient");
                   //   ingredientsList.add(new Ingredient(randomNumber,quantity,measure,id,ingredient));

                    }
//
                    Log.e("Nmae", name);
                    remoteViews.setTextViewText(R.id.appwidget_text, name);
//                    adapterRecipe=new AdapterIngredientWidget(context1,R.layout.recipe_widget,ingredientsList);
//                    listView.setAdapter(adapterRecipe);
                    //remoteViews.setRemoteAdapter(R.id.widget_list,adapterRecipe);
                    // Display weather data on widget
//                    remoteViews.setTextViewText(R.id.tv_temperature, temperature);
//                    remoteViews.setTextViewText(R.id.tv_humidity, "H: " + humidity + " %");

                    // Apply the changes
                    appWidgetManager.updateAppWidget(watchWidget, remoteViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } // onPostExecute() end
    } // ProcessJSON class end

    // Method to get celsius value from kelvin
    public Double getCelsiusFromKelvin(String kelvinString) {
        Double kelvin = Double.parseDouble(kelvinString);
        Double numberToMinus = 273.15;
        Double celsius = kelvin - numberToMinus;
        // Rounding up the double value
        // Each zero (0) return 1 more precision
        // Precision means number of digits after dot
        celsius = (double) Math.round(celsius * 10) / 10;
        return celsius;
    }

    // Custom method to check internet connection
    public Boolean isInternetConnected() {
        boolean status = false;
        try {
            InetAddress address = InetAddress.getByName("google.com");

            if (address != null) {
                status = true;
            }
        } catch (Exception e) // Catch the exception
        {
            e.printStackTrace();
        }
        return status;
    }
//    public class FetchDataWidget extends AsyncTask<Void, Void, List<BakingRecipe>> {
//        String data="";
//        List<BakingRecipe> BRList;
//        List<Ingredient> ingredientsList;
//        List<Steps> stepsList;
//
//        @Override
//        protected List<BakingRecipe> doInBackground(Void... voids) {
//            URL url = null;
//            BRList= new ArrayList<>();
//            ingredientsList=new ArrayList<>();
//            stepsList=new ArrayList<>();
//            try {
//                url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
//
//                Log.e("kkk",url.toString());
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuilder builder = new StringBuilder();
//
//                String line = "";
//                while (line != null) {
//                    line = bufferedReader.readLine();
//                    builder.append(line).append("\n");
//                    data = data + line;
//                }
//
//                JSONArray json = new JSONArray(builder.toString());
//              //  for (int i = 0; i < json.length(); i++) {
//                    JSONObject c = json.getJSONObject(0);
//                    String name = c.optString("name");
//                    String id = c.optString("id");
//                    String servings = c.optString("servings");
//
//                    Random r = new Random();
//                    int randomNumber = r.nextInt(100);
//                    JSONArray baking = c.getJSONArray("ingredients");
//                    for (int j=0; j<baking.length();j++ ){
//                        JSONObject b = baking.getJSONObject(j);
//                        double quantity=b.optDouble("quantity");
//                        String measure=b.optString("measure");
//                        String ingredient=b.optString("ingredient");
//                        ingredientsList.add(new Ingredient(randomNumber,quantity,measure,id,ingredient));
//
//                    }
//
//
//Log.e("List",ingredientsList.get(0).getIngredient());
//             //   adapterRecipe = new AdapterIngredient(ingredientsList,stepsList);
////                listView.setAdapter((ListAdapter) adapterRecipe);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return BRList;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(List<BakingRecipe> bakingRecipes) {
//            super.onPostExecute(bakingRecipes);
//        }
//}}
}