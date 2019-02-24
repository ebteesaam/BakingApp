package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.DetailsActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.R;

import java.util.List;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.RecipeViewHolder> {


    private Context context;
    private List<BakingRecipe> bakingRecipesList;
    private BakingRecipe bakingRecipe;

    public AdapterRecipe(List<BakingRecipe> bakingRecipesList) {
        this.bakingRecipesList = bakingRecipesList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.context=context;
        View view =  LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        RecipeViewHolder recipeViewHolder=new RecipeViewHolder(view);

        return recipeViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        final BakingRecipe bakingRecipe=bakingRecipesList.get(i);

        recipeViewHolder.recipe_name.setText(bakingRecipe.getName());
        recipeViewHolder.servings.setText(bakingRecipe.getServings());

        Log.d(AdapterRecipe.class.toString(), "#" + bakingRecipe.getName());
        recipeViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                String item = String.valueOf(bakingRecipe.getId());
                intent.putExtra("id",item);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bakingRecipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        public TextView recipe_name;
        public TextView servings;
        public ConstraintLayout constraintLayout;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipe_name=itemView.findViewById(R.id.recipe_name);
            servings=itemView.findViewById(R.id.servings);
            constraintLayout=itemView.findViewById(R.id.constraintl);

        }

        void bind(int listIndex) {
//            image.setText(listIndex);
        }
    }



}
