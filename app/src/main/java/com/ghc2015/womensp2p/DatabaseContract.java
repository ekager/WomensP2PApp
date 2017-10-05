package com.ghc2015.womensp2p;

import android.provider.BaseColumns;

/**
 * Defines table and column names for the viewer database.
 */
public class DatabaseContract {
    public static final class PostEntry implements BaseColumns {

        public static final String TABLE_NAME = "posts";

        // time
        public static final String COLUMN_TIME = "time";

        // Nullable Hack
        public static final String COLUMN_NULLABLE = "end_time";

        public static final String COLUMN_LOCATION = "location";

        public static final String COLUMN_REPORT = "report";

        public static final String COLUMN_POST_TEXT = "post_text";

        public static final String COLUMN_USER = "user";

        public static final String COLUMN_IMAGE = "image";

    }
}