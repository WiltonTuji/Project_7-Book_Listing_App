package com.example.android.project7_wiltontuji;

import static android.R.attr.author;

/**
 * Created by Adailto on 11/05/2017.
 */

public class Books {

    private String mTitle;
    private String mAuthor;

    public Books (String title, String author){
        mTitle = title;
        mAuthor = author;
    }

    public String getTitle (){
        return mTitle;
    }

    public String getmAuthor(){
        return mAuthor;
    }

}
