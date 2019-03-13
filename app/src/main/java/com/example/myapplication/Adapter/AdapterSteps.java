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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Model.Steps;
import com.example.myapplication.R;
import com.example.myapplication.StepActivity;

import java.util.List;

public class AdapterSteps extends RecyclerView.Adapter<AdapterSteps.RecipeIngredientViewHolder> {


    private Context context;

    private List<Steps> stepsList;
    private Steps steps;

    public AdapterSteps(List<Steps> stepsList) {
        this.stepsList=stepsList;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.context=context;
        View view =  LayoutInflater.from(context).inflate(R.layout.list_steps, viewGroup, false);
        RecipeIngredientViewHolder recipeViewHolder=new RecipeIngredientViewHolder(view);

        return recipeViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder recipeViewHolder, int i) {
        final Steps steps=stepsList.get(i);
        final int sizeStep=stepsList.size()-1;
        final String videourl;
        recipeViewHolder.num.setText(steps.getId());
        recipeViewHolder.steps.setText(steps.getShortDescription());
        final  boolean video=steps.getVideoURL().isEmpty();
        Log.e("Step", String.valueOf(video));
        if(video != true) {
            videourl = String.valueOf(steps.getVideoURL());


        }else {
           videourl =null;
            Log.e("videoD", String.valueOf(steps.getVideoURL().isEmpty()));

        }
        Log.e("SizeF", String.valueOf(sizeStep));


        recipeViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StepActivity.class);
                String Desc = String.valueOf(steps.getDescription());

                intent.putExtra("idRecipe",steps.getIdRecipe());
                intent.putExtra("size",sizeStep);
//                Log.e("videoD", videourl);
                intent.putExtra("video",videourl);
                intent.putExtra("id",steps.getId());

                intent.putExtra("Describtion",Desc);
                intent.putExtra("shortDescribtion",steps.getShortDescription());


                context.startActivity(intent);

            }
        });

//        Log.e("IngredientAdapter",ingredient.getIngredient());
//
//        Log.d(AdapterSteps.class.toString(), "#" + ingredient.getId());
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
        return stepsList.size();
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder{

        public TextView num;
        public TextView steps;


        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);

            num=itemView.findViewById(R.id.num);
            steps=itemView.findViewById(R.id.steps);

            constraintLayout=itemView.findViewById(R.id.constraintl);
            imageView=itemView.findViewById(R.id.describtion_arrow);

        }

        void bind(int listIndex) {
//            image.setText(listIndex);
        }
    }



}
