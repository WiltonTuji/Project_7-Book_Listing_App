package com.example.android.project7_wiltontuji;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.author;
import static android.view.View.Z;

/**
 * Created by Adailto on 11/05/2017.
 */

public class QueryUtils {

    private QueryUtils() {
    }

    public static List<Books> fetchBookData (String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("fetchBookData", "Problem making the HTTP request.");
        }
        List<Books> books = extractFromJson(jsonResponse);
        return books;
    }

    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e("createURL", "Problem building the URL ");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("makeHTTPRequest", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("makeHTTPRequest", "Problem retrieving the earthquake JSON results.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Books> extractFromJson (String booksJSON) {
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }
        List<Books> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(booksJSON);
            JSONArray itensArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < itensArray.length(); i++) {
                JSONObject currentBook = itensArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                String authorSpecialCharacters = authorArray.toString();
                String authorStep1 = authorSpecialCharacters.replaceAll("[\\[\\]\"]","");
                String author = authorStep1.replaceAll(",",", ");
                Books book = new Books(title, author);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return books;
    }
}
