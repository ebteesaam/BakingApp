package com.example.myapplication.Model;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@SuppressLint("ParcelCreator")
@Entity(tableName = "ingredient")
public class Ingredient implements Parcelable {

    @PrimaryKey
    @NonNull
    int idIngredient;

    double quantity;
    String measure;
    String idRecipe;
    String ingredient;

    public Ingredient(int idIngredient, double quantity, String measure, String idRecipe, String ingredient) {
        this.idIngredient = idIngredient;
        this.quantity = quantity;
        this.measure = measure;
        this.idRecipe = idRecipe;
        this.ingredient = ingredient;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        idIngredient = in.readInt();
        quantity = in.readDouble();
        measure = in.readString();
        idRecipe = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idIngredient);
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(idRecipe);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}