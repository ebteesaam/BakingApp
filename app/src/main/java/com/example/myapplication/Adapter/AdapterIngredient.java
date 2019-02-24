package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.Model.Steps;
import com.example.myapplication.R;

import java.util.List;

public class AdapterIngredient extends RecyclerView.Adapter<AdapterIngredient.RecipeIngredientViewHolder> {


    private Context context;
    private List<Ingredient> ingredientList;
    private Ingredient ingredient;
    private List<Steps> stepsList;
    private Steps steps;

    public AdapterIngredient(List<Ingredient> ingredientList, List<Steps> stepsList) {
        this.ingredientList = ingredientList;
        this.stepsList=stepsList;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.context=context;
        View view =  LayoutInflater.from(context).inflate(R.layout.list_ingredeint, viewGroup, false);
        RecipeIngredientViewHolder recipeViewHolder=new RecipeIngredientViewHolder(view);

        return recipeViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder recipeViewHolder, int i) {
        final Ingredient ingredient=ingredientList.get(i);

        recipeViewHolder.ingredientTV.setText(ingredient.getIngredient());
        recipeViewHolder.measure.setText(ingredient.getMeasure());
        String q= String.valueOf(ingredient.getQuantity());
        recipeViewHolder.quantity.setText(q);
        Log.e("IngredientAdapter",ingredient.getIngredient());

        Log.d(AdapterIngredient.class.toString(), "#" + ingredient.getIdRecipe());
//        recipeViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailsActivity.class);
//                String item = String.valueOf(bakingRecipe.getId());
//                intent.putExtra("id",item);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder{

        public TextView ingredientTV;
        public TextView quantity;
        public TextView measure;

        public ConstraintLayout constraintLayout;

        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);

            ingredientTV=itemView.findViewById(R.id.ingredient);
            quantity=itemView.findViewById(R.id.quantity);
            measure=itemView.findViewById(R.id.measure);

            constraintLayout=itemView.findViewById(R.id.constraintl);

        }

        void bind(int listIndex) {
//            image.setText(listIndex);
        }
    }



}
