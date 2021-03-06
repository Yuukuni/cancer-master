package com.example.huangyuwei.myapplication.link;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class link_resource extends AppCompatActivity {

    class Center{
        String name;
        String phone;
        String address;
        String url;
        String info;
    }
    private String TAG = "link_resource";
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    public static final ArrayList<Center> wig=new ArrayList<>();
    public static final ArrayList<Center> bra=new ArrayList<>();
    public static final ArrayList<Center> cuff=new ArrayList<>();
    public static final ArrayList<Center> home=new ArrayList<>();

    private String data;

    public link_resource() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_link_resource);
        new getData(this).execute();
    }


    private void addTableRow(final Context context, TableLayout tl, final String name, final String phone, final String address, final String url, final String info){
        LayoutInflater inflater = this.getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(name);

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(phone);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(address);
        tr.setClickable(true);

        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpannableString s;
                if(url!=null && info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info + "\n\n" + url);
                else if(url!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + url);
                else if(info!=null)
                    s = new SpannableString("電話：" + phone + "\n地址：" + address + "\n\n" + info);
                else
                    s = new SpannableString("電話：" + phone + "\n地址：" + address);
                    Linkify.addLinks(s, Linkify.WEB_URLS);
                    final AlertDialog d = new AlertDialog.Builder(context)
                            .setPositiveButton(android.R.string.ok, null)
                            .setMessage( s )
                            .setTitle(name)
                            .create();
                    d.show();

                    // Make the textview clickable. Must be called after show()
                    ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
//your code...


        tl.addView(tr);
    }

    private class getData extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;
        ProgressDialog pDialog;
        private Context mcontext;

        getData(Context context){
            this.mcontext=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pDialog = new ProgressDialog(mcontext);
                    pDialog.setMessage("Loading..");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();

                }
            });

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("http://13.231.194.159/link_resource.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
//                Uri.Builder builder = new Uri.Builder()
//                        .appendQueryParameter("username", params[0])
//                        .appendQueryParameter("password", params[1]);
//                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pDialog.dismiss();
            data = result;
            JSONanalyse();


            TableLayout ll = (TableLayout) findViewById(R.id.WigTable);
            TableLayout cl = (TableLayout) findViewById(R.id.BraTable);
            TableLayout sl = (TableLayout) findViewById(R.id.CuffTable);
            TableLayout hl = (TableLayout) findViewById(R.id.HomeTable);
            for (int i = 0; i <wig.size(); i++)
                addTableRow(mcontext,ll,wig.get(i).name,wig.get(i).phone,wig.get(i).address,wig.get(i).url,wig.get(i).info);

            for (int i = 0; i <bra.size(); i++)
                addTableRow(mcontext,cl,bra.get(i).name,bra.get(i).phone,bra.get(i).address,bra.get(i).url,bra.get(i).info);

            for (int i = 0; i < cuff.size(); i++)
                addTableRow(mcontext,sl,cuff.get(i).name,cuff.get(i).phone,cuff.get(i).address,cuff.get(i).url,cuff.get(i).info);

            for (int i = 0; i < home.size(); i++)
                addTableRow(mcontext,hl,home.get(i).name,home.get(i).phone,home.get(i).address,home.get(i).url,home.get(i).info);


        }

    }


    private void JSONanalyse(){
        try{
            wig.clear();
            bra.clear();
            cuff.clear();
            home.clear();
            JSONArray jsonArray = new JSONArray(data);
            for(int k=0;k<jsonArray.length();k++) {
                //Log.i(TAG, jsonArray.getString(i));
                if (k == 0)
                    Log.i(TAG, "北區+++++++");
                else if (k == 1)
                    Log.i(TAG, "中區+++++++");
                else if (k == 2)
                    Log.i(TAG, "南區+++++++");

                JSONObject jsonObject = new JSONObject(jsonArray.getString(k));
                //Log.i(TAG, "length: "+jsonObject.toString());
                if (k == 0) {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("intro");
                        wig.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==1){
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("intro");
                        bra.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==2) {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("intro");
                        cuff.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
                else if(k==3) {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject data2 = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Center temp = new Center();
                        String number = data2.getString("number");
                        temp.name = data2.getString("unit");
                        temp.phone = data2.getString("phone");
                        temp.address = data2.getString("address");
                        temp.url = data2.getString("url");
                        temp.info = data2.getString("intro");
                        home.add(temp);
                        Log.i(TAG, "123------ >  " + number + "  " + temp.name + "  " + temp.phone + "  " + temp.address);
                    }
                }
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }

}