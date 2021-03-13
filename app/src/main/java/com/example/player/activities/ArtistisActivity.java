package com.example.player.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.example.player.R;
import com.example.player.adapters.ArtistAdapter;
import com.example.player.adapters.SongsAdapter;
import com.example.player.indexbar.IndexBarRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ArtistisActivity extends AppCompatActivity {

    private LinearLayoutManager mArtistsLayoutManager, mAlbumsLayoutManager, mSongsLayoutManager;
    private int mAccent;
    private boolean sThemeInverted;
    private boolean sSearchBarVisible;
    private IndexBarRecyclerView mArtistsRecyclerView;
    private RecyclerView mAlbumsRecyclerView, mSongsRecyclerView;
    private ArtistAdapter mArtistsAdapter;
    private SongsAdapter mSongsAdapter;
    private TextView mPlayingAlbum, mPlayingSong, mDuration, mSongPosition, mSelectedDiscographyArtist, mSelectedArtistDiscCount, mSelectedDiscographyDisc, mSelectedDiscographyDiscYear;
    private SeekBar mSeekBarAudio;
    private LinearLayout mControlsContainer;
    private BottomSheetBehavior mBottomSheetBehaviour;
    private View mPlayerInfoView, mArtistDetails;
    private ImageView mPlayPauseButton, mSkipPrevButton;
    private boolean mUserIsSeeking = false;
    private String mNavigationArtist;
    private boolean sExpandArtistDiscography = false;
    private boolean sPlayerInfoLongPressed = false;
    private boolean sArtistDiscographyDiscLongPressed = false;
    private boolean sArtistDiscographyExpanded = false;
    private boolean sBound;
    private Parcelable mSavedArtistRecyclerLayoutState;
    private Parcelable mSavedAlbumsRecyclerLayoutState;
    private Parcelable mSavedSongRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistis);

    }
}
