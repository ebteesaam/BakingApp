package com.example.myapplication.DB.DBI;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ebtesam on 19/12/2018 AD.
 */

public class ContractIngredient {

    public static final String PATH = "ingredient";
    public static final String CONTENT_AUTHORITY = "com.example.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public ContractIngredient() {
    }

    public static final class Recipe implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        public final static String TABLE_NAME = "ingredient";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);
        public final static String _ID= BaseColumns._ID;

        public final static String _ID_Recipe = "idrecipe";
        public final static String QUANTITY = "quantity";
        public final static String MEASURE = "measure";
        public final static String INGREDIENT = "ingredient";




    }
}
