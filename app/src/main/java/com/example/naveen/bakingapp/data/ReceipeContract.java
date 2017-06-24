package com.example.naveen.bakingapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Vamsi Smart on 12-09-2016.
 */
public class ReceipeContract {


    public static final String CONTENT_AUTHORITY = "com.example.naveen.bakingapp.data.ReceipeProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class ReceipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Receipe";

        // Table columns
        public static final String COLUMN_RECEIPE_ID = "receipe_id";
        public static final String COLUMN_RECEIPE_NAME = "receipe_name";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildReceipeUri(long receipe_id) {
            return ContentUris.withAppendedId(CONTENT_URI,receipe_id);
        }



    }

}
