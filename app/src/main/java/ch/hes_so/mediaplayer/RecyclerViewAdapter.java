package ch.hes_so.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Iosis on 20.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SongListManager songListManager;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView artist;
        public ImageView image;
        public LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.title);
            this.artist = (TextView) v.findViewById(R.id.artist);
            this.image = (ImageView) v.findViewById(R.id.artwork);
            this.layout = (LinearLayout) v.findViewById(R.id.line_layout);
        }
    }

    public RecyclerViewAdapter(Context context)
    {
        this.songListManager = new SongListManager(context);
        this.context = context;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaPlayerActivity.class);
                intent.putExtra("SONG_INDEX", (Integer)v.getTag());
                context.startActivity(intent);
            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        RecyclerViewAdapter.ViewHolder holder = (RecyclerViewAdapter.ViewHolder)h;
        holder.title.setText(this.songListManager.getTitle(position));
        holder.artist.setText(this.songListManager.getArtist(position));
        holder.layout.setTag(position);
        try {
            if(this.songListManager.getArtwork(position) != null)
                holder.image.setImageBitmap(this.songListManager.getArtwork(position));
            else
                holder.image.setImageResource(android.R.drawable.ic_media_play);
        }
        catch (Exception e) {
            Log.e("MediaPlayer", " -> " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return songListManager.getPlayListLength();
    }
}
