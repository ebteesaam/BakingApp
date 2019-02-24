package com.example.myapplication.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.DB.DBI.ContractIngredient;
import com.example.myapplication.R;

public class WidjetCurserAdapter extends CursorAdapter {
    long id;

    public WidjetCurserAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_ingredeint, parent, false);
    }

    public TextView ingredientTV;
    public TextView quantityTV;
    public TextView measureTV;
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ingredientTV = view.findViewById(R.id.ingredient);
        quantityTV = view.findViewById(R.id.quantity);
        measureTV = view.findViewById(R.id.measure);
        id = cursor.getLong(cursor.getColumnIndex(ContractIngredient.Recipe._ID));

        int ingredientColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.INGREDIENT);
        int measureColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.MEASURE);
        int quantityColumnIndex = cursor.getColumnIndex(ContractIngredient.Recipe.QUANTITY);
        String ingredient = cursor.getString(ingredientColumnIndex);
        String measure = cursor.getString(measureColumnIndex);
        double quantity = cursor.getDouble(quantityColumnIndex);
        ingredientTV.setText(ingredient);
        measureTV.setText(measure);
        String q = String.valueOf(quantity);
        quantityTV.setText(String.valueOf( quantity));
    }
}
