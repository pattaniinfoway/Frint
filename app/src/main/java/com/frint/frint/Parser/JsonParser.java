package com.frint.frint.Parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Microsoft on 14/ 03/ 2016.
 */
public class JsonParser {
    static URL url;
    static HttpURLConnection httpURLConnection;


    static URLConnection conn;

    static String status;
    public static String GetJsonFromURL(String data,String weburl) {
        BufferedReader reader=null;
        try {

            url=new URL(weburl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
            status = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  status;
    }




/*

    public static String Get(String login,String param )
    {
        String status = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader br;

        try {
            url=new URL(login);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
            wr.write(param);
            wr.flush();
            httpURLConnection.getResponseCode();
            Log.e("response",httpURLConnection.getResponseCode()+" ");

            br=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            Log.e("tesstst", sb.toString());
            status=sb.toString();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
*/
}
