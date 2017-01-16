package com.onepiece_eren.mysqlbaglantisiornegi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText ad,soyad,yas;
    String url_kaydet=" ";
    String url_goster=" ";

    //istek kuyrugu
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        ad = (EditText) findViewById(R.id.editText);
        soyad = (EditText) findViewById(R.id.editText2);
        yas = (EditText) findViewById(R.id.editText3);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    public void gosterClick(View view) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_goster, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray ogrenciler = response.getJSONArray("ogrenciler");

                    for(int i=0; i<ogrenciler.length(); i++){

                        JSONObject ogrenci=ogrenciler.getJSONObject(i);

                        String ad = ogrenci.getString("ad");
                        String soyad = ogrenci.getString("soyad");
                        String yas = ogrenci.getString("yas");

                        textView.append(ad+" " +soyad +" "+ yas + "\n");

                    }
                    textView.append("---------------");
                } catch (JSONException e) {
                    Log.e("Hata ",e.getLocalizedMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Hata ErrorResponse",error.getLocalizedMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
        // kuyruga eklicez en son yapılan isteği
    }

    public void kaydetClick(View view) {

        StringRequest request = new StringRequest(Request.Method.POST, url_kaydet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String, String>();

                params.put("ad",ad.getText().toString());
                params.put("soyad",soyad.getText().toString());
                params.put("yas",yas.getText().toString());

                return params;
            }
        };

        requestQueue.add(request);

    }
}
