package com.example.player.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.player.R;
import com.example.player.fragments.AboutFragment;
import com.example.player.fragments.AlbumFragment;
import com.example.player.fragments.ArtistFragment;
import com.example.player.fragments.DeviceFragment;
import com.example.player.fragments.FeedbackFragment;
import com.example.player.fragments.HeartAnalyserFragement;
import com.example.player.fragments.HomeFragment;
import com.example.player.fragments.SearchFragment;
import com.example.player.fragments.SettingsFragement;
import com.example.player.fragments.SongFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolBarImplementation();

         requestForPermsissions();

         initComponent();

         navigationView.setNavigationItemSelectedListener(this);

         HomeFragment homeFragment=new HomeFragment();
         FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
         fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
         fragmentTransaction.add(R.id.frame_main,homeFragment);
         fragmentTransaction.commit();

    }

    private void requestForPermsissions() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(requestCode==1){
             if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                 Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
             }
         }
    }

    private void initComponent() {
        navigationView=findViewById(R.id.nav0);
        frameLayout=findViewById(R.id.frame_main);
    }

    private void toolBarImplementation() {
      Toolbar toolbar=findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

       drawerLayout=findViewById(R.id.drawerLayout);
       ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
       actionBarDrawerToggle.syncState();
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);


       SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE) ;
       searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
       searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
       searchView.setQueryHint("Search Songs");

//       search view click handled
            searchView.setOnSearchClickListener(v->{
                SearchFragment searchFragment=new SearchFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_main,searchFragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            searchView.setOnCloseListener(()->{
                getSupportFragmentManager().popBackStack();
                return false;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment=null;
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                selectedFragment=new HomeFragment();
              break;
            case R.id.nav_songs:
                selectedFragment=new SongFragment();
                break;
            case R.id.nav_artists:
                startActivity(new Intent(MainActivity.this,ArtistisActivity.class));
                break;
            case R.id.nav_albums:
                selectedFragment=new AlbumFragment();
                break;
            case R.id.nav_about:
                selectedFragment=new AboutFragment();
                break;
            case R.id.nav_heart:
                selectedFragment=new HeartAnalyserFragement();
                break;
            case R.id.nav_settings:
                selectedFragment=new SettingsFragement();
                break;
            case R.id.nav_feedback:
                selectedFragment=new FeedbackFragment();
                break;
            case R.id.nav_device:
                selectedFragment=new DeviceFragment();
                break;
        }
         if(selectedFragment!=null){
             FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.frame_main,selectedFragment);
             fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();
         }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
