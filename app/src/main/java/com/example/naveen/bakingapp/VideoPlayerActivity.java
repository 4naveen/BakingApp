package com.example.naveen.bakingapp;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VideoPlayerActivity extends AppCompatActivity {
    int receipe_id;
    String video_url;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private ExoPlayer.EventListener exoPlayerEventListener;
    private static final String TAG = "VideoPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        receipe_id=getIntent().getIntExtra("receipe_id",0);
        video_url=getIntent().getStringExtra("video_url");
        Log.i("video_url",video_url);
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);
        Uri mp4VideoUri =Uri.parse(video_url);
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(VideoPlayerActivity.this, Util.getUserAgent(VideoPlayerActivity.this, "BakingApp"), bandwidthMeterA);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);
        //final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        player.prepare(videoSource);
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG,"Listener-onLoadingChanged...");

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG,"Listener-onPlayerStateChanged...");

            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG,"Listener-onTimelineChanged...");

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG,"Listener-onPlayerError...");
                player.stop();
               // player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG,"Listener-onPositionDiscontinuity...");

            }
        });
        player.setPlayWhenReady(true);

//new GetVideo().execute();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"onDestroy()...");
        player.release();

    }

 /*   public class GetVideo extends AsyncTask<String,Void,String>
    {   String response;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         *//*   dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);*//*
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
           // if (dialog!=null&&dialog.isShowing()){dialog.dismiss();}
            try {
                // JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);
                    if (object.getInt("id")==receipe_id){

                        JSONArray jsonArray1=object.getJSONArray("steps");
                        for (int j=0;j<jsonArray1.length();j++)
                        {
                            JSONObject object1=jsonArray1.getJSONObject(j);
                            Log.i("video_id from url", String.valueOf(object1.getInt("id")));
                            Log.i("video_id ", String.valueOf(video_id));

                            if (object1.getInt("id")==video_id){
                               // Log.i("video_url ", object1.getString("videoURL"));
                                Uri mp4VideoUri =Uri.parse(object1.getString("videoURL"));
                                DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
                                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(VideoPlayerActivity.this, Util.getUserAgent(VideoPlayerActivity.this, "bakingapp"), bandwidthMeterA);
                                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                                MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
                                final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
                                player.prepare(loopingSource);
                                player.addListener(new ExoPlayer.EventListener() {
                                    @Override
                                    public void onLoadingChanged(boolean isLoading) {
                                        Log.v(TAG,"Listener-onLoadingChanged...");

                                    }

                                    @Override
                                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                        Log.v(TAG,"Listener-onPlayerStateChanged...");

                                    }

                                    @Override
                                    public void onTimelineChanged(Timeline timeline, Object manifest) {
                                        Log.v(TAG,"Listener-onTimelineChanged...");

                                    }

                                    @Override
                                    public void onPlayerError(ExoPlaybackException error) {
                                        Log.v(TAG,"Listener-onPlayerError...");
                                        player.stop();
                                        player.prepare(loopingSource);
                                        player.setPlayWhenReady(true);
                                    }

                                    @Override
                                    public void onPositionDiscontinuity() {
                                        Log.v(TAG,"Listener-onPositionDiscontinuity...");

                                    }
                                });
                                player.setPlayWhenReady(true);
                            }

                        }


                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }*/
}
