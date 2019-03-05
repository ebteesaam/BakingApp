package com.example.myapplication.WidgetR;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Adapter;

import com.example.myapplication.Adapter.AdapterIngredientWidget;
import com.example.myapplication.Model.Ingredient;

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

public class RemoteFetchService extends Service {
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    JSONObject jsonobject;
    JSONArray jsonarray;
    private int count = 0;
   // AQuery aquery;
   List<Ingredient> ingredientsList;

    private String remoteJsonUrl ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static ArrayList<Ingredient> listItemList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * Retrieve appwidget id from intent it is needed to update widget later
     * initialize our AQuery class
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
       // aquery = new AQuery(getBaseContext());
        new DownloadJSON().execute();
        return super.onStartCommand(intent, flags, startId);
    }
    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, List<Ingredient>> {
        String data="";
        public Adapter adapter;

        @Override
        protected List<Ingredient>  doInBackground(Void... voids) {
            URL url = null;

            ingredientsList=new ArrayList<>();

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

//                Log.e("RemoteP",ingredientsList.get(0).getIngredient());


                //}
              adapter = new AdapterIngredientWidget(getApplicationContext(),0,ingredientsList);
populateWidget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ingredientsList;
        }
    }

//        private void storeListItem() {
//        DatabaseManager dbManager = DatabaseManager.INSTANCE;
//        dbManager.init(getBaseContext());
//        dbManager.storeListItems(appWidgetId, listItemList);
//
//        int length = listItemList.size();
//        for (int i = 0; i < length; i++) {
//            Ingredient listItem = listItemList.get(i);
//            final int index = i;
//            aquery.ajax(listItem.imageUrl, Bitmap.class,new AjaxCallback<Bitmap>() {
//                @Override
//                public void callback(String url, Bitmap bitmap, AjaxStatus status) {
//                    super.callback(url, bitmap, status);
//                    storeBitmap(index, bitmap);
//                };
//            });
//        }
//    }
    /**
     * Instead of using static ArrayList as we have used before,no we rely upon
     * data stored on database so saving the fetched json file content into
     * database and at same time downloading the image from web as well
     */

//    private void storeListItem() {
//        DatabaseManager dbManager = DatabaseManager.INSTANCE;
//        dbManager.init(getBaseContext());
//        dbManager.storeListItems(appWidgetId, listItemList);
//
//        int length = listItemList.size();
//        for (int i = 0; i < length; i++) {
//            Ingredient listItem = listItemList.get(i);
//            final int index = i;
//            aquery.ajax(listItem.imageUrl, Bitmap.class,new AjaxCallback<Bitmap>() {
//                @Override
//                public void callback(String url, Bitmap bitmap, AjaxStatus status) {
//                    super.callback(url, bitmap, status);
//                    storeBitmap(index, bitmap);
//                };
//            });
//        }
//    }
//    /**
//     * Saving the downloaded images into file and after all the download of
//     * images be complete begin to populate widget as done previously
//     */
//    private void storeBitmap(int index, Bitmap bitmap) {
//        FileManager.INSTANCE.storeBitmap(appWidgetId, bitmap,
//                listItemList.get(index).heading, getBaseContext());
//        count++;
//        Log.i("count",String.valueOf(count) + "::"+ Integer.toString(listItemList.size()));
//        if (count == listItemList.size()) {
//            count = 0;
//            populateWidget();
//        }
//
//    }

    /**
     * Method which sends broadcast to WidgetProvider so that widget is notified
     * to do necessary action and here action == WidgetProvider.DATA_FETCHED
     */
    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        //widgetUpdateIntent.setAction(WidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }
}