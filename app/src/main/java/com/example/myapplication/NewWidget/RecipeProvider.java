package com.example.myapplication.NewWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.Random;

public class RecipeProvider extends AppWidgetProvider {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        /*
            AppWidgetManager
                Updates AppWidget state; gets information about installed AppWidget
                providers and other AppWidget related state.

            ComponentName
                Identifier for a specific application component (Activity, Service, BroadcastReceiver,
                or ContentProvider) that is available. Two pieces of information, encapsulated here,
                are required to identify a component: the package (a String) it exists in, and the
                class (a String) name inside of that package.

            RemoteViews
                A class that describes a view hierarchy that can be displayed in another process.
                The hierarchy is inflated from a layout resource file, and this class provides some
                basic operations for modifying the content of the inflated hierarchy.
        */
        AppWidgetManager appWidgetManager1= appWidgetManager;
        ComponentName watchWidget = new ComponentName(context, RecipeProvider.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent intent = new Intent(context, MainActivity.class);
        /*
            PendingIntent
                A description of an Intent and target action to perform with it. Instances of this
                class are created with getActivity(Context, int, Intent, int),
                getActivities(Context, int, Intent[], int), getBroadcast(Context, int, Intent, int),
                and getService(Context, int, Intent, int); the returned object can be handed to other
                applications so that they can perform the action you described on your behalf at a later time.
        */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_list, pendingIntent);
        views.setTextViewText(R.id.appwidget_text,"mm");
//        remoteViews.setOnClickPendingIntent(
//                R.id.tv_temperature,
//                getPendingSelfIntent(context, TEMPERATURE_CLICKED)
//        );
//
//        remoteViews.setOnClickPendingIntent(
//                R.id.tv_humidity,
//                getPendingSelfIntent(context, HUMIDITY_CLICKED)
//        );
        appWidgetManager1.updateAppWidget(watchWidget, remoteViews);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Allow the network operation on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        ComponentName watchWidget = new ComponentName(context, RecipeProvider.class);

       // Toast.makeText(context, "Requested", Toast.LENGTH_SHORT).show();

        // Check the internet connection availability
       // if(isInternetConnected()){
         //   Toast.makeText(context, "Fetching Data", Toast.LENGTH_SHORT).show();
            // Update the widget weather data
            // Execute the AsyncTask
            new ProcessJSONData(appWidgetManager,watchWidget,remoteViews).execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
          // remoteViews.setString(R.id.appwidget_text, "setBackgroundResource");

        //}else {
            //Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
           // remoteViews.setInt(R.id.tv_temperature, "setBackgroundResource", R.drawable.bg_red);
       // }

        // If the temperature text clicked
//        if (TEMPERATURE_CLICKED.equals(intent.getAction())) {
//            // Do something
//        }
//
//        // If the humidity text clicked
//        if(HUMIDITY_CLICKED.equals(intent.getAction())){
//            // Do something
//        }

        // Apply the changes
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);

    }

    // AsyncTask to fetch, process and display weather data
    private class ProcessJSONData extends AsyncTask<String, Void, String> {
        private AppWidgetManager appWidgetManager;
        private ComponentName watchWidget;
        private RemoteViews remoteViews;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        public ProcessJSONData(AppWidgetManager appWidgetManager, ComponentName watchWidget, RemoteViews remoteViews){
            // Do something
            this.appWidgetManager = appWidgetManager;
            this.watchWidget = watchWidget;
            this.remoteViews = remoteViews;
        }

        @Override
        protected String doInBackground(String... strings){
            String stream;
            String urlString = strings[0];

            // Get jason data from web
            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream){
            super.onPostExecute(stream);

            if(stream !=null){
                try{
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
                    //    ingredientsList.add(new Ingredient(randomNumber,quantity,measure,id,ingredient));

                    }

                    Log.e("Nmae",name);
                    remoteViews.setTextViewText(R.id.appwidget_text,name);
                    // Display weather data on widget
//                    remoteViews.setTextViewText(R.id.tv_temperature, temperature);
//                    remoteViews.setTextViewText(R.id.tv_humidity, "H: " + humidity + " %");

                    // Apply the changes
                    appWidgetManager.updateAppWidget(watchWidget, remoteViews);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        } // onPostExecute() end
    } // ProcessJSON class end

    // Method to get celsius value from kelvin
    public Double getCelsiusFromKelvin(String kelvinString){
        Double kelvin = Double.parseDouble(kelvinString);
        Double numberToMinus = 273.15;
        Double celsius = kelvin - numberToMinus;
        // Rounding up the double value
        // Each zero (0) return 1 more precision
        // Precision means number of digits after dot
        celsius = (double)Math.round(celsius * 10) / 10;
        return celsius;
    }

    // Custom method to check internet connection
    public Boolean isInternetConnected(){
        boolean status = false;
        try{
            InetAddress address = InetAddress.getByName("google.com");

            if(address!=null)
            {
                status = true;
            }
        }catch (Exception e) // Catch the exception
        {
            e.printStackTrace();
        }
        return status;
    }
}



