package com.techeducation.tech_reveal;

import android.net.Uri;

public class DictionaryUtil {

    public static final int DB_VERSION=1;
    public static String DB_PATH = "/data/data/com.techeducation.tech_reveal/databases/";
    public static String DB_NAME_WORDS = "SubjectsDatabase.db";

    public static final String TABLE_CA="Computer_Arch";
    public static Uri uri_ca=Uri.parse("content://com.techeducation.wordscp/"+TABLE_CA);

    public static final String TABLE_CG="Computer_Graphics";
    public static Uri uri_cg=Uri.parse("content://com.techeducation.wordscp/"+TABLE_CG);

    public static final String TABLE_CN="Computer_Network";
    public static Uri uri_cn=Uri.parse("content://com.techeducation.wordscp/"+TABLE_CN);

    public static final String TABLE_DBMS="DBMS";
    public static Uri uri_dbms=Uri.parse("content://com.techeducation.wordscp/"+TABLE_DBMS);

    public static final String TABLE_DCLD="DCLD";
    public static Uri uri_dcld=Uri.parse("content://com.techeducation.wordscp/"+TABLE_DCLD);

    public static final String TABLE_PL="Programming_Languages";
    public static Uri uri_pl=Uri.parse("content://com.techeducation.wordscp/"+TABLE_PL);

    public static final String TABLE_WT="Web_design";
    public static Uri uri_wt=Uri.parse("content://com.techeducation.wordscp/"+TABLE_WT);

    public static final String TABLE_DSA="DSA";
    public static Uri uri_dsa=Uri.parse("content://com.techeducation.wordscp/"+TABLE_DSA);

    public static final String TABLE_OS="Operating_System";
    public static Uri uri_os=Uri.parse("content://com.techeducation.wordscp/"+TABLE_OS);

    public static final String TABLE_MAP="MALP";
    public static Uri uri_map=Uri.parse("content://com.techeducation.wordscp/"+TABLE_MAP);

    public static final String TABLE_CS="Cyber_Security";
    public static Uri uri_cs=Uri.parse("content://com.techeducation.wordscp/"+TABLE_CS);

    public static final String COL_ID="_ID";
    public static final String COL_WORDS="WORD";
    public static final String COL_MEANING="MEANING";
    public static final String COL_URI="URI";

    public static final String DB_NAME="RecFavDB.db";

    public static final String TABLE_NAME_RECENT="RecentWords";
    public static final String TABLE_NAME_FAVOURITES="Favourites";


    public static final String CREATE_TABLE_QUERY_RECENT="create table RecentWords("+
            "_ID integer primary key autoincrement,"+
            "WORD text,"+
            "URI text"+
            ")";

    public static final String CREATE_TABLE_QUERY_FAVOURITES="create table Favourites("+
            "_ID integer primary key autoincrement,"+
            "WORD text,"+
            "URI text"+
            ")";

    public static final Uri uri_recent=Uri.parse("content://com.techeducation.recfavcp/"+TABLE_NAME_RECENT);
    public static final Uri uri_favourites=Uri.parse("content://com.techeducation.recfavcp/"+TABLE_NAME_FAVOURITES);

    public static final int recentWordsMax=15;
    public static final int favWordsMax=15;
}
