package com.example.android.project7_wiltontuji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.duration;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.widget.Toast.LENGTH_SHORT;

public class SearchActivity extends AppCompatActivity {

    private String searchUserParameters = null;
    public String searchApiParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button button = (Button) findViewById(R.id.search_button);
        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Integer[] items = new Integer[]{1,5,10,15,20};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserParameters = searchEditText.getText().toString();
                Log.v("searchUserParameter", searchUserParameters);
                if(searchUserParameters.equals("")) {
                    Toast.makeText(SearchActivity.this, R.string.nosearchparam , Toast.LENGTH_LONG).show();
                } else {
                    searchApiParameter = searchUserParameters.replaceAll(" ", "+");
                    String searchNumbers = spinner.getSelectedItem().toString();
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    intent.putExtra("searchParameter", searchApiParameter);
                    intent.putExtra("searchNumbers", searchNumbers);
                    startActivity(intent);
                }
            }
        });
    }
}
