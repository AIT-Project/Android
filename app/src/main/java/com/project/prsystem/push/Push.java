package com.project.prsystem.push;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by b10311 on 2016-04-26.
 */
public class Push {
    String token, con, sub;
    public Push(String sub, String con, String token){
        this.token = token;
        this.sub = sub;
        this.con = con;
        SendRequest send = new SendRequest();
        send.execute();
    }
    String[] getId(){
        String[] SendInfo = {sub, con};
        return SendInfo;
    }
    private class SendRequest extends AsyncTask<Void, Void, String> {

        private static final String TAG = "PostFetcher";
        //public static final String SERVER_URL = "https://gcm-http.googleapis.com/gcm/send";
        private static final String SERVER_API_KEY = "AIzaSyAlvaG0ss63NcW7Yro3NvqH3elLFCIUeNA";
        @Override
        protected String doInBackground(Void... params) {
            Log.d(TAG, "2-1");
            String args[] = getId();

            try {
                // Prepare JSON containing the GCM message content. What to send and where to send.
                JSONObject jGcmData = new JSONObject();
                JSONObject jData = new JSONObject();
                Log.d(TAG,"title"+args[0]);
                Log.d(TAG,"message"+args[1]);
                jData.put("title", args[0].trim());
                jData.put("message", args[1].trim());
                Log.d(TAG,jData.toString());
                // Where to send GCM message.
                if (args.length > 1 && args[1] != null) {
                    jGcmData.put("to", token.trim());
                } else {
                    jGcmData.put("to", "/topics/global");
                }
                // What to send in GCM message.
                jGcmData.put("data", jData);
                Log.d(TAG,jGcmData.toString());
                // Create connection to send GCM Message request.
                URL url = new URL("https://android.googleapis.com/gcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "key=" + SERVER_API_KEY);
                Log.d(TAG, token);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                Log.d(TAG, "2-3");
                // Send GCM message content.
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(jGcmData.toString().getBytes());

                // Read GCM response.
                InputStream inputStream = conn.getInputStream();
                String resp = IOUtils.toString(inputStream);
                System.out.println(resp);
                Log.d(TAG, "2-4");
                System.out.println("Check your device/emulator for notification or logcat for " +
                        "confirmation of the receipt of the GCM message.");

            } catch (IOException e) {
                System.out.println("Unable to send GCM message.");
                System.out.println("Please ensure that API_KEY has been replaced by the server " +
                        "API key, and that the device's registration token is correct (if specified).");
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "2-5");

            return null;
        }
    }
}
