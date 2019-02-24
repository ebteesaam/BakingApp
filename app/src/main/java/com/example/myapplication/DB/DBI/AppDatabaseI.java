package com.example.myapplication.DB.DBI;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.myapplication.Model.Ingredient;


@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDatabaseI extends RoomDatabase {

    private static AppDatabaseI INSTANCE;

    public static AppDatabaseI getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (new Object()) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabaseI.class, "ingredient")

                            .allowMainThreadQueries()
                           /// .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
       // Log.e("AppDatabaseI","nnnnn");
        return INSTANCE;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE `Movie` ADD `originalTitle` TEXT;");
//        }
//    };

    //
    public abstract IngredientDao IngredientDao();
}