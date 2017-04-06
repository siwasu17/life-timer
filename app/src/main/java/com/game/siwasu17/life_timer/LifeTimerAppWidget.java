package com.game.siwasu17.life_timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class LifeTimerAppWidget extends AppWidgetProvider {

    private final int WIDGET_UPDATE_MSEC = 1000;

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        System.err.println("Update");
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(final Context context) {
        // Enter relevant functionality for when the first widget is created
        System.err.println("Enabled");

        Intent launchIntent = new Intent(context, WidgetUpdateAlermReceiver.class);
        PendingIntent mAlarmIntent = PendingIntent.getBroadcast(context, 0, launchIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long firstTime = SystemClock.elapsedRealtime();
        long interval = 30 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC, firstTime, interval, mAlarmIntent);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent launchIntent = new Intent(context, WidgetUpdateAlermReceiver.class);
        PendingIntent mAlarmIntent = PendingIntent.getBroadcast(context, 0, launchIntent, 0);
        alarmManager.cancel(mAlarmIntent);

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        LifeTimeManager lifeTimeManager = LifeTimeManager.getInstance(context);
        lifeTimeManager.updateLifeTime();
        long lifeTimeSec = lifeTimeManager.getLifeTimeSec();

        CharSequence widgetText = String.valueOf(lifeTimeSec);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.life_timer_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

