package com.example.rgbcontrollerui.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.rgbcontrollerui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Song> allSongs = new ArrayList<>();
    ActivityResultLauncher<String> storagePermissionLauncher;
    final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_library);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.app_name));

        //recycler view
        recyclerView = findViewById(R.id.recyclerView);
//        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//            if (isGranted) {
//                fetchSongs();
//            } else {
//                userResponses();
//            }
//        });

        //launch storage permission on create
        storagePermissionLauncher.launch(permission);
    }

//    private void userResponses() {
//        if (ContextCompat.checkSelfPermission(this, permission) == getPackageManager().PERMISSION_GRANTED) {
//            //fetch songs
//            fetchSongs();
//        }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (shouldShowRequestPermissionRationale(permission)) {
//                //show an educational UI to user explaining why we need this permission
//                //use alert dialog
//                new AlertDialog.Builder(this)
//                        .setTitle("Request Permission")
//                        .setMessage("Allow us to fetch songs on your device")
//                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //request permission
//                                storagePermissionLauncher.launch(permission);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(getApplicationContext(), "You denied us to show songs", Toast.LENGTH_LONG).show();
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        }
//        else {
//            Toast.makeText(this,"You canceled to show songs", Toast.LENGTH_LONG).show();
//        }
//    }

//    private void fetchSongs() {
//        //define a list to query songs
//        List<Song> songs = new ArrayList<>();
//        Uri mediaStoreUri = null;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            mediaStoreUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
//        }
//
//        //define projection
//        String[] projection = new String[] {
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.SIZE,
//                MediaStore.Audio.Media.ALBUM_ID,
//        };
//
//        //order
//        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";
//
//        //get the songs
//        try(Cursor cursor = getContentResolver().query(mediaStoreUri, projection, null, null, sortOrder)) {
//            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
//            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
//            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
//            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
//            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
//
//            //clear the previously loaded before adding loading again
//            while (cursor.moveToNext()) {
//                //get the values of column for a given audio file
//                long id = cursor.getLong(idColumn);
//                String name = cursor.getString(nameColumn);
//                String duration = Integer.toString(cursor.getInt(durationColumn));
//                int size = cursor.getInt(sizeColumn);
//                long albumId = cursor.getLong(albumIdColumn);
//
//                //song uri
//                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
//
//                //album artwork uri
//                Uri albumArtworkUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
//
//                //remove .mp3 extension from song title
//                name = name.substring(0, name.lastIndexOf("."));
//
//                //song item
//                Song song = new Song(name, uri, albumArtworkUri, size, duration);
//
//                //add song item to song list
//                songs.add(song);
//            }
//            Log.d("Songs array size", String.valueOf(songs.size()));
//
//            //display songs
//            showSongs(songs);
//
//        }
//    }

//    private void showSongs(List<Song> songs) {
//        Log.d("Songs array size", String.valueOf(songs.size()));
//        if (songs.size() == 0) {
//            Toast.makeText(this, "No songs found!", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        //save songs
//        allSongs.clear();
//        allSongs.addAll(songs);
//
//        //update the tool bar title
//        String title = getResources().getString(R.string.app_name) + " - " + songs.size();
//        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
//
//        //Layout Manager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //songs adapter
//        songAdapter = new SongAdapter(this, songs);
//
//        //set the adapter to recycler view
//        recyclerView.setAdapter(songAdapter );
//    }
}