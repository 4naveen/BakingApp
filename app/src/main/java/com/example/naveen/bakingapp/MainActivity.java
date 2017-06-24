package com.example.naveen.bakingapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.naveen.bakingapp.adapters.ReceipeAdapter;
import com.example.naveen.bakingapp.data.ReceipeContract;
import com.example.naveen.bakingapp.models.Receipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   // @BindView(R.id.recyclerview) RecyclerView recyclerView;
    ArrayList<Receipe>receipeArrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        receipeArrayList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
       /* for (int i=0;i<5;i++)
        {
            Receipe receipe=new Receipe();
            receipe.setReceipe_name("Natelie Pie");
            receipe.setReceipe_image(R.drawable.receipe1);
            receipeArrayList.add(receipe);
        }
 */
new GetAllReceipe().execute();
    }
    public class GetAllReceipe extends AsyncTask<String,Integer,String>
    {   String response;
        ProgressDialog dialog;
        ContentValues receipeValuesArr;
        int progressStatus = 0;
        boolean running;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
            dialog.setProgress(progressStatus);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    running = false;
                }
            });
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

                int i = 5;
                while (running & progressStatus < 5) {
                    try {
                        progressStatus++;
                        publishProgress(progressStatus);
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i-- == 0) {
                        running = false;
                    }
                    publishProgress(i);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(progressStatus);
        }
        @Override
        protected void onPostExecute(String response) {
            //Log.i("result",response.toString());
            if (dialog!=null&&dialog.isShowing()){dialog.dismiss();}
            try {
               // JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);
                    receipeValuesArr = new ContentValues();
                    Receipe receipe=new Receipe();
                    receipe.setReceipe_name(object.getString("name"));
                    receipe.setId(object.getInt("id"));
                    receipeArrayList.add(receipe);
                    receipeValuesArr.put(ReceipeContract.ReceipeEntry.COLUMN_RECEIPE_ID,object.getInt("id"));
                    receipeValuesArr.put(ReceipeContract.ReceipeEntry.COLUMN_RECEIPE_NAME, object.getString("name"));
                    getContentResolver().insert(ReceipeContract.ReceipeEntry.CONTENT_URI,receipeValuesArr);
                }
                RecyclerView.LayoutManager lmanager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(lmanager);
                recyclerView.setAdapter(new ReceipeAdapter(receipeArrayList,MainActivity.this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
