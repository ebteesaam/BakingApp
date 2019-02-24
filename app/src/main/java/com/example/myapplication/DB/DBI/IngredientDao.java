package com.example.myapplication.DB.DBI;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.myapplication.Model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    LiveData<List<Ingredient>> getAll();

@Update(onConflict = OnConflictStrategy.REPLACE)
void update(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredient")
    void delete();
}