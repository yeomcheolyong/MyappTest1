package com.example.a302.mytestapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class jsonactivity extends AppCompatActivity {
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_email = "email";
    private static final String TAG_NAME = "name";
    private static final String TAG_carnum = "carnum";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsonlayout);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://203.250.137.165/json.php"); //수정 필요
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String email = c.getString(TAG_email);
                String name = c.getString(TAG_NAME);
                String carnum = c.getString(TAG_carnum);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_email, email);
                persons.put(TAG_NAME, name);
                persons.put(TAG_carnum, carnum);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    jsonactivity.this, personList, R.layout.list_item,
                    new String[]{TAG_email, TAG_NAME, TAG_carnum},
                    new int[]{R.id.email, R.id.name, R.id.carnum}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
