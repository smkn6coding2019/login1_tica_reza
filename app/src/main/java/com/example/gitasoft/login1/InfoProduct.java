package com.example.gitasoft.login1;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoProduct extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataProduct> ProductList = new ArrayList<DataProduct>();

    private static final String TAG = InfoProduct.class.getSimpleName();
    private static String url_list = Server.URL + "info_product.php?offset=";

    private int offSet = 0;

    int no;

    ProductAdapter adapter;

    Handler handler;
    Runnable runnable;

    public final static String TAG_NO = "no";
    public final static String TAG_ID = "id";
    public final static String TAG_TITLE = "title";
    public final static String TAG_SHORTDESC = "shortdesc";
    public final static String TAG_RATING = "rating";
    public final static String TAG_PRICE = "price";
    public final static String TAG_IMAGE = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rumah);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list_rumah);
        ProductList.clear();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InfoProduct.this, DeskripsiProduct.class);
                intent.putExtra(TAG_ID, ProductList.get(position).getId());
                startActivity(intent);
            }
        });

        adapter = new ProductAdapter(this, ProductList);
        list.setAdapter(adapter);
        swipe.setOnRefreshListener( this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           ProductList.clear();
                           adapter.notifyDataSetChanged();
                           callRumah(0);
                       }
                   }
        );
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    swipe.setRefreshing(true);
                    handler = new Handler();

                    runnable = new Runnable() {
                        public void run() {
                            callRumah(offSet);
                        }
                    };

                    handler.postDelayed(runnable, 3000);
                }
            }

        });



    }
    private void callRumah(int page){
        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        if (response.length() > 0) {
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    DataProduct rumah = new DataProduct();
                                    no = obj.getInt(TAG_NO);

                                    rumah.setId(obj.getString(TAG_ID));
                                    rumah.setTitle(obj.getString(TAG_TITLE));
                                    rumah.setShortdesc(obj.getString(TAG_SHORTDESC));
                                    rumah.setRating(obj.getString(TAG_RATING));
                                    rumah.setPrice(obj.getString(TAG_PRICE));

                                    if (obj.getString(TAG_IMAGE) != "") {
                                        rumah.setImage(obj.getString(TAG_IMAGE));
                                    }


                                    // adding news to news array
                                    ProductList.add(rumah);


                                    if (no > offSet)
                                        offSet = no;

                                    Log.e(TAG, "offSet " + offSet);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }
                        }
                        swipe.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrReq);
    }
    @Override
    public void onRefresh() {
        ProductList.clear();
        adapter.notifyDataSetChanged();
        callRumah(0);
    }


}


