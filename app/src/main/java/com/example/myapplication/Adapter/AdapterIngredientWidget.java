package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.R;

import java.util.List;

public class AdapterIngredientWidget extends ArrayAdapter<Ingredient> {


    private Context context;
    private List<Ingredient> ingredientList;
    private Ingredient ingredient;

    public AdapterIngredientWidget(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
        ingredientList = objects;
        this.context = context;
    }


//    public AdapterIngredientWidget(List<Ingredient> ingredientList) {
//        this();
//
//        this.ingredientList = ingredientList;
//    }

    public TextView ingredientTV;
    public TextView quantity;
    public TextView measure;

    public ConstraintLayout constraintLayout;

//    public AdapterIngredientWidget(Class<FetchDataWidget> fetchDataWidgetClass, int list_ingredeint, List<Ingredient> ingredientsList) {
//        super();
//    }

//    public AdapterIngredientWidget(Context context, int list_ingredeint, LiveData<List<Ingredient>> movies) {
//        super(context, list_ingredeint, (List<Ingredient>) movies);
//        ingredientList = (List<Ingredient>) movies;
//        this.context = context;
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_ingredeint, parent, false);
        ingredientTV = view.findViewById(R.id.ingredient);
        quantity = view.findViewById(R.id.quantity);
        measure = view.findViewById(R.id.measure);

        constraintLayout = view.findViewById(R.id.constraintl);

        final Ingredient ingredient = ingredientList.get(position);

        ingredientTV.setText(ingredient.getIngredient());
        measure.setText(ingredient.getMeasure());
        String q = String.valueOf(ingredient.getQuantity());
        quantity.setText(q);

        return view;
    }

}