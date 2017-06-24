package com.example.naveen.bakingapp.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naveen.bakingapp.R;
import com.example.naveen.bakingapp.adapters.IngridientAdapter;
import com.example.naveen.bakingapp.models.Ingredient;

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
public class IngredientFragment extends Fragment {
    int receipe_id;
    RecyclerView recyclerView;
    ArrayList<Ingredient>ingredientArrayList;
    public IngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_ingredient, container, false);
        receipe_id=getArguments().getInt("receipe_id");
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(getArguments().getString("receipe_name"));
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        ingredientArrayList=new ArrayList<>();

new GetIngredient().execute();
        return v;


    }
    public class GetIngredient extends AsyncTask<String,Void,String>
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
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);

                    if (object.getInt("id")==receipe_id){

                        JSONArray jsonArray1=object.getJSONArray("ingredients");
                        for (int j=0;j<jsonArray1.length();j++){
                            JSONObject jsonObject=jsonArray1.getJSONObject(j);
                            Ingredient ingredient=new Ingredient();
                            ingredient.setIngredient(jsonObject.getString("ingredient"));
                            ingredient.setQuantity(jsonObject.getInt("quantity"));
                            ingredient.setMeasure(jsonObject.getString("measure"));
                            ingredientArrayList.add(ingredient);
                        }

                    }


                }
                RecyclerView.LayoutManager lmanager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(lmanager);
                recyclerView.setAdapter(new IngridientAdapter(ingredientArrayList,getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
