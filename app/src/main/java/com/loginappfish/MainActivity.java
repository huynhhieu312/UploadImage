package com.loginappfish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loginappfish.Adapters.TourAdapter;
import com.loginappfish.Models.Tour;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvdstourthailan;
    ArrayList<Tour> dstour;
    TourAdapter adaptertourthailan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvdstourthailan=(ListView)findViewById(R.id.lvdstourthailan);
        dstour=new ArrayList<Tour>();
      //  dstour.add(new Tour(R.drawable.thailand,"Tour du lich Thái Lan","Máy Bay","Tháng 8,9,10","5.500.000"));
        //dstour.add(new Tour(R.drawable.campuchia,"Tour du lich Thái Lan","Máy Bay","Tháng 1,2,3","8.500.000"));
        //dstour.add(new Tour(R.drawable.malaysia,"Tour du lich Thái Lan","Xe 45 chỗ đời mới","Tháng 9,10","4.500.000"));
        //dstour.add(new Tour(R.drawable.hanquoc,"Tour du lich Chợ Thái Lan","Máy Bay - Xe 45 chỗ","Tháng 8,9,10","5.500.000"));
        //dstour.add(new Tour(R.drawable.japan,"Tour du lich Thái Lan","Máy Bay","Tháng 8,9,10","5.500.000"));
        adaptertourthailan =new TourAdapter(dstour,MainActivity.this);

        lvdstourthailan.setAdapter(adaptertourthailan);
        new TourAsync(getApplication()).execute();

    }

    public class TourAsync extends AsyncTask<Void, Object,Void> {
        ProgressDialog pDialog;
        private final Context mContext;
        String responseString="";
        ArrayList<Tour> _mlist;
        public TourAsync(Context ctx) {
            this.mContext = ctx;

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                this.responseString = GetServer();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Log.d(responseString,"respon");
            try {
                JSONObject jObj = new JSONObject(responseString);
                int error = jObj.getInt("status");
                if (error == 200) {
                    String errorMsg = jObj.getString("msg");
                    JSONArray jsonarr =jObj.getJSONArray("list");
                    for(int i=0;i<jsonarr.length();i++){
                        JSONObject json = jsonarr.getJSONObject(i);
                        Tour a = new Tour(json);
                        dstour.add(a);
                    }
                    lvdstourthailan.invalidate();
                }
                else
                {
                    String errorMsg = jObj.getString("mess");
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        public String GetServer() throws IOException {
            // Create a new HttpClient and Post Header

            try {
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setSoTimeout(params, 0);
                HttpClient httpClient = new DefaultHttpClient(params);
                //HttpPost httppost = new HttpPost("http://webmarket.vn/login.php");
                HttpGet httpget = new HttpGet("http://webmarket.vn/dulich.php");
                HttpResponse response = httpClient.execute(httpget);

                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    Log.d(responseString,"jsonresponsive");
                    return responseString;
                } else {
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }catch (Exception e) {
                // TODO Auto-generated catch block
            }
            return "";
        }


    }


}
