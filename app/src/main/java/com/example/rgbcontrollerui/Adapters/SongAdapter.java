//package com.example.rgbcontrollerui.Adapters;
//
//import android.content.Context;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.rgbcontrollerui.R;
//import com.example.rgbcontrollerui.UI.Song;
//
//import java.util.List;
//
//public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    //members
//    Context context;
//    List<Song> songs;
//
//    //constructor
//    public SongAdapter(Context context, List<Song> songs) {
//        this.context = context;
//        this.songs = songs;
//    }
//    //View Holder
//    public static class SongViewHolder extends RecyclerView.ViewHolder {
//        //Members
//        ImageView artworkHolder;
//        TextView titleHolder, durationHolder, sizeHolder;
//
//        public SongViewHolder(@NonNull View song_row_item) {
//            super(song_row_item);
//
//            artworkHolder = song_row_item.findViewById(R.id.albumCoverImgView);
//            titleHolder = song_row_item.findViewById(R.id.songTitleView);
//            durationHolder = song_row_item.findViewById(R.id.songDurationTextView);
//            sizeHolder = song_row_item.findViewById(R.id.songSizeTextView);
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row_item, parent, false);
//        return new SongViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        //current song and view holder
//        Song song = songs.get(position);
//        SongViewHolder viewHolder = (SongViewHolder) holder;
//
//        //set values to views
//        viewHolder.titleHolder.setText(song.getTitle());
//        viewHolder.durationHolder.setText(song.getDuration());
//        viewHolder.sizeHolder.setText(song.getSize());
//
//        //artwork
//        Uri artworkUri = song.getArtworkUri();
//
////        if (artworkUri!= null) {
////            //set the uri to image view
////            viewHolder.artworkHolder.setImageURI(artworkUri);
////
////            //make sure that the uri has an artwork
////            if (viewHolder.artworkHolder.getDrawable() == null) {
////                viewHolder.artworkHolder.setImageResource(R.drawable.song_default_icon);
////            }
////        }
//
//        //on item click
//        viewHolder.itemView.setOnClickListener(view -> Toast.makeText(context, song.getTitle(), Toast.LENGTH_SHORT).show());
//    }
//
//    @Override
//    public int getItemCount() {
//        return songs.size();
//    }
//
//    //filter songs/search results
//    public void filterSongs(List<Song> filteredList) {
//        songs = filteredList;
//        notifyDataSetChanged();
//    }
//}
