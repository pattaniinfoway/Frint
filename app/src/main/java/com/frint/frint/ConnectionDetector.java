package com.frint.frint;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    public static String baseUrl="http://pattaniinfotech.com/api/expressFeelings/";
	 private Context _context;
	 public ConnectionDetector(Context context){
	        this._context = context;
	    }
	 
	 public boolean isConnectingToInternet(){
		 ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
      if (connectivity != null) 
      {
          NetworkInfo[] info = connectivity.getAllNetworkInfo();
          if (info != null) 
              for (int i = 0; i < info.length; i++) 
                  if (info[i].getState() == NetworkInfo.State.CONNECTED)
                  {

                      return true;
                  }

      }
      return false;
	 }

}

/*
*  ConnectionDetector connectionDetector;
    Boolean isConnected=false;


     connectionDetector =new ConnectionDetector(MainActivity3.this);


      isConnected=connectionDetector.isConnectingToInternet();
        if(isConnected)
        {
            Toast.makeText(getApplicationContext(),"Fetching details",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity3.this,MainActivity4.class);
            intent.putExtra("name",inputName.getText().toString());
            intent.putExtra("email",inputEmail.getText().toString());
            Toast.makeText(getApplicationContext(),inputEmail.getText().toString()+" "+inputPassword.getText().toString(),Toast.LENGTH_SHORT).show();
            intent.putExtra("password",inputPassword.getText().toString());
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"Please check you internet connection",Toast.LENGTH_SHORT).show();
        }
* */
