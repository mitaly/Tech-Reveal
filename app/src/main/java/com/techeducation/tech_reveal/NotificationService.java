package com.techeducation.tech_reveal;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.techeducation.tech_reveal.action.FOO";
    private static final String ACTION_BAZ = "com.techeducation.tech_reveal.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.techeducation.tech_reveal.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.techeducation.tech_reveal.extra.PARAM2";


    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;
    ArrayList<Uri> uriList;

    public NotificationService() {
        super("NotificationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }

        uriList=new ArrayList<>();
        uriList.add(DictionaryUtil.uri_ca);
        uriList.add(DictionaryUtil.uri_cg);
        uriList.add(DictionaryUtil.uri_cn);
        uriList.add(DictionaryUtil.uri_cs);
        uriList.add(DictionaryUtil.uri_dbms);
        uriList.add(DictionaryUtil.uri_dcld);
        uriList.add(DictionaryUtil.uri_dsa);
        uriList.add(DictionaryUtil.uri_os);
        uriList.add(DictionaryUtil.uri_pl);
        uriList.add(DictionaryUtil.uri_map);
        uriList.add(DictionaryUtil.uri_wt);

        ArrayList<String> list=new ArrayList<>();
        Random random=new Random();
        int randomNumber=random.nextInt(uriList.size());
        Uri uriOfTable=uriList.get(randomNumber);
        ContentResolver resolver=getContentResolver();
        String[] projection={DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS};
        Cursor cursor=resolver.query(uriOfTable,projection,null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_WORDS)));
        }
        randomNumber=random.nextInt(list.size());
        String word=list.get(randomNumber);
        Intent i=new Intent(this,WordMeaningActivity.class);
        i.putExtra("keyWord",word);
        i.putExtra("keyUri",String.valueOf(uriOfTable));

        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo1)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.logo))
                .setTicker("Word of the day")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Word of the day")
                .setContentText(word).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
