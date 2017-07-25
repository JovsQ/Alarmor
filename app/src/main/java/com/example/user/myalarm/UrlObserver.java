package com.example.user.myalarm;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by USER on 7/25/2017.
 */

public class UrlObserver extends ContentObserver {

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public UrlObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d("content_observer", "uri: " + uri);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

    }
}
