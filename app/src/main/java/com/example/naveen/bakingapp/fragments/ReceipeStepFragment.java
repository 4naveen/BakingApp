package com.example.naveen.bakingapp.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.naveen.bakingapp.utils.AppSession;
import com.example.naveen.bakingapp.R;
import com.example.naveen.bakingapp.utils.RecyclerTouchListener;
import com.example.naveen.bakingapp.VideoPlayerActivity;
import com.example.naveen.bakingapp.adapters.StepsAdapter;
import com.example.naveen.bakingapp.models.ReceipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReceipeStepFragment extends Fragment {
    int receipe_id,receipe_image;
    RecyclerView recyclerView;
    ArrayList<ReceipeStep> stepArrayList;
    //for tablet
    String video_url;
    TextView description;
    FrameLayout frameLayout;
    public ReceipeStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_receipe_step, container, false);
        receipe_id=getArguments().getInt("receipe_id");
        receipe_image=getArguments().getInt("receipe_image");

        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        stepArrayList=new ArrayList<>();
        AppSession.receipeStepArrayList=new ArrayList<>();
        if (deviceSize()>7){
            frameLayout = (FrameLayout)v.findViewById(R.id.layout_back);
            description = (TextView)v.findViewById(R.id.description);
            frameLayout.setBackgroundResource(receipe_image);

        }

        new GetReceipeSteps().execute();
        return v;

    }
    public class GetReceipeSteps extends AsyncTask<String,Void,String>
    {   String response;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url ;
            HttpURLConnection connection ;
            try {
                url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
                connection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String temp;
                while ((temp=br.readLine())!=null)
                {
                    buffer.append(temp);
                }
                response=buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String response) {
            Log.i("result",response.toString());
            if (dialog!=null&&dialog.isShowing()){dialog.dismiss();}
            try {
                // JSONObject jsonObject=new JSONObject(response);
                if (AppSession.receipeStepArrayList.size()!=0)
                {
                    AppSession.receipeStepArrayList.clear();
                }
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);
                    if (object.getInt("id")==receipe_id){

                        JSONArray jsonArray1=object.getJSONArray("steps");
                        for (int j=0;j<jsonArray1.length();j++){
                            JSONObject jsonObject=jsonArray1.getJSONObject(j);
                            ReceipeStep step=new ReceipeStep();
                            step.setDescription(jsonObject.getString("shortDescription"));
                            step.setReceipe_id(receipe_id);
                            step.setVideo_id(jsonObject.getInt("id"));
                            step.setVideo_url(jsonObject.getString("videoURL"));
                            step.setFull_description(jsonObject.getString("description"));
                            step.setReceipe_image(receipe_image);
                            stepArrayList.add(step);
                            AppSession.receipeStepArrayList.add(step);
                        }
                    }
                }
                RecyclerView.LayoutManager lmanager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(lmanager);
                recyclerView.setAdapter(new StepsAdapter(stepArrayList,getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
if (deviceSize()>7)
{
    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override

        public void onClick(View view, int position) {

            ReceipeStep receipeStep=stepArrayList.get(position);
            description.setText(receipeStep.getFull_description());
            video_url=receipeStep.getVideo_url();
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));
    frameLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
            intent.putExtra("receipe_id", receipe_id);
            intent.putExtra("video_url", video_url);
            startActivity(intent);
        }
    });
}
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public double deviceSize() {

        double size = 0;
        try {

            // Compute screen size

            DisplayMetrics dm = getResources().getDisplayMetrics();

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
}
