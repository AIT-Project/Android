package com.project.prsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.prsystem.vo.ProfessorInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    LoginRequest login;

    EditText etUserID, etPassword;
    Button button;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bLogin:
                    doAction();
                    break;
                case R.id.tvRegisterLink:
                    goRegister();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.bLogin).setOnClickListener(handler);
        findViewById(R.id.tvRegisterLink).setOnClickListener(handler);
        etUserID = (EditText) findViewById(R.id.etUserID);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }


    void doAction() {
        login = new LoginRequest();
        login.execute();
    }

    void goRegister() {
//        Intent intent = new Intent(LoginActivity.this, Register.class);
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posts, menu);
        return true;
    }

    private String[] getId() {
        String id = etUserID.getText().toString();
        String pwd = etPassword.getText().toString();
        String[] loginInfo = {id, pwd};

        return loginInfo;
    }

    private class LoginRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String[] loginInfo = getId();

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(MyApplication.SERVER_URL);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", loginInfo[0]));
                nameValuePairs.add(new BasicNameValuePair("password", loginInfo[1]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                String result = null;

                while ((line = reader.readLine()) != null) {
                    LogManager.logPrint(line);
                    result = line;
//                    sb.append(line).append("\n");
                }

                parsing(result);


            } catch (IOException e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

            return null;
        }
    }

    // 객체 배열 JSON 파싱
    public void parsing(String result) {
        try {

            JSONObject jObject = new JSONObject(result);
            // JSON 구문을 파싱해서 JSONArray 객체를 생성
            JSONArray jAr = new JSONArray(jObject.get("login").toString());

            JSONObject info = jAr.getJSONObject(0);


            if (info.getString("prof_auth") != null && info.getString("prof_auth").equals("professor")) {

                ProfessorInfo profInfo = new ProfessorInfo();

                profInfo.setProfEmail(info.getString("prof_email"))
                        .setProfName(info.getString("prof_name"))
                        .setProfAuth(info.getString("prof_auth"))
                        .setProfId(info.getInt("prof_id"));
                LogManager.logPrint(profInfo.getProfAuth());
                pref = getSharedPreferences(MyApplication.PROFINFO, 0);
                editor = pref.edit();
                editor.putString(MyApplication.PROFINFO, profInfo.toString());
                if (editor.commit()) {
                }

                goProfMain();

            } else if (info.getString("std_auth") != null && info.getString("std_auth").equals("student")) {
//                pref = getSharedPreferences(MyApplication.STDINFO, MODE_PRIVATE);
//                editor = pref.edit();
                // editor.putString("profInfo", stdInfo.toString());
                //   editor.commit();
                // goStdMain();
            }

        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
    }

    private void goProfMain() {
        login.cancel(true);

        LogManager.logPrint("교수 로그인 허가 받음");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
