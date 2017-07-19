package com.example.android.project7_wiltontuji;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Adailto on 11/05/2017.
 */

public class BooksLoader extends AsyncTaskLoader <List<Books>> {

    private String mUrl;

    public BooksLoader (Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Books> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
