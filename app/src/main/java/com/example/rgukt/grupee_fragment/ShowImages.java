package com.example.rgukt.grupee_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowImages extends Fragment {

    private ImageView dogImageView,crossImage,checkImage;
    private ProgressBar progressBar;
    private String text="";
    private static final String URL_data = "https://dog.ceo/api/breeds/image/random";

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.activity_show_images, container, false);

            dogImageView = (ImageView)root.findViewById(R.id.image_dog);
            crossImage = (ImageView)root.findViewById(R.id.crossImage);
            checkImage = (ImageView)root.findViewById(R.id.checkImage);
            progressBar = (ProgressBar)root.findViewById(R.id.progressbar);

            LoadImages();

            crossImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadImages();
                }
            });

            checkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CreateData(text);

                    LoadImages();
                }
            });

            return root;
        }



    public void LoadImages() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            text = jsonObject.getString("message");
                            Picasso.with(getActivity()).load(text).into(dogImageView,
                                    new com.squareup.picasso.Callback(){

                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }

                                        @Override
                                        public void onError() {
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void CreateData(String text){

        Date objDate = new Date();
        String format = "E MMM d y hh:mm:ss";
        SimpleDateFormat objSDF = new SimpleDateFormat(format);
        String timeStamp = objSDF.format(objDate);
        final DatabaseHelper mydb = new DatabaseHelper(getActivity());
        mydb.insertData(text,timeStamp);
        mydb.close();
    }
}
