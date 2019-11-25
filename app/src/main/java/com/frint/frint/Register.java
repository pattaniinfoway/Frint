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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.frint.frint.Parser.JsonParser;
import com.frint.frint.Service.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText field_mob;
    private EditText field_name;
    private EditText field_email;
    public ConnectionDetector connectionDetector;
    public Boolean isConnected=false;
    UserSessionManager userSessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        field_mob=(EditText)findViewById(R.id.register_mob);
        String sourceString = "MOBILE NUMBER (" +"<i>" + "without country code" + "</i>"+")" ;
        field_mob.setHint(Html.fromHtml(sourceString));
        field_name=(EditText)findViewById(R.id.register_name);
        field_email=(EditText)findViewById(R.id.register_email);

        connectionDetector =new ConnectionDetector(getApplicationContext());
        userSessionManager=new UserSessionManager(Register.this);
        HashMap<String,String> hashMap= userSessionManager.getUserDetails();
        if (hashMap.get(UserSessionManager.KEY_userId)!=null)
        {Toast.makeText(getApplicationContext(),hashMap.get(UserSessionManager.KEY_userId).toString()+"",Toast.LENGTH_LONG).show();

            Log.e("sa", hashMap.get(UserSessionManager.KEY_userId).toString());}
        else {
            Log.e("sa","Hi");
        }

    }
    public void onClick_register(View view){


        isConnected=connectionDetector.isConnectingToInternet();
        if(field_name.getText().toString().length()<=0)
        {
            field_name.setError("Enter name");
        }else if(field_mob.getText().toString().length()<10 || field_mob.getText().toString().length()>10)
        {
            field_mob.setError("mobile number must be 10 digits long");
        }else if(field_email.getText().toString().length()<=0)
        {
            field_email.setError("email");
        }else {
            if(isConnected)
            {
                new GetLogin().execute( field_name.getText().toString(),field_mob.getText().toString(),field_email.getText().toString());
            }else {

            }
        }

        /*if (isConnected) {
            Log.i(abc,"  1true ................");

        }else {
        Toast.makeText(getApplicationContext(), "Please check you internet connection", Toast.LENGTH_SHORT).show();*/
    }

    class GetLogin extends AsyncTask<String,String,String>
    {
        boolean istrue=false;
ProgressDialog progressDialog;
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Register.this);
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
//                        Toast.makeText(getApplicationContext(),"data code not equal to 4000",Toast.LENGTH_SHORT);
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
                Intent intent= new Intent(Register.this, Verify.class);
                intent.putExtra("name", field_name.getText().toString());
                intent.putExtra("email", field_email.getText().toString());
                intent.putExtra("mobile", field_mob.getText().toString());
                intent.putExtra("datacodeSent",WebService.datacode.toString());
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            }
        }
    }


}
