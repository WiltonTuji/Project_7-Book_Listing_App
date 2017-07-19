package com.example.android.project7_wiltontuji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by Adailto on 11/05/2017.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    public BooksAdapter(Context context, ArrayList<Books> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Books books = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        TextView titleTV = (TextView) convertView.findViewById(R.id.title_text_view);
        TextView authorTV = (TextView) convertView.findViewById(R.id.author_text_view);
        titleTV.setText(getContext().getResources().getString(R.string.title) + ": " + books.getTitle());
        authorTV.setText(getContext().getResources().getString(R.string.authors) + ": " + books.getmAuthor());
        return convertView;
    }
}
