package ch.hes_so.mediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

public class PlayListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private void init_phone_music_grid() {
        // Init songs list (Instanciate Adapter & Manager)
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    //------------
    // SURCHARGES
    //------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //--------------------------------------
        // check if the permission is granted
        //--------------------------------------

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            this.init_phone_music_grid();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    /**************************************************************************
     * Check if the permission has been granted, if not terminate the activity
     **************************************************************************/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.init_phone_music_grid();
                } else {
                    new AlertDialog.Builder(this).setTitle("Permission Error").setMessage("Permission to access your media not granted!").setNeutralButton("Close", null).show();
                    finish();
                }

                return;
            }
        }
    }

}