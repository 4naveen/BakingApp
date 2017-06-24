package com.example.naveen.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naveen.bakingapp.R;
import com.example.naveen.bakingapp.models.ReceipeStep;
import com.example.naveen.bakingapp.ReceipeStepDetailActivity;

import java.util.ArrayList;

/**
 * Created by User on 6/3/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    ArrayList<ReceipeStep> stepArrayList;
    private Context context;
    public StepsAdapter(ArrayList<ReceipeStep> stepArrayList, Context context) {
        this.stepArrayList = stepArrayList;
        this.context = context;
    }

    public double deviceSize() {

        double size = 0;
        try {

            // Compute screen size

            DisplayMetrics dm = context.getResources().getDisplayMetrics();

            float screenWidth  = dm.widthPixels / dm.xdpi;

            float screenHeight = dm.heightPixels / dm.ydpi;

            size = Math.sqrt(Math.pow(screenWidth, 2) +

                    Math.pow(screenHeight, 2));
            int device_size= (int) size;
            Log.i("device_size", String.valueOf(size)+" ---"+String.valueOf(device_size));

        } catch(Throwable t) {

        }

        return size;

    }
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indi_view_steps, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        ReceipeStep receipeStep=stepArrayList.get(position);
        holder.descripton.setText(receipeStep.getDescription());
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView descripton;
        public ViewHolder(View itemView) {
            super(itemView);
            descripton=(TextView)itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           // Configuration config = context.getResources().getConfiguration();
        if (deviceSize() <= 7) {
            ReceipeStep receipeStep=stepArrayList.get(getAdapterPosition());
            Intent intent=new Intent(context,ReceipeStepDetailActivity.class);
            intent.putExtra("receipe_id",receipeStep.getReceipe_id());
            intent.putExtra("video_id",receipeStep.getVideo_id());
            intent.putExtra("receipe_image",receipeStep.getReceipe_image());

            intent.putExtra("video_url",receipeStep.getVideo_url());
            intent.putExtra("full_description",receipeStep.getFull_description());
            intent.putExtra("description",receipeStep.getDescription());

            context.startActivity(intent);
        }

        }
    }
}
