package com.example.myapplication.WidgetR;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.myapplication.R;

public class WidgetProvider extends AppWidgetProvider {

    /**
     * this method is called every 30 mins as specified on <span class="skimlinks-unlinked">widgetinfo.xml</span>
     * this method is also called on every phone reboot
     **/


    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager, int[] appWidgetIds) {

        /*int[] appWidgetIds holds ids of multiple instance
         * of your widget
         * meaning you are placing more than one widgets on
         * your homescreen*/
        final int N = appWidgetIds.length;
        for (int i = 0; i <N; ++i) {
            RemoteViews remoteViews = updateWidgetListView(context,
                     appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds,
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.recipe_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
               svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.widget_list, R.id.widget_emptyView);
        return remoteViews;
    }


}
