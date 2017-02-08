package com.techeducation.tech_reveal;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class WordMeaningActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static ViewPager mViewPager;
    static String uriString;
    Uri uri;
    int position;
    String[] projection;
    static ContentResolver resolver;
    ArrayList<String> list;
    String title;

    void initViews(){
        resolver=getContentResolver();
        Intent rcv=getIntent();

        uriString=rcv.getStringExtra("keyUri");
        uri=Uri.parse(uriString);
        if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_CA)){
            title="Computer Architecture";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_CG)){
            title="Computer Graphics";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_CN)){
            title="Computer Networks";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_CS)){
            title="Cyber Security";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_DBMS)){
            title="Database Management System";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_DCLD)){
            title="Digital Circuits";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_DSA)){
            title="Data Structure Algorithms";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_MAP)){
            title="Microprocessors";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_OS)){
            title="Operating Systems";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_PL)){
            title="Programming Languages";
        }else if(uriString.equals("content://com.techeducation.wordscp/"+DictionaryUtil.TABLE_WT)){
            title="Web Technologies";
        }
        list=new ArrayList<>();

        projection= new String[]{DictionaryUtil.COL_WORDS};
        Cursor cursor=resolver.query(uri,projection,null,null,null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_WORDS)));
        }
        cursor.close();
        position=list.indexOf(rcv.getStringExtra("keyWord"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning);

        initViews();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String word = list.get(position);
                insertToRecent(word);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);
    }

    String getMeaning(String word){

        projection=new String[]{DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS,DictionaryUtil.COL_MEANING};
        Cursor cursor = resolver.query(uri, projection, DictionaryUtil.COL_WORDS + "='" + word + "'", null, null);
        cursor.moveToNext();
        String meaning=cursor.getString(cursor.getColumnIndex(DictionaryUtil.COL_MEANING));
        cursor.close();

        return meaning;
    }

    boolean checkIfFav(String word){
        boolean on=false;
        projection= new String[]{DictionaryUtil.COL_ID, DictionaryUtil.COL_WORDS};

        Cursor cursorCheckFav=resolver.query(DictionaryUtil.uri_favourites,projection,null,null,null);
        String favWord;
        if(cursorCheckFav.getCount()>0){
            while(cursorCheckFav.moveToNext()){
                favWord=cursorCheckFav.getString(cursorCheckFav.getColumnIndex(DictionaryUtil.COL_WORDS));
                if(favWord.equals(word)){
                    on=true;
                    break;
                }
            }
        }
        cursorCheckFav.close();
        return on;
    }

    void insertToRecent(String word){
        projection=new String[]{DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS,DictionaryUtil.COL_URI};
        Cursor cursorNew=resolver.query(DictionaryUtil.uri_recent, projection, null, null, null);

        if(cursorNew.getCount()>0){
            while(cursorNew.moveToNext()){
                if(cursorNew.getString(cursorNew.getColumnIndex(DictionaryUtil.COL_WORDS)).equals(word)){
                    ///if word searched already present in RECENT ,then delete it
                    resolver.delete(DictionaryUtil.uri_recent,DictionaryUtil.COL_ID+" = "+cursorNew.getInt(cursorNew.getColumnIndex(DictionaryUtil.COL_ID)),null);
                    break;
                }
            }
        }
        cursorNew.close();

        Cursor cursor = resolver.query(DictionaryUtil.uri_recent, projection, null, null, null);
        ///delete last row:with least id
        if (cursor.getCount() == DictionaryUtil.recentWordsMax) {
            cursor.moveToLast();
            resolver.delete(DictionaryUtil.uri_recent,DictionaryUtil.COL_ID+"="+cursor.getInt(cursor.getColumnIndex(DictionaryUtil.COL_ID)),null);
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DictionaryUtil.COL_WORDS, word);
        values.put(DictionaryUtil.COL_URI, uriString);
        resolver.insert(DictionaryUtil.uri_recent, values);
    }

    static boolean onClickFavourites(View view,boolean on,String word) {
        String[] projection;

        projection=new String[]{DictionaryUtil.COL_ID,DictionaryUtil.COL_WORDS,DictionaryUtil.COL_URI};

        if (on) {
            ///Word marked as favourite is unMarked: therefore deleted from FAVOURITES
            resolver.delete(DictionaryUtil.uri_favourites,DictionaryUtil.COL_WORDS+"='"+word+"'",null);
            on = false;
            Snackbar.make(view, "Removed from Favourites", Snackbar.LENGTH_SHORT).show();
        } else {
            Cursor cursorFavInsert = resolver.query(DictionaryUtil.uri_favourites, projection, null, null, null);
            if (cursorFavInsert.getCount() == DictionaryUtil.favWordsMax) {
                cursorFavInsert.moveToLast();
                ///Fav word with least id is deleted:::: the one which is the lat row
                resolver.delete(DictionaryUtil.uri_favourites, DictionaryUtil.COL_ID + "=" + cursorFavInsert.getInt(0), null);
            }
            cursorFavInsert.close();

            ContentValues values = new ContentValues();
            values.put(DictionaryUtil.COL_WORDS, word);
            values.put(DictionaryUtil.COL_URI,uriString);
            resolver.insert(DictionaryUtil.uri_favourites, values);

            on = true;
            Snackbar.make(view, "Added to Favourites", Snackbar.LENGTH_SHORT).show();
        }
        return on;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment{

        TextView txtMeaning,txtHeading;
        String word,meaning;
        Button btnfav,btnshare;
        boolean on;

        public PlaceholderFragment() {
        }

        public  PlaceholderFragment newInstance(String w,String m, boolean bool) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("word", w);
            args.putString("meaning", m);
            args.putBoolean("fabState", bool);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            word=getArguments().getString("word");
            on=getArguments().getBoolean("fabState");
            meaning=getArguments().getString("meaning");

            View rootView = inflater.inflate(R.layout.fragment_word_meaning, container, false);

            txtMeaning = (TextView) rootView.findViewById(R.id.txtMeaning);
            txtHeading = (TextView)rootView.findViewById(R.id.textHeading);
            btnfav = (Button)rootView.findViewById(R.id.fav);
            btnshare = (Button)rootView.findViewById(R.id.share);

            txtHeading.setText(word);
            txtMeaning.setText(meaning);

            Typeface typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/Prime Regular.ttf");
            txtHeading.setTypeface(typeface);
            txtMeaning.setTypeface(typeface);

            if(on){
                btnfav.setBackgroundResource(R.drawable.star_on);
            }
            else{
                btnfav.setBackgroundResource(R.drawable.star_border);
            }


            btnfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickFavourites(v, on, word)) {
                        btnfav.setBackgroundResource(R.drawable.star_on);
                        on = true;
                    } else {
                        btnfav.setBackgroundResource(R.drawable.star_border);
                        on = false;
                    }
                }
            });

            btnshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    waIntent.putExtra(Intent.EXTRA_TEXT, word + " - \n" + meaning);
                    startActivity(Intent.createChooser(waIntent, "Share with"));


                }
            });

            return rootView;
        }

    }
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String word=list.get(position);
            String m=getMeaning(word);
            boolean on=checkIfFav(word);

            return new PlaceholderFragment().newInstance(word, m, on);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
