package com.project.sagar.fcmnotifier;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCMNotifier {
    String fcmkey;
    String title;
    String msg;
    String fcmtoken;
    FCMNotifier(String fcmkey, String fcmtoken)
    {
        this.fcmkey=fcmkey;
        this.fcmtoken=fcmtoken;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void send()
    {
        if(!fcmkey.isEmpty())
        {
            if(!fcmtoken.isEmpty())
            {
                if(!msg.isEmpty())
                {
                    if(!title.isEmpty())
                    {
                        new Notify().execute();
                    }
                }
            }
        }

    }







    public class Notify extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization","key="+fcmkey);
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();


                json.put("to", fcmtoken);


                JSONObject info = new JSONObject();
                info.put("title", title);   // Notification title
                info.put("body", msg); // Notification body
                json.put("notification", info);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();

            } catch (Exception e) {
                Log.d("Error", "" + e);
            }


            return null;
        }

    }
}
