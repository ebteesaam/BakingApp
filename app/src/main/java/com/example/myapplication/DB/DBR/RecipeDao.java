package com.example.myapplication.DB.DBR;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.myapplication.Model.BakingRecipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    LiveData<List<BakingRecipe>> getAll();

@Update(onConflict = OnConflictStrategy.REPLACE)
void update(BakingRecipe bakingRecipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BakingRecipe bakingRecipe);

    @Delete
    void delete(BakingRecipe bakingRecipe);
}