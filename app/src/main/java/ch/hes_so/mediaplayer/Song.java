package ch.hes_so.mediaplayer;

import android.graphics.Bitmap;

/**
 * Created by Pascal Bruegger on 08/01/16.
 */
public class Song {

    private String songTitle;
    private String artist_Name;
    private String album;
    private Bitmap artwork;

    public Song(String title, String artist) {
        this.songTitle = title;
        this.artist_Name = artist;
    }

    public String getSongTitle() {

        return songTitle;
    }

    public void setSongTitle(String songTitle) {

        this.songTitle = songTitle;
    }

    public String getArtistName() {

        return artist_Name;
    }

    public void setArtistName(String artist_Name) {

        this.artist_Name = artist_Name;
    }

}
