package com.ult.android.myprojet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.ult.android.myprojet.app.MyApplication;
import com.ult.android.myprojet.helper.Happy;
import com.ult.android.myprojet.helper.SwipeListAdapter;


public class ListActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = ListActivity.class.getSimpleName();

    private String URL_HAPPY = "http://10.0.3.2/php/happy.php";


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Happy> happyList;

    // initially offset will be 0, later will be updated while parsing the json
   // private int offSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        happyList = new ArrayList<>();

        adapter = new SwipeListAdapter(this, happyList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchHappys();
                                    }
                                }
        );





    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchHappys();
    }

    /**
     * Fetching movies json by making http call
     */
    private void fetchHappys() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = URL_HAPPY;

        // Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to happy list
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject happyObj = response.getJSONObject(i);

                                    //int rank = happyObj.getInt("rank");
                                    String title = happyObj.getString("title");
                                    String aime = happyObj.getString("aime");
                                    String adr = happyObj.getString("adr");

                                    Happy m = new Happy(title,aime,adr);

                                    happyList.add(0, m);

                                    // updating offset value to highest value
                                   // if (rank >= offSet)
                                     //   offSet = rank;

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}