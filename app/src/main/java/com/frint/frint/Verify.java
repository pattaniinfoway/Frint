package com.frint.frint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.frint.frint.Parser.JsonParser;
import com.frint.frint.Service.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class Verify extends AppCompatActivity {

    private EditText field_mob;

    public ConnectionDetector connectionDetector;
    public Boolean isConnected=false;
    private EditText field_code;
    String register_name, register_email,register_code;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_verify);
        connectionDetector =new ConnectionDetector(getApplicationContext());
        userSessionManager=new UserSessionManager(Verify.this);

        field_code=(EditText)findViewById(R.id.verify_otp);
        field_mob=(EditText)findViewById(R.id.verify_mob);
        String sourceString = "MOBILE NUMBER (" +"<i>" + "without country code" + "</i>"+")" ;
        field_mob.setHint(Html.fromHtml(sourceString));

       /* register_name=getIntent().getStringExtra("name").toString();
        register_email=getIntent().getStringExtra("email").toString();
        register_code=getIntent().getStringExtra("datacodeVerify").toString();*/


    }
    public void onClick_verify(View view){



        isConnected=connectionDetector.isConnectingToInternet();
         if(!(field_code.getText().toString().equals(WebService.datacode)))
        {
            Log.e("datacode",WebService.datacode);
            Log.e("datacode",field_code.getText().toString());
            field_code.setError("OTP");
        }else {
            if(isConnected)
            {


                new GetVerify().execute(getIntent().getStringExtra("name").toString(),getIntent().getStringExtra("mobile").toString(),getIntent().getStringExtra("email").toString());
                //Intent intent =new Intent(this,Welcome.class);
                //startActivity(intent);
            }else {

            }
        }

    }
    class GetVerify extends AsyncTask<String, String, String>
    {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Verify.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        boolean istrue=false;
        @Override
        protected String doInBackground(String... params) {
            String param="name="+params[0]+"&mobile="+params[1]+"&email="+ params[2];
            String res= JsonParser.GetJsonFromURL(param, WebService.verify);
            Log.e("respone_verify", res);
            if(res != null) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                    JSONObject obj1 = jsonObject.getJSONObject("data");
                    userSessionManager.createUserLoginSession(params[0],obj1.getString("userId"),params[2],params[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


                    return null;}

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Intent intent = new Intent(Verify.this,Welcome.class);
            startActivity(intent);
            finish();
        }
    }
    public void onclick_Reset(View view){
        if (connectionDetector.isConnectingToInternet()){

            new GetLogin().execute( getIntent().getStringExtra("name").toString(),getIntent().getStringExtra("mobile").toString(),getIntent().getStringExtra("email").toString());

        }else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }


    }


    class GetLogin extends AsyncTask<String,String,String>
    {
        boolean istrue=false;
        ProgressDialog progressDialog;
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Verify.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }
        @Override
        protected String doInBackground(String... params) {
            String param="name="+params[0]+"&mobile="+params[1]+"&email="+ params[2];
            String res= JsonParser.GetJsonFromURL(param, WebService.login);
            Log.e("respone",res);
            if(res != null) {
                try {
                    JSONObject jsonObject= new JSONObject(res);
                    JSONObject obj1=jsonObject.getJSONObject("data");
                    if(obj1.getString("data_code").equals("4000"))
                    {
                        WebService.datacode=obj1.getString("code");
                        istrue=true;
                    }else {
                        istrue=false;
                        //Toast.makeText(getApplicationContext(),"data code not equal to 4000",Toast.LENGTH_SHORT);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(istrue)
            {

            }else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
