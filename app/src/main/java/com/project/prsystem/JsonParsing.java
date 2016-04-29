package com.project.prsystem;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class JsonParsing extends AppCompatActivity {

    SharedPreferences pref;

    //    ListView view;
    RecyclerView recyclerView;
    ArrayList<SubjectItem> mySubejectList = new ArrayList<SubjectItem>();

    //    MySubjectAdapter subejctAdapter;
    MyAdapter adapter;

    TextView mTextMessage;

    private TextView textview = null;
    private String JSONdata1 = null;
    private final static int GET_JSON = 0;
    private final static int DO_STH = 1;
    private final static int GET_IMAGE = 2;

    private static int BACK_ING = 1;
    //    ImageView ImageView;

    ArrayList<Bitmap> bmImg = new ArrayList<Bitmap>();
    //    ArrayList<Bitmap> bitmapImage = new ArrayList<Bitmap>();
    back task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(MyApplication.LOGIN, MODE_PRIVATE);


        // view = (ListView)findViewById(R.id.listView); // inflate 한 것임.

//            View headerView = view.inflate(this, R.layout.title_view, null);
//            view.addHeaderView(headerView);
//            subejctAdapter = new MySubjectAdapter(mySubejectList,this,R.layout.subejct);
        //view.setAdapter(subejctAdapter);


        recyclerView = (RecyclerView) findViewById(R.id.view);
        //adapter = new MyAdapter(this, R.layout.subejct, mySubejectList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        new getData().execute();


    }

    void initData(ArrayList<String> image, ArrayList<String> code, ArrayList<String> name) {
        back back = new back();
        back.execute(image);
        if (BACK_ING == 0) {
            for (int i = 0; i < name.size(); i++) {

                mySubejectList.add(new SubjectItem(image.get(i), code.get(i), name.get(i)));

            }
            adapter.notifyDataSetChanged();
//            subejctAdapter.notifyDataSetChanged();
        }
    }

    private class getData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String result = null;


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(MyApplication.MAIN_URL);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(MyApplication.AUTH, pref.getString(MyApplication.AUTH, "")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                result = sb.toString();
                LogManager.logPrint(result);
                JSONdata1 = result;
                messageHandler.sendEmptyMessage(GET_JSON);

            } catch (IOException e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

            return null;
        }
    }

    private class back extends AsyncTask<ArrayList<String>, Integer, ArrayList<Bitmap>> {
        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... urls) {
            // TODO Auto-generated method stub
            try {
                for (int i = 0; i < urls[0].size(); i++) {
                    URL myFileUrl = new URL(MyApplication.IMAGE_URL + urls[0].get(i));
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bmImg.add(BitmapFactory.decodeStream(is));

                    conn.disconnect();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        //리턴하면 여기로옴
        // 여기값을 핸들러로 넘겨주면 됨
        protected void onPostExecute(ArrayList<Bitmap> img) {

            messageHandler.sendEmptyMessage(GET_IMAGE);
            if (isCancelled()) {
                cancel(true);
            }
        }

    }

    private Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;

            switch (what) {
                case GET_JSON:
                    jsonParsing(JSONdata1);

                    break;

                case DO_STH:
                    break;

                case GET_IMAGE:
                    BACK_ING = 0;
                    break;
            }
        }
    };


    // 객체 배열 JSON 파싱
    public void jsonParsing(String result) {


        String strData = "";

        ArrayList<String> image = new ArrayList<String>();
        ArrayList<String> code = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();

        try {

            JSONObject jObject1 = new JSONObject(result);
            // JSON 구문을 파싱해서 JSONArray 객체를 생성
            JSONArray jAr = new JSONArray(jObject1.get("subject").toString());
            for (int i = 0; i < jAr.length(); i++) {
                JSONObject student = jAr.getJSONObject(i);
                image.add(student.getString("image"));
                code.add(student.getString("code"));
                name.add(student.getString("name"));
            }

            /// 파싱데이터 커스텀 뷰로 보내기.
            initData(image, code, name);
        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
    }


}
