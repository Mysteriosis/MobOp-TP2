package ch.hes_so.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private Integer currentSongIndex;


    public MediaPlayer mediaPlayer;
    public SongListManager songListManager;

    private void initFields() {
        album_art = (ImageView) findViewById(R.id.album_art);
        song_title = (TextView) findViewById(R.id.song_title);
    }

    private void initButtons() {
        playButton = (ImageButton) this.findViewById(R.id.playImageButton);
        stopButton = (ImageButton) this.findViewById(R.id.stopImageButton);
    }

    private void initNotification() {
        // ...
    }

    public void playSong(int songIndex) {
        setSongInfo(songIndex);

        try {
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(this.songListManager.getSongFileName(songIndex));
            this.mediaPlayer.prepare();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mediaPlayer.start();

        playButton.setImageResource(R.drawable.ic_pause);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lie le layout a l'activitée
        setContentView(R.layout.activity_media_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFields();
        initButtons();
        initNotification();

        // -------------------------------------------------------
        // Etape 1 : creation du media player
        // Ecrire votre code ci-dessous:
        // -------------------------------------------------------

        this.mediaPlayer = new MediaPlayer();

        // --------------------------------
        // Play continously...
        // --------------------------------
        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentSongIndex < songListManager.getPlayListLength() - 1) {
                    currentSongIndex++;
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "End of list!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                playSong(currentSongIndex);
            }
        });

        // -------------------------------------------------------
        // Etape 2 : creation du manager de la liste de chansons
        // Ecrire votre code ci-dessous:
        // -------------------------------------------------------

        songListManager = new SongListManager(this);
        setListenerToButton();

        // Manages the index coming from the playList activity
        Intent playListIntent = getIntent();
        currentSongIndex = playListIntent.getIntExtra("SONG_INDEX", 0);
        playSong(currentSongIndex);
    }

    private void setListenerToButton() {

        /******************************
         * STOP button initialisation
         *******************************/
        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewParam);

            // ------------------------------------
            // Etape 3 - Gerer le boutton stop
            // Stopper le media player
            // ------------------------------------
            mediaPlayer.stop();

            if(!stopped) {
                stopped = true;
                paused = false;
                playButton.setImageResource(R.drawable.ic_play);
            }
        }
    });
}
