package com.example.myapplication.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.myapplication.DB.DBI.ContractIngredient;
import com.example.myapplication.R;

import static com.example.myapplication.DB.DBI.ContractIngredient.BASE_CONTENT_URI;
import static com.example.myapplication.DB.DBI.ContractIngredient.PATH;

public class WidgetRemote extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteView(this.getApplicationContext());
    }
}

    class WidgetRemoteView implements RemoteViewsService.RemoteViewsFactory{
        Context context;
        Cursor cursor;
    public WidgetRemoteView() {
        super();
    }

    public WidgetRemoteView(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        Uri uri=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        if(cursor!=null)cursor.close();
        cursor = context.getContentResolver().query(
                uri,
                null,
                null,
                null,
               ContractIngredient.Recipe._ID
        );

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(cursor==null)
        return 0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (cursor==null||cursor.getCount()==0)return null;
        cursor.moveToPosition(position);
        int ingredientColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.INGREDIENT);
        int measureColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.MEASURE);
        int quantityColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.QUANTITY);
        String ingredient = cursor.getString(ingredientColumnIndex);
        String measure = cursor.getString(measureColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);

        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.list_ingredeint);

        remoteViews.setTextViewText(R.id.ingredient,ingredient);
        remoteViews.setTextViewText(R.id.measure,measure);
        String q = String.valueOf(quantity);
        remoteViews.setTextViewText(R.id.quantity,String.valueOf( quantity));

        Log.e("Remote",ingredient+measure+quantity);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
