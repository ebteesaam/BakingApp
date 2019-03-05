package com.example.myapplication.WidgetR;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ListProvider
    implements
 RemoteViewsService.RemoteViewsFactory {
//    private ArrayList listItemList = new ArrayList();
//    private Context context = null;
//    private int appWidgetId;
//
//    public ListProvider(Context context, Intent intent) {
//        this.context = context;
//        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                AppWidgetManager.INVALID_APPWIDGET_ID);
//
//        populateListItem();
//    }
//
//    private void populateListItem() {
//        for (int i = 0; i &lt; 10; i++) {
//            Ingredient listItem = new Ingredient();
//            listItem.heading = "Heading" + i;
//            listItem.content = i
//                    + " This is the content of the app widget <span class="skimlinks-unlinked">listview.Nice</span> content though";
//<span class="skimlinks-unlinked">listItemList.add(listItem</span>);
//        }
//
//    }
//
//    @Override
//    public int getCount() {
//        return <span class="skimlinks-unlinked">listItemList.size</span>();
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    /*
//     *Similar to getView of Adapter where instead of View
//     *we return RemoteViews
//     *
//     */
//    @Override
//    public RemoteViews getViewAt(int position) {
//        final RemoteViews remoteView = new RemoteViews(
//                context.getPackageName(), R.layout.list_row);
//        Ingredient listItem = (Ingredient) listItemList.get(position);
//        remoteView.setTextViewText(R.id.heading, listItem.heading);
//        remoteView.setTextViewText(R.id.content, listItem.content);
//
//        return remoteView;
//    }
//}
private ArrayList<Ingredient> listItemList = new ArrayList<Ingredient>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        for (int i = 0; i < 10; i++) {
            Ingredient listItem = new Ingredient();
//            listItem. = "Heading" + i;
//            listItem.content = i
//                    + " This is the content of the app widget listview.Nice content though";
            listItemList.add(listItem);
        }

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.recipe_widget);
        Ingredient listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.appwidget_text, listItem.getIdRecipe());
      //  remoteView.setTextViewText(R.id.content, listItem.content);

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }
}