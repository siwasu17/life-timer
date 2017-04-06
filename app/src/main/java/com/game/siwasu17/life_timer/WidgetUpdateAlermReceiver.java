package com.game.siwasu17.life_timer;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetUpdateAlermReceiver extends BroadcastReceiver {
    public WidgetUpdateAlermReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.err.println("Recieve");

        LifeTimeManager lifeTimeManager = LifeTimeManager.getInstance(context);
        lifeTimeManager.updateLifeTime();
        long lifeTimeSec = lifeTimeManager.getLifeTimeSec();

        CharSequence widgetText = String.valueOf(lifeTimeSec);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.life_timer_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        ComponentName widget = new ComponentName(context, LifeTimerAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(widget);

        manager.updateAppWidget(ids, views);
    }
}
