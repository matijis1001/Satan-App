package com.segiempat.segiempatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url = "https://blossom-hell.glitch.me/pesanandroid";

    private RelativeLayout loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] grupid = {"Cf5da55a616fe0bdae88a2153f66ad5cd", "C317957a351f6840e522eb6b38ed19c57", "C9ae56d60bba24b2412d0b5cd7d0d65cc", "Mazda"};
        final Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Daiakuma Kazoku", "Line OC", "Illuminafish"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        final RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
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
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url+"?pesan="+replace(pesan)+"&idgrup="+aidigrup,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    loading.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this,response,
                                            Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error

                                }
                            }
                    ) ;
                    queue.add(postRequest);

                }
                pesans.setText("");
            }
        });


    }

    public String replace(String str) {
        return str.replaceAll(" ", "%20");
    }




}