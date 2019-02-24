package com.example.myapplication.Model;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "recipe")
@SuppressLint("ParcelCreator")
public class BakingRecipe implements Parcelable {

    String name;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    String servings;

    public BakingRecipe(String name, String id, String servings) {
        this.name = name;
        this.id = id;
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    protected BakingRecipe(Parcel in) {
        name = in.readString();
        id = in.readString();
        servings = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(servings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BakingRecipe> CREATOR = new Creator<BakingRecipe>() {
        @Override
        public BakingRecipe createFromParcel(Parcel in) {
            return new BakingRecipe(in);
        }

        @Override
        public BakingRecipe[] newArray(int size) {
            return new BakingRecipe[size];
        }
    };
}
