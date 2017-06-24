package com.example.naveen.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naveen.bakingapp.models.Ingredient;
import com.example.naveen.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by User on 6/3/2017.
 */

public class IngridientAdapter extends RecyclerView.Adapter<IngridientAdapter.ViewHolder> {

ArrayList<Ingredient>ingredientArrayList;
    private Context context;

    public IngridientAdapter(ArrayList<Ingredient> ingredientArrayList, Context context) {
        this.ingredientArrayList = ingredientArrayList;
        this.context = context;
    }

    @Override
    public IngridientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indi_view_ingridient, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngridientAdapter.ViewHolder holder, int position) {
        Ingredient ingredient=ingredientArrayList.get(position);
        holder.measure.setText(ingredient.getMeasure());
        holder.ingredient.setText(ingredient.getIngredient());
        holder.quantity.setText(String.valueOf(ingredient.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView measure,ingredient,quantity;
        public ViewHolder(View itemView) {
            super(itemView);
            measure=(TextView)itemView.findViewById(R.id.measure);
            ingredient=(TextView)itemView.findViewById(R.id.ingredient);
            quantity=(TextView)itemView.findViewById(R.id.quantity);

        }
    }
}
