package com.example.android.project7_wiltontuji;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {

    private String REQUEST_URL_TEST;
    private BooksAdapter mAdapter;
    private static final int BOOKS_LOADER_ID = 1;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String searchParameter = extras.getString("searchParameter");
            String searchNumbers = extras.getString("searchNumbers");
            REQUEST_URL_TEST = "https://www.googleapis.com/books/v1/volumes?q=" + searchParameter + "&maxResults=" + searchNumbers;
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(GONE);
            mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
            mEmptyTextView.setText(R.string.no_internet);
        }

        ArrayList<Books> books = new ArrayList<Books>();
        mAdapter = new BooksAdapter(this, books);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        return new BooksLoader(this, REQUEST_URL_TEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(GONE);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        mEmptyTextView.setText(R.string.no_result);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mEmptyTextView.setVisibility(GONE);
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();
    }

}
