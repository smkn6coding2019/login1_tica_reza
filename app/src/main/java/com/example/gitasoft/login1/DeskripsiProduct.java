package com.example.gitasoft.login1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeskripsiProduct extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NetworkImageView thumb_image;
    private static final String TAG = DeskripsiProduct.class.getSimpleName();

    TextView Tvtitle, Tvshortdesc, Tvwrating,TVprice;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SwipeRefreshLayout swipe;

    String id_rumah, title, images,shortdesc, rating, price;

    Button Call,beli;
    ProgressDialog pDialog;
    public final static String TAG_NO = "no";
    public final static String TAG_ID = "id";
    public final static String TAG_TITLE = "title";
    public final static String TAG_SHORTDESC = "shortdesc";
    public final static String TAG_RATING = "rating";
    public final static String TAG_PRICE = "price";
    public final static String TAG_IMAGE = "image";
    private static final String url_detail = Server.URL + "detail_product.php";
    String tag_json_obj = "json_obj_req";
    SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_rumah);
        thumb_image = (NetworkImageView) findViewById(R.id.DescFoto);
        Tvtitle = (TextView) findViewById(R.id.DescTitle);
        Tvshortdesc = (TextView) findViewById(R.id.DescShortdesc);
        Tvwrating = (TextView) findViewById(R.id.DescRating);
        TVprice = (TextView) findViewById(R.id.DescPrice);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        id_rumah = getIntent().getStringExtra(TAG_ID);

        beli = (Button) findViewById(R.id.Beli);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeskripsiProduct.this, sukses.class);
                startActivity(intent);
            }
        });
        Call = (Button) findViewById(R.id.btnCall);

        //intent ke tlpn
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+628982105101";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });


        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           if (!id_rumah.isEmpty()) {
                               callDetailRumah(id_rumah);
                           }
                       }
                   }
        );
    }


    private void callDetailRumah(final String id) {
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);
                    id_rumah = obj.getString(TAG_ID);
                    title = obj.getString(TAG_TITLE);
                    images = obj.getString(TAG_IMAGE);
                    shortdesc = obj.getString(TAG_SHORTDESC);
                    rating = obj.getString(TAG_RATING);
                    price = obj.getString(TAG_PRICE);



                    Tvtitle.setText(title);
                    Tvshortdesc.setText(shortdesc);
                    Tvwrating.setText(rating);
                    TVprice.setText(price);
                    if (obj.getString(TAG_IMAGE) != "") {
                        thumb_image.setImageUrl(images, imageLoader);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail product Error: " + error.getMessage());
                Toast.makeText(DeskripsiProduct.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onRefresh() {
        callDetailRumah(id_rumah);
    }
}
