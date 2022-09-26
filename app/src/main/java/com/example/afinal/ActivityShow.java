package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ActivityShow extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    ArrayList<String> leson = new ArrayList<>();
    private ListView listView;
    StorageReference storageReference = storage.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listView=findViewById(R.id.fileListView);
        getFiles();

//        ShowAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

    }

    public void getFiles() {
        storageReference = storage.getReference().child("subject/teacher2/video");
        // show in list view all file names - each file is the song
        storageReference.listAll()
                .addOnCompleteListener(new OnCompleteListener<ListResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ListResult> task) {
                        leson.clear();
                        for (StorageReference item : task.getResult().getItems()
                        ) {
                            leson.add(item.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityShow.this, android.R.layout.simple_list_item_1, android.R.id.text1, leson);
                        listView.setAdapter(adapter
                        );
                        // when user clicks on an element -> this is the song to be chosen
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String name = adapter.getItem(i);

                                // storage reference is referring to the location of the video file in firebase storage
                                // in order to play using media player we use the url of the video file
                                // we can get the url or the video file using storageReference.getDownloadUrl
                                // each file in firebase storage has a sharing ling url :   http://_______
                                storageReference = storage.getReference().child("subject/teacher2/video/" + name);
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        // 1initalize player
                                        // 2download srt file
                                        //onStart(uri);\
                                        //                       initializePlayer(uri);
                                        //                       downloadSrtFile(name);
                                        //start next activity sending uri&name
                                       Intent intent = new Intent(ActivityShow.this,ActivityVideo.class);
                                       intent.putExtra("uri",uri);
                                       intent.putExtra("name",name);
                                       startActivity(intent);

                                        Toast.makeText(ActivityShow.this, "1 is working", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });
                            }
                        });
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FilesActivity", "onFailure: ");

                    }
                });


    }
}