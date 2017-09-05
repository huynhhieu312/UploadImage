package com.loginappfish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputUser;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = inputUser.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(!user.isEmpty() && !password.isEmpty()){
                  new LoginAsync(getApplication(),user,password).execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Nhap User password",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public class LoginAsync extends AsyncTask<Void, Object,Void> {
        ProgressDialog pDialog;
        private final Context mContext;
        String user ="";
        String pass="";
        String responseString="";
        public LoginAsync(Context ctx, String user, String password) {
            this.mContext = ctx;
            this.user = user;
            this.pass = password;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                this.responseString = postLogin(this.user, this.pass);
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
                    String errorMsg = jObj.getString("mess");
                    Intent intent=new Intent(LoginActivity.this,CameraActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();                }
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

        public String postLogin(String user,String password) throws IOException {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://webmarket.vn/login.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", user));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
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


    void findViewById() {
        inputUser = (EditText) findViewById(R.id.user);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
}
