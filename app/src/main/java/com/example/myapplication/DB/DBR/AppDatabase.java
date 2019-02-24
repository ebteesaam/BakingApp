package com.example.myapplication.DB.DBR;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.Model.BakingRecipe;


@Database(entities = {BakingRecipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (new Object()) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, "recipe")

                            .allowMainThreadQueries()
                           /// .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        Log.e("AppDatabaseI","nnnnn");
        return INSTANCE;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE `Movie` ADD `originalTitle` TEXT;");
//        }
//    };

    //
    public abstract RecipeDao RecipeDao();
}