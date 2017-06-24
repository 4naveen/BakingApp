package com.example.naveen.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naveen.bakingapp.models.ReceipeStep;
import com.example.naveen.bakingapp.utils.AppSession;

public class ReceipeStepDetailActivity extends AppCompatActivity {
    int receipe_id,video_id;
    String video_url;
    FrameLayout frameLayout;
    TextView description;
    ImageView left,right,play_image;
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_step_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra("description"));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        frameLayout = (FrameLayout) findViewById(R.id.layout_back);
        description = (TextView) findViewById(R.id.description);
        description.setText(getIntent().getStringExtra("full_description"));
        receipe_id = getIntent().getIntExtra("receipe_id", 0);
        video_id = getIntent().getIntExtra("video_id", 0);
        video_url = getIntent().getStringExtra("video_url");
        left=(ImageView)findViewById(R.id.left);
        right=(ImageView)findViewById(R.id.right);
        play_image=(ImageView)findViewById(R.id.play);
        frameLayout.setBackgroundResource(getIntent().getIntExtra("receipe_image", 0));
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceipeStepDetailActivity.this, VideoPlayerActivity.class);
                intent.putExtra("receipe_id", receipe_id);
                intent.putExtra("video_url", video_url);
                // intent.putExtra("force_fullscreen",true);
                startActivity(intent);
            }
        });
     /*   Log.i("receipe_id", String.valueOf(receipe_id));
        Log.i("video_id", String.valueOf(video_id));*/
right.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (video_id< AppSession.receipeStepArrayList.size()-1)
        {
            ReceipeStep receipeStep=AppSession.receipeStepArrayList.get(video_id+1);
            video_id++;
            description.setText(receipeStep.getFull_description());
            actionBar.setTitle(receipeStep.getDescription());
            video_url = receipeStep.getVideo_url();


        }


    }
});
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_id>0){
                    ReceipeStep receipeStep=AppSession.receipeStepArrayList.get(video_id-1);
                    description.setText(receipeStep.getFull_description());
                    actionBar.setTitle(receipeStep.getDescription());
                    video_url = receipeStep.getVideo_url();

                    video_id--;
                }

            }
        });


       /* for (ReceipeStep receipeStep:AppSession.receipeStepArrayList) {
            Log.i("Arraylist:--", String.valueOf(receipeStep.getDescription()));
        }*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);

    }
}
