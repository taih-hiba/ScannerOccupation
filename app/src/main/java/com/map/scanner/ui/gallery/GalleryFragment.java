package com.map.scanner.ui.gallery;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.map.scanner.R;
import com.map.scanner.model.Creneau;
import com.map.scanner.model.Occupation;
import com.map.scanner.model.Salle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private CodeScanner mCodeScanner;
    //private TextView iid;
    private String iid;
    private TextView cre;
    private TextView sal;
    private TextView blc;
    private Button btn;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;

    String addOcup ="https://glacial-caverns-30906.herokuapp.com/occupation/";
    String urlSalles ="https://glacial-caverns-30906.herokuapp.com/salles/";
    String cree;
    String idCreneau;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue2 = Volley.newRequestQueue(getActivity().getApplicationContext());

        cre =  root.findViewById(R.id.textView);
        sal =  root.findViewById(R.id.textView3);
        blc =  root.findViewById(R.id.textView5);
        btn = root.findViewById(R.id.button2);
        btn.setVisibility(View.INVISIBLE);

        LocalTime time = LocalTime.now();
        String t = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        Date d4 = null;
        Date d5 = null;
        Date d6 = null;
        Date d7 = null;


        try {
            d1 = sdf.parse(t);
            d2 = sdf.parse("08:00");
            d3 = sdf.parse("10:00");
            d4 = sdf.parse("12:00");
            d5 = sdf.parse("13:00");
            d6 = sdf.parse("15:00");
            d7 = sdf.parse("23:00");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (d1.getTime()<d2.getTime()){
            cre.setText("Hors heur d'occupation");
        }else if(d1.getTime()>=d2.getTime() && d1.getTime()<d3.getTime()){
            cre.setText("08:00 - 10:00");
            cree="Créneau 1";
            idCreneau="61ccb00c1a49052898d3bee8";
            btn.setVisibility(View.VISIBLE);
        }else if(d1.getTime()>=d3.getTime() && d1.getTime()<d4.getTime()){
            cre.setText("10:00 - 12:00");
            cree="Créneau 2";
            idCreneau="61ccb0321a49052898d3beeb";
            btn.setVisibility(View.VISIBLE);
        }else if(d1.getTime()>=d5.getTime() && d1.getTime()<d6.getTime()){
            cre.setText("13:00 - 15:30");
            cree="Créneau 3";
            idCreneau="61ccb04a1a49052898d3beee";
            btn.setVisibility(View.VISIBLE);
        }else if(d1.getTime()>=d6.getTime() && d1.getTime()<d7.getTime()){
            cre.setText("15:00 - 23:00");
            cree="Créneau 4";
            idCreneau="61ccb09d1a49052898d3bef1";
            btn.setVisibility(View.VISIBLE);
        }else{
            cre.setText("Hors heur d'occupation");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "OCCUPATION AJOUTEE", Toast.LENGTH_SHORT).show();
                try {
                    envoiDonner(iid,idCreneau);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });










        //TextView idd =  root.findViewById(R.id.textView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                         //idd.setText(result.getText());
                        //envoiDonner(result.getText(),idCreneau);
                        iid = result.getText();
                        Log.d("TAG", "run: "+result.getText());
                        getNames(result.getText());

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void envoiDonner(String idSalle,String idCreneau) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("salle", idSalle);
        json.put("creneau", idCreneau);
        Log.d("rrrr", "envoiDonner: "+json);
        JsonObjectRequest req = new JsonObjectRequest(addOcup, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.e("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();

                VolleyLog.e("Error1: ", error.getMessage());
            }
        });
        requestQueue.add(req);
    }

    private void getNames(String idS) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, urlSalles+idS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: "+response);
                try {
                    String a = response.getString("sal");
                    String b = response.getString("blc");
                    sal.setText(a);
                   blc.setText(b);
                    Log.d("TAG", "onResponse: "+a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.networkResponse);

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}