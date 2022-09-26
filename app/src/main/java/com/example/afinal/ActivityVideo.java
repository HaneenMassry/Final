package com.example.afinal;

import static android.R.layout.simple_list_item_1;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;


public class ActivityVideo extends AppCompatActivity {


    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri uri;
    private String name;
    private VideoView videoView;
    private TextView mBufferingTextView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";

    SearchView editsearch;
    ArrayList<String> leson = new ArrayList<>();
    private ArrayList<String> paragraphs,times;
    private ListView listView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        mBufferingTextView = findViewById(R.id.buffering_textview);
        editsearch = findViewById(R.id.searchView);


        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;//editsearch.getText().toString().toLowerCase(Locale.getDefault());
                int count = 0;
                for (int i = 0; i < paragraphs.size(); i++) {
                    if (paragraphs.get(i).contains(text))
                    {
                        count++;

                    }

                }
                Toast.makeText(ActivityVideo.this, "count=" + count, Toast.LENGTH_SHORT).show();

                String[] HaveText = new String[count];
                int done = 0;
                for (int i = 0; i < paragraphs.size(); i++) {
                    if (paragraphs.get(i).contains(text)) {
                        HaveText[done] = paragraphs.get(i);
                        done++;

                    }

                }

                // update list view
          //      adapter = new
                adapter.clear();
                adapter.addAll(HaveText);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        /*

    (new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                int count = 0;
                for (int i = 0; i < paragraphs.size(); i++) {
                    if (paragraphs.get(i).contains(text))
                    {
                        count++;

                    }

                }
                Toast.makeText(ActivityVideo.this, "count=" + count, Toast.LENGTH_SHORT).show();

                String[] HaveText = new String[count];
                int done = 0;
                for (int i = 0; i < paragraphs.size(); i++) {
                    if (paragraphs.get(i).contains(text))
                        done++;
                    HaveText[done] = paragraphs.get(i);

                }

                // update list view
                adapter.clear();
                adapter.addAll(HaveText);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

         */

    }



    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("uri")) {
                uri = (Uri) intent.getExtras().get("uri");
                name = intent.getExtras().getString("name");
                //tow functions
                downloadSrtFile(name);
                initializePlayer(uri);


            }
        }
    }


    private void downloadSrtFile(String name) {
        String fileName = name.replace("mp4", "txt");//"sample_srt4.txt";  string.replace("mp4","txt")
        StorageReference ref = storage.getReference().child("subject/teacher2/TXT/" + fileName);
        final long ONE_HUNDRED_K = 100 * 1024;
        ref.getBytes(ONE_HUNDRED_K).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // read this function
                String s = new String(bytes, StandardCharsets.UTF_8);
                //blocks
                // each block holds:
                // 16
                // time
                // text
                // s holds the string from the srt file
                //  s = "Hello Haneen, Reema, Allon"
                // ["Hello Haneen","Remma","Allon"]

                String[] arr = s.split("\\n");
                // ignore
                // add to times
                // add time+paragraph to pragraphs array
                // ignore
                //arr.length
                 paragraphs = new ArrayList<>();
                 times = new ArrayList<>();

                int index = 0;
                while (index < arr.length) {
                    try {

                        // ignore
                        index++;

                        // add to times
                        times.add(arr[index]);
                        index++;

                        // add the time+paragraph
                        paragraphs.add(arr[index - 1] + arr[index]);
                        index++;
                        // ignore
                        index++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // what we have here?
                // ArrayList -> paragrpahs
                // listView to display.
                // Adapter
                // listView - > onclick listener...


                 listView = findViewById(R.id.lvSubtitles);
                String[] strArray = paragraphs.toArray(new String[paragraphs.size()]);


                ArrayList<String> arrCopy =new ArrayList<>(paragraphs);


                adapter =
                        new ArrayAdapter(ActivityVideo.this, simple_list_item_1, arrCopy);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                        // have the index!
                                                        // get the time of starty fo this pargraph
                                                        String s1 = times.get(i);

                                                        String t = s1.substring(0, 8);

                                                        String H = s1.substring(0, 2);
                                                        int hh = Integer.parseInt(H) / 60 / 60;

                                                        String M = s1.substring(3, 5);
                                                        int mm = Integer.parseInt(M) / 60;


                                                        String s = s1.substring(6, 8);
                                                        int ss = Integer.parseInt(s);

                                                        int totalS = hh + ss + mm;

                                                        int totalMl = totalS * 1000;
                                                        videoView.seekTo(totalMl);

                                                        // set the mdeia [player time accordingly
                                                        // make it nicer visually!@

                                                    }
                                                }
                );
            }
        });
    }



    private void initializePlayer(Uri uri) {
        Log.d(TAG, "initializePlayer: start");
        // Show the "Buffering..." message while the video loads.
        //mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.
        Uri videoUri =Uri.parse(String.valueOf(uri)); //getMedia(VIDEO_SAMPLE);
        //   mVideoView.setVideoURI(videoUri);
        videoView.setVideoPath(String.valueOf(uri));
        videoView.requestFocus();
// Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);

        // Listener for onPrepared() event (runs after the media is prepared).
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        Log.d(TAG, "onPrepared:  first");


                        // Hide buffering message.
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            videoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            videoView.seekTo(1);
                        }

                        // Start playing!
                        videoView.start();
                    }
                });
        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(ActivityVideo.this,
                                "video completed",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        videoView.seekTo(0);
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }
    private void releasePlayer() {
        videoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {

            // you can also put a video file in raw package and get file from there as shown below

            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }





}