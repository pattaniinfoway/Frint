package com.frint.frint;

import java.util.HashMap;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class UserSessionManager {
	
	 // Shared Preferences reference
    SharedPreferences pref;
     
    // Editor reference for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREFER_NAME = "Frint";
     
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
 // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_userId = "userId";
    public static final String KEY_mobile = "mobile";
    public static final String KEY_mail = "mail";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
  //Create login session
    public void createUserLoginSession(String name, String userId,String email,String mob){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_userId,userId);
        // Storing email in pref
        editor.putString(KEY_mail,email);
        editor.putString(KEY_mobile,mob);



        // commit changes
        editor.commit();
    } 
    
 /*   public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){
             
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            String flag="false";
            if(_context instanceof Chat)
            {
            	flag="chat";
            }else if(_context instanceof Ride)
            {
            	flag="Ride";
            }else if (_context instanceof RespndedList) {
            	flag="RespndedList";
			}
            i.putExtra("flag",flag);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            _context.startActivity(i);
             
            return true;
        }
        return false;
    }*/
    
    public HashMap<String, String> getUserDetails(){
        
        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
         
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_mail, pref.getString(KEY_mail, null));
        // user email id
        user.put(KEY_mobile, pref.getString(KEY_mobile, null));
        user.put(KEY_mobile,pref.getString(KEY_mobile, null));

        user.put(KEY_userId,pref.getString(KEY_userId, null));
        // return user
        return user;
    }
   /* public void logoutUser(){
        
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
         
        
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Login.class);
         
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        // Staring Login Activity
        _context.startActivity(i);
    }*/
     
     
    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
