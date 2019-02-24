package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DetailsActivity;
import com.example.myapplication.Fragment.StepsFragmentTowPane;
import com.example.myapplication.Model.Steps;
import com.example.myapplication.R;

import java.util.List;

public class AdapterStepsTwoPane extends RecyclerView.Adapter<AdapterStepsTwoPane.RecipeIngredientViewHolder> {


    private Context context;

    private List<Steps> stepsList;
    private Steps steps;
    FragmentManager fragmentManager;


    public AdapterStepsTwoPane(List<Steps> stepsList) {
        this.stepsList=stepsList;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.context=context;
        View view =  LayoutInflater.from(context).inflate(R.layout.list_steps, viewGroup, false);
        RecipeIngredientViewHolder recipeViewHolder=new RecipeIngredientViewHolder(view);
        //fragmentManager = viewGroup.getAc().getSupportFragmentManager();
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
        if(video != true) {
            videourl = String.valueOf(steps.getVideoURL());

        }else {
           videourl =null;
            Log.e("videoD", String.valueOf(steps.getVideoURL().isEmpty()));

        }
        Log.e("SizeF", String.valueOf(sizeStep));

      // fragmentManager = context.getSupportFragmentManager();

        recipeViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StepsFragmentTowPane fragmentg = new StepsFragmentTowPane(Integer.parseInt(steps.getId()),steps.getIdRecipe());
                DetailsActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_recipe, fragmentg)
                        .commit();

//        FetchDataStepsDetailsTwoPane fetchDataStepsDetailsTwoPane=
//                new FetchDataStepsDetailsTwoPane(Integer.parseInt(steps.getId()),Integer.parseInt(steps.getIdRecipe()));
//        fetchDataStepsDetailsTwoPane.execute();
    }
});




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
