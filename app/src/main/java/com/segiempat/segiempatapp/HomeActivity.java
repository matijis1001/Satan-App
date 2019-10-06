package com.segiempat.segiempatapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String url = "https://blossom-hell.glitch.me/pesanandroid";

    private RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        AppBarLayout navbar = (AppBarLayout) findViewById(R.id.navbarhome);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navbar.bringToFront();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final String[] grupid = {"Cf5da55a616fe0bdae88a2153f66ad5cd", "C317957a351f6840e522eb6b38ed19c57", "C9ae56d60bba24b2412d0b5cd7d0d65cc", "Mazda"};
        final Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Daiakuma Kazoku", "Line OC", "Illuminafish"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        final RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        Button angryButton = (Button) findViewById(R.id.button);
        final EditText pesans = (EditText) findViewById(R.id.pesan);


        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loading = (RelativeLayout)findViewById(R.id.layoutloading);
                String aidigrup = grupid[dropdown.getSelectedItemPosition()];
                String pesan = pesans.getText().toString();

                if (TextUtils.isEmpty(pesan)){
                    pesans.setError("Isi lok bodo!");
                }else {

                    loading.setVisibility(View.VISIBLE);
                    Map<String, String> params = new HashMap<String, String>();
                    Log.d("wew", pesan);
                        params.put("pesan", replace(pesan));
                    params.put("idgrup", aidigrup);

                    JSONObject jsonPesan = new  JSONObject(params);
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,jsonPesan,
                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // response
                                    loading.setVisibility(View.GONE);

                                    try {
                                        Toast.makeText(HomeActivity.this,(String) response.get("status"),
                                                Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loading.setVisibility(View.GONE);
                                    Toast.makeText(HomeActivity.this,error.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                    ) ;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(postRequest);

                }
                pesans.setText("");
            }
        });
    }

    public String replace(String str) {
        return str.replaceAll(" ", "%20");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
