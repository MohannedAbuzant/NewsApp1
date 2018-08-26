package com.channel.example.newsapp1;

import android.net.Uri;
import android.nfc.Tag;
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
import java.util.WeakHashMap;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public class QueryUtils {
    private  static final String TAG=QueryUtils.class.getSimpleName();
    private  QueryUtils(){}
        public static  List<News> fetchNewsData(String requstedUrl){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ie) {
                Log.e(TAG,"fetchNewsData: Interrupted",ie);
            }
            //Create URL
            URL newsUrl = createUrl(requstedUrl);
            //Perform httpRequest
            String jsonResponse= null;
            try {
                jsonResponse = makeHttpRequest(newsUrl);
            }catch (IOException ioe){
                Log.e(TAG,"fetchNewsData: Problem making HTTP request",ioe);
            }
            //Extract relevant data
            List<News> myNews = extractNewsFromJson(jsonResponse);
            return myNews;


    }

    private static List<News> extractNewsFromJson(String jsonResponse) {
        String title;
        String author;
        String date;
        String urlSource;
        //Check for JSON is null
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<News> myNews= new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(jsonResponse);
            JSONObject baseJSONResponseResult = baseJSONResponse.getJSONObject("response");
            JSONArray currentNewsArticles = baseJSONResponseResult.getJSONArray("results");
            //make items
            for (int i =0 ; i<currentNewsArticles.length();i++){
                JSONObject currentArticle =currentNewsArticles.getJSONObject(i);
                title= currentArticle.getString("webTitle");
                urlSource=currentArticle.getString("webUrl");
                date = currentArticle.getString("webpublicationDate");
                JSONArray authorArray = currentArticle.getJSONArray("tags");

            }
        }catch (JSONException je){
            Log.e(TAG,"extractNewsFromJson:Problem parsing results",je);
        }
        return myNews;

    }

    private static URL createUrl(String requestedUrl){
        URL url =null;
        try {
            url = new URL(requestedUrl);
        }catch (MalformedURLException mue){
            Log.e(TAG,"createUrl : Problem building URl",mue);
        }
        return url;
    }
    private static String makeHttpRequest(URL newsUrl)throws IOException{
        String jsonResponse = "";
        //Check for null
        if (newsUrl == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        //Create the connection
        try {
            urlConnection = (HttpURLConnection)newsUrl.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("Get");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(TAG,"makeHttpRequest : Error Code"+urlConnection.getResponseCode());
            }
        }catch(IOException ioe){
            Log.e(TAG,"makeHttpRequest: Couldnt retrieve JSON",ioe);
        }finally {
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            if (inputStream !=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
            BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();

            }

        }
        return output.toString();

    }


}

