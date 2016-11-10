package ch.hes_so.mediaplayer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;

public class SongListManager {

    Cursor musicCursor;
    int music_column_index;
    Context context;
    ArrayList<Song> songList;
    MediaMetadataRetriever metaDataRetriever;
    private byte[] art;

    public SongListManager(Context context) {
        this.context = context;
        System.gc();
        metaDataRetriever = new MediaMetadataRetriever();
        songList = new ArrayList<Song>();

        String[] proj = {BaseColumns._ID, MediaColumns.DATA,
                AudioColumns.ALBUM, AudioColumns.ARTIST,
                MediaColumns.DISPLAY_NAME, MediaColumns.TITLE,
                MediaColumns.SIZE};

        String audioselection = AudioColumns.IS_MUSIC + " != 0";
        String sortOrder = AudioColumns.ARTIST + " ASC, " +
                AudioColumns.ALBUM + " ASC";

        // *************************************
        // Gets the media content of the phone
        // *************************************
        musicCursor = new CursorLoader(context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj,
                audioselection, null, sortOrder).loadInBackground();

        // ************************************
        // Populating the array list of songs
        // ************************************

        if (musicCursor.getCount() != 0) {

            while (!musicCursor.isLast()) {
                musicCursor.moveToNext();

                music_column_index = musicCursor
                        .getColumnIndexOrThrow(AudioColumns.ARTIST);

                String artistText = musicCursor.getString(music_column_index);
                music_column_index = musicCursor
                        .getColumnIndexOrThrow(MediaColumns.TITLE);

                String titleText = musicCursor.getString(music_column_index);

                songList.add(new Song(titleText, artistText));
            }
        }
    }

    /******************************************************
     * Get the artwork if available for the current song
     *
     * @param songIndex
     * @return Bitmap (an image)
     ******************************************************/
    public Bitmap getArtwork(int songIndex) {

        metaDataRetriever.setDataSource(getSongFileName(songIndex));

        art = metaDataRetriever.getEmbeddedPicture();
        if (art != null)
            return BitmapFactory.decodeByteArray(art, 0, art.length);
        else
            return null;
    }

    /******************************************************
     * Get the genre of the current song
     *
     * @param songIndex
     * @return String
     ******************************************************/

    public String getGenre(int songIndex) {
        metaDataRetriever.setDataSource(getSongFileName(songIndex));

        return metaDataRetriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);

    }

    /******************************************************
     * Get the play list
     *
     * @return ArrayList
     ******************************************************/
    public ArrayList<Song> getPlayList() {

        return songList;
    }

    /******************************************************
     * Get the file name of the current song
     *
     * @param songIndex
     * @return String
     ******************************************************/
    public String getSongFileName(int songIndex) {

        musicCursor.moveToPosition(songIndex);
        music_column_index = musicCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        return musicCursor.getString(music_column_index);
    }

    /******************************************************
     * Get the song title
     *
     * @param songIndex
     * @return String
     ******************************************************/
    public String getTitle(int songIndex) {

        musicCursor.moveToPosition(songIndex);
        music_column_index = musicCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        return musicCursor.getString(music_column_index);
    }

    /******************************************************
     * Get the artist name of the current song
     *
     * @param songIndex
     * @return String
     ******************************************************/
    public String getArtist(int songIndex) {

        musicCursor.moveToPosition(songIndex);
        music_column_index = musicCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        return musicCursor.getString(music_column_index);
    }

    /******************************************************
     * Get the album title of the current song
     *
     * @param songIndex
     * @return String
     ******************************************************/
    public String getAlbum(int songIndex) {

        musicCursor.moveToPosition(songIndex);
        music_column_index = musicCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        return musicCursor.getString(music_column_index);
    }

    /******************************************************
     * Get the playlist length
     *
     * @param
     * @return int - number of songs
     ******************************************************/
    public int getPlayListLength() {

        return musicCursor.getCount();

    }

    /******************************************************
     * Test is the list is empty
     *
     * @param
     * @return boolean
     ******************************************************/
    public boolean isListEmpty() {

        if (musicCursor.getCount() == 0)
            return true;
        else
            return false;
    }

}
