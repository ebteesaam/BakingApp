package com.example.myapplication.DB.DBR;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class ContractRecipe {

    public static final String PATH = "recipe";
    public static final String CONTENT_AUTHORITY = "com.example.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public ContractRecipe() {
    }

    public static final class Recipe implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        public final static String TABLE_NAME = "recipe";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public final static String _ID_Recipe = BaseColumns._ID;
        public final static String NAME = "name";
//        public final static String INGREDIENT = "ingredients";
//        public final static String STEPS = "steps";
        public final static String SERVINGS = "servings";
        public final static String IMAGE = "image";




    }
}
