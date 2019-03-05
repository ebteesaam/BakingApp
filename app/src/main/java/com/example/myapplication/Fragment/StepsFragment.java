package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DB.FetchDataStepsDetails;
import com.example.myapplication.Model.BakingRecipe;
import com.example.myapplication.Model.Ingredient;
import com.example.myapplication.Model.Steps;
import com.example.myapplication.R;
import com.example.myapplication.StepActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ValidFragment")
public class StepsFragment  extends Fragment implements ExoPlayer.EventListener{
    private static final String STEP_NUMBER = "step_num";
    private static final String RECIPE_NUMBER = "recipe_num";

    String data="";
    List<BakingRecipe> BRList;
    List<Ingredient> ingredientsList;
    List<Steps> stepsList;
    boolean videoboo;
    String shortdes;
    String des;
    int id;
    String idRecipe;
    public static TextView describtion;
    private NotificationManager mNotificationManager;
    public static SimpleExoPlayer mExoPlayer;
    public static SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;
//    @BindView(R.id.directions_toolbarText)TextView shortDes;
    @BindView(R.id.directions_toolbar_back_button)
ImageView backB;
    String thumbnail;
    private boolean isFullScreen = false;

    //
//    @BindView(R.id.directions_toolbar_forward_button)
//    ImageView forwardB;
    String video;
    ImageView thumbnailIV;
    FetchDataStepsDetails fetchDataStepsDetails;
    @SuppressLint("ValidFragment")
    public StepsFragment(String shortdes, String des, int id, String idRecipe, String video) {
        this.shortdes = shortdes;
        this.des = des;
        this.id = id;
        this.idRecipe = idRecipe;
        this.video = video;
    }
    public StepsFragment( int id, String idRecipe) {

        this.id = id;
        this.idRecipe = idRecipe;

    }
    public StepsFragment( ) {


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_directions, container,false);
        mPlayerView = view.findViewById(R.id.simple_exo_play_view);
        thumbnailIV=view.findViewById(R.id.thumbbnail);

        ButterKnife.bind(getActivity());

        initializeMediaSession();

//        @SuppressLint("ResourceType")
//        StepsFragment stepsFragment= (StepsFragment) getSupportFragmentManager().findFragmentById(R.layout.activity_step_fragment);

//        String shortdes=getActivity().getIntent().getExtras().getString("shortDescribtion");
//
//        String des=getActivity().getIntent().getExtras().getString("Describtion");
      // video=getActivity().getIntent().getExtras().getString("video");
//        final String id=getActivity().getIntent().getExtras().getString("id");
//        final String idRecipe=getActivity().getIntent().getExtras().getString("idRecipe");
//
//
//        getActivity().setTitle(shortdes);
//

//        id=StepActivity.id;
//        idRecipe=StepActivity.idRecipe;
//        shortdes=StepActivity.shortdes;
//        des=StepActivity.des;
       // video=StepActivity.video;
      //  Log.e("Ffffff",id+idRecipe+shortdes+des);
        if(savedInstanceState==null) {
             fetchDataStepsDetails = new FetchDataStepsDetails(id, Integer.valueOf(idRecipe));
            fetchDataStepsDetails.execute();
        }else {
            fetchDataStepsDetails = new FetchDataStepsDetails(savedInstanceState.getInt(STEP_NUMBER), savedInstanceState.getInt(idRecipe));
            fetchDataStepsDetails.execute();
        }
//        final int idInt= Integer.parseInt(StepActivity.id);
//        final int idRecipeInt= Integer.parseInt(idRecipe);

        describtion=view.findViewById(R.id.description);
      //  describtion.setText(des);
        video=fetchDataStepsDetails.videoURL;
        thumbnail=fetchDataStepsDetails.thumbnailURL;
//        videoboo=video.isEmpty();
        Log.e("videoL", String.valueOf(video));
//        if(videoURL==""||videoURL==null){
//            mPlayerView.setVisibility(View.GONE);
//        }else {
//            StepsFragment.call(Uri.parse(videoURL));
//            StepsFragment.initializeMediaSession();
//        }

       // Log.e("thumbnail", thumbnail);
//        if(thumbnail!=""){
//            thumbnailIV.setVisibility(View.VISIBLE);
////            Log.e("thumbnail", thumbnail);
//     //       thumbnailIV.setImageURI(Uri.parse(thumbnail));
//            Picasso.with(getActivity())
//                    .load(thumbnail)
//                    .into(thumbnailIV);
//        }

            if(video==""||video==null){
                mPlayerView.setVisibility(View.GONE);
            }else {
                initializePlayer(Uri.parse(video));
                initializeMediaSession();


            }
            if(view != null) {
                return view;
            }


       // shortDes.setText(shortdes);


            return container;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_NUMBER,id);
        outState.putString(RECIPE_NUMBER, idRecipe);

    }

    public  void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener( this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking Recipe");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(video==""||video==null){

        }else {
            releasePlayer();
            mMediaSession.setActive(false);}

    }

    /**
     * Release ExoPlayer.
     */
    public void releasePlayer() {
        if(video==""||video==null){

        }else {
            mNotificationManager.cancelAll();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;}

    }

    public  void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), "Step");

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        showNotification(mStateBuilder.build());


    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
    private void showNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getContext());

        int icon;
        String play_pause;
        if(state.getState() == PlaybackStateCompat.STATE_PLAYING){
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }


        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(getContext(),
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (getContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (getContext(), 0, new Intent(getActivity(), StepActivity.class), 0);

        builder.setContentTitle(getString(R.string.guess))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_music_note)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0,1));


        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

}
