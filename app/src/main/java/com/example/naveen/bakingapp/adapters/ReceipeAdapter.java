package com.example.naveen.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naveen.bakingapp.R;
import com.example.naveen.bakingapp.models.Receipe;
import com.example.naveen.bakingapp.ReceipeStepActivity;

import java.util.ArrayList;

/**
 * Created by User on 6/3/2017.
 */

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.ViewHolder> {
    private ArrayList<Receipe> receipeArrayList;
    private Context context;
     int []images=new int[]{R.drawable.receipe1,R.drawable.receipe2,R.drawable.receipe3,R.drawable.receipe4};
    public ReceipeAdapter(ArrayList<Receipe> receipeArrayList, Context context) {
        this.receipeArrayList = receipeArrayList;
        this.context = context;
    }

    @Override
    public ReceipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indi_view_receipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceipeAdapter.ViewHolder holder, int position) {
        Receipe receipe=receipeArrayList.get(position);
        holder.name.setText(receipe.getReceipe_name());
        holder.image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return receipeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*    @BindView(R.id.name) TextView name;
        @BindView(R.id.image) ImageView image;*/
        TextView name;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
           // ButterKnife.bind(context, itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            image=(ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Receipe receipe=receipeArrayList.get(getAdapterPosition());
            Intent intent=new Intent(context,ReceipeStepActivity.class);
            intent.putExtra("receipe_id",receipe.getId());
            intent.putExtra("receipe_image",images[getAdapterPosition()]);
            intent.putExtra("receipe_name",receipe.getReceipe_name());

            context.startActivity(intent);
        }
    }
}
