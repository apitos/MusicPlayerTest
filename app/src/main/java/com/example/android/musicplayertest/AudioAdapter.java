package com.example.android.musicplayertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Apt on 15/05/2018.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<AudioModel> mList;

    AudioAdapter(Context context, ArrayList<AudioModel> list) {
        mContext = context;
        mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_artistName, item_songTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_artistName = itemView.findViewById(R.id.item_artistName);
            item_songTitle = itemView.findViewById(R.id.songTitle);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.rv_audio_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final AudioModel audioItem = mList.get(position);

        ImageView image = holder.item_image;
        TextView artistName, songTitle;

        artistName = holder.item_artistName;
        songTitle = holder.item_songTitle;


        image.setImageResource(audioItem.getImage());

        artistName.setText(audioItem.getArtistName());
        songTitle.setText(audioItem.getSongTitle());





           holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "**" +audioItem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, MainActivity.class);
               mContext.startActivity(intent);

                private int currentPosition = 0;

                currentPosition = position;
                Intent i = new Intent(mContext, MainActivity.class);
                //String song = AudioModel.class.getsongId(currentPosition);
                String song = mList.get(position);
                //i.putExtra("position", currentPosition);
               i.putExtra("song", song);
               mContext.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
