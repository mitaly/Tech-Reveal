package com.techeducation.tech_reveal;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ListView listView;
    DictAdapter adapter;
     static ArrayList<String> listSubject;
    TextView textHeading;
    ContentResolver resolver;

    void initViews(){
        listView=(ListView)findViewById(R.id.listViewSbjct);
        textHeading=(TextView)findViewById(R.id.subjectHead);
        listSubject=new ArrayList<>();
        resolver = getContentResolver();

        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/SEASRN__.ttf");
        textHeading.setTypeface(typeface);

        initListSubject();

        adapter=new DictAdapter(SubjectsActivity.this,R.layout.list_item_subjects,listSubject,0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SubjectsActivity.this,WordListActivity.class);
                intent.putExtra("keyPosition",position);
                startActivity(intent);
            }
        });
    }
    void initListSubject(){
        listSubject.add("Computer Architecture");
        listSubject.add("Computer Graphics");
        listSubject.add("Computer Networks");
        listSubject.add("Cyber Security");
        listSubject.add("Database Management System");
        listSubject.add("Digital Circuits");
        listSubject.add("Data Structure Algorithms");
        listSubject.add("Microprocessors");
        listSubject.add("Operating Systems");
        listSubject.add("Programming Languages");
        listSubject.add("Web Technologies");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        initViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_recentWords) {
            String[] projection={DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS};
            Cursor cursorRec=resolver.query(DictionaryUtil.uri_recent, null, null, null, null);
            ///If no row is present in Recent Table---Then Display message to user
            if(cursorRec.getCount()==0){
                Toast.makeText(this, "No recent words", Toast.LENGTH_SHORT).show();
            }

            ///else start Recent Activity to display the contents in the form of listview
            else{
                Intent i=new Intent(SubjectsActivity.this,RecentActivity.class);
                startActivity(i);
            }
            cursorRec.close();
        } else if (id == R.id.nav_favourites) {

            Cursor cursorFav=resolver.query(DictionaryUtil.uri_favourites, null, null, null, null);
            if(cursorFav.getCount()==0){
                Toast.makeText(this,"No favourites",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent i=new Intent(SubjectsActivity.this,FavouritesActivity.class);
                startActivity(i);
            }
            cursorFav.close();
        } else if (id == R.id.nav_help) {
            Intent intent=new Intent(SubjectsActivity.this,HelpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_rateApp) {
            Intent i = new Intent(SubjectsActivity.this, RateAppActivity.class);
            startActivity(i);

        }else if(id==R.id.nav_exit){
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
