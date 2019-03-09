package com.example.myapplication.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.DB.DBI.ContractIngredient;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.R;

import java.util.List;

public class AdapterIngredientWidgetCurser extends CursorAdapter {


    private Context context;
    private List<Ingredient> ingredientList;
    private Ingredient ingredient;




//    public AdapterIngredientWidget(List<Ingredient> ingredientList) {
//        this();
//
//        this.ingredientList = ingredientList;
//    }

    public TextView ingredientTV;
    public TextView quantityTV;
    public TextView measureTV;

    public ConstraintLayout constraintLayout;

    public AdapterIngredientWidgetCurser(Context context, Cursor c) {
        super(context, c);
    }

//    public AdapterIngredientWidget(Class<FetchDataWidget> fetchDataWidgetClass, int list_ingredeint, List<Ingredient> ingredientsList) {
//        super();
//    }

//    public AdapterIngredientWidget(Context context, int list_ingredeint, LiveData<List<Ingredient>> movies) {
//        super(context, list_ingredeint, (List<Ingredient>) movies);
//        ingredientList = (List<Ingredient>) movies;
//        this.context = context;
//    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.list_ingredeint, parent, false);
//        ingredientTV = view.findViewById(R.id.ingredient);
//        quantity = view.findViewById(R.id.quantity);
//        measure = view.findViewById(R.id.measure);
//
//        constraintLayout = view.findViewById(R.id.constraintl);
//
//        final Ingredient ingredient = ingredientList.get(position);
//
//        ingredientTV.setText(ingredient.getIngredient());
//        measure.setText(ingredient.getMeasure());
//        String q = String.valueOf(ingredient.getQuantity());
//        quantity.setText(q);
//
//        return view;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_ingredeint, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ingredientTV = view.findViewById(R.id.ingredient);
        quantityTV = view.findViewById(R.id.quantity);
        measureTV = view.findViewById(R.id.measure);

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