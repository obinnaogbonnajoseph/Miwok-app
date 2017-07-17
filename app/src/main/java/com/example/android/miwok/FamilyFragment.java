package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    //This sets up the AudioManager variable
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This sets up an Audio Focus Change listener, that notifies us of any change in state
     * of Audio Focus
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    //If there is a temporary loss of AudioFocus, execute the code below
                    if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //Pauses the playback
                        mMediaPlayer.pause();
                        //Returns the playback to beginning.
                        mMediaPlayer.seekTo(0);
                    }
                    //This restarts the playback after AudioFocus is regained.
                    else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();
                    }
                    //This action occurs when there is a permanent loss of AudioFocus
                    //Example, when another app that requires audio is started.
                    //Like the music app.
                    else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                }
            };



    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //Sets me up to get the AudioFocus I need and play with it.
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        // Create an {@link itemsAdapter}, whose data source is a list of Words. The
        // adapter knows how to create layouts for each item in the list, using the
        // list_item.xml layout resource personally defined.
        // This list item layout contains an ImageView and two TextViews which display the
        // english and miwok translations.
        WordAdapter itemsAdapter =
                new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_listyout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);

        // Set up a listener that makes each item on the list responsive to click.
        // It is meant to play an audio when clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set the mMediaPlayer to null so that it releases previous song to play a new one
                releaseMediaPlayer();
                // Gets the current word on the list
                Word word = words.get(position);

                //This requests for Audio Focus.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                // This checks if request for Audio Focus has been granted. If granted,
                //play audio.
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // Creates an instance of mediaPlayer and prepares to play an audio
                    mMediaPlayer = MediaPlayer.create(getActivity(),
                            word.getMediaResourceId());
                    mMediaPlayer.start();
                }
                //Sets up an onCompletionListener to release the audio once it is done.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });

        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            //Please abandon the AudioFocus after resources are destroyed. Thank you.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
