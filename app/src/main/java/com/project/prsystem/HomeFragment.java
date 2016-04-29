package com.project.prsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.prsystem.vo.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class HomeFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    ArrayList<SubjectItem> mySubejectList = new ArrayList<SubjectItem>();
    MyAdapter adapter;

    TextView mTextMessage;

    private TextView textview = null;
    private String JSONdata1 = null;
    private final static int GET_JSON = 0;
    private final static int DO_STH = 1;

    // 교수정보
    Map<String, String> profInfo;

    // 과목정보 얻는 스레드
    GetSubjThread getSubjThread;

    MainFragment mainFragment;
    android.support.v4.app.FragmentManager manager;
    FragmentTransaction ft;
    android.support.v4.app.Fragment fragment;

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView:
                    // 메인 fragment를 부른다.
                    setDateMainFragment();
                    break;
            }
        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.findViewById(R.id.textView).setOnClickListener(handler);
        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();

        // 교수 정보 파일을 불러온다,
        pref = getSharedPreferences(MyApplication.PROFINFO, 0);

        profInfo = Parsing.profInfoParsing(pref.getString(MyApplication.PROFINFO, ""));

        recyclerView = (RecyclerView)rootView.findViewById(R.id.view);
        adapter = new MyAdapter(this, R.layout.subejct, mySubejectList, manager);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        getSubjThread = new GetSubjThread();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Handler messageHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;

            switch (what) {
                case GET_JSON:
                    // 과목파싱 스레드 종료
                    getSubjThread.cancel(true);

                    break;

                case DO_STH:
                    break;


            }
        }
    };
    public void setDateMainFragment() {
        if (fragment == null) {

            pref = getSharedPreferences(MyApplication.NOTICEINFO, 0);
            // pref.getString(MyApplication.NOTICEINFO, "");

            //LogManager.logPrint( pref.getString(MyApplication.NOTICEINFO+1, ""));
            Bundle bundle = new Bundle();
            bundle.putInt(MyApplication.NOTICEINFO_SIZE, pref.getInt(MyApplication.NOTICEINFO_SIZE, 0));
            for (int i = 0; i < pref.getInt(MyApplication.NOTICEINFO_SIZE, 0); i++) {
                String notice = pref.getString(MyApplication.NOTICEINFO + i, "");
                bundle.putString(MyApplication.NOTICEINFO + i, notice);
            }
            mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            ft = manager.beginTransaction();
            ft.replace(R.id.fragment, mainFragment, "main");
            ft.commit();
        }

    }
    void initData(ArrayList<String> image, ArrayList<String> code, ArrayList<String> name) {
        for (int i = 0; i < name.size(); i++) {
            mySubejectList.add(new SubjectItem(image.get(i), code.get(i), name.get(i)));
        }
        adapter.notifyDataSetChanged();
    }

    private class GetSubjThread extends AsyncTask<Void, Void, String> {
        String result = null;

        @Override
        protected String doInBackground(Void... params) {


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(MyApplication.MAIN_URL);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                // 웹서버에서 사용자 권한확인
                nameValuePairs.add(new BasicNameValuePair(MyApplication.AUTH, profInfo.get("prof_auth")));
                // 웹서버에서 사용자 교번 확인
                nameValuePairs.add(new BasicNameValuePair("prof_id", profInfo.get("prof_id")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    result = line;

                }
                // result = sb.toString();
                // 스레드 종료를 위한 알림
                messageHandler.sendEmptyMessage(GET_JSON);

            } catch (IOException e) {
            } finally {
                // 과목 파싱
                parsing(result);
                httpclient.getConnectionManager().shutdown();
            }

            return null;
        }
    }

    // 객체 배열 JSON 파싱
    // 파싱한 데이터들을 쉐어프리퍼런스에 저장한다.
    public void parsing(String result) {

        LogManager.logPrint(result);
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
                image.add(student.getString("subj_image"));
                code.add(student.getString("subj_code_div"));
                name.add(student.getString("subj_name"));
            }


            // 공지사항 정보를 파싱 후, vo에 저장.
            JSONArray jAr2 = new JSONArray(jObject1.get("notice").toString());
            Notice noticeInfo = new Notice();

            pref = getSharedPreferences(MyApplication.NOTICEINFO, 0);
            editor = pref.edit();
            editor.putInt(MyApplication.NOTICEINFO_SIZE, jAr2.length());

            for (int i = 0; i < jAr2.length(); i++) {
                JSONObject notice = jAr2.getJSONObject(i);

                try {
                    // STring 으로 날라온 날짜 데이터를 다시 Date 형식으로 바꾸어주자,
                    DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

                    noticeInfo.setNoti_number(notice.getInt("noti_number"))
                            .setNoti_comment(notice.getString("noti_comment"))
                            .setSubj_code_div(notice.getString("subj_code_div"))
                            .setProf_id(notice.getInt("prof_id"))
                            .setNoti_cre_date(sdFormat.parse(notice.getString("noti_cre_date")))
                            .setNoti_mod_date(sdFormat.parse(notice.getString("noti_mod_date")))
                            .setNoti_name(notice.getString("noti_name"))
                            .setNoti_del(notice.getInt("noti_del"));


                    //     editor = pref.edit();
                    // 공지사항 목록을 i개 만들어서 json 형식으로 넣는다.

                    pref = getSharedPreferences(MyApplication.NOTICEINFO, 0);
                    editor = pref.edit();
                    editor.putString(MyApplication.NOTICEINFO + i, noticeInfo.toString());

                } catch (Exception e) {

                } finally {
                    if (editor.commit()) {
//                        LogManager.logPrint("확인 " + pref.getInt(MyApplication.NOTICEINFO_SIZE, 0));
//                       LogManager.logPrint("확인 " + pref.getString(MyApplication.NOTICEINFO+i, ""));
                    }
                }

            }


            /// 파싱데이터 커스텀 뷰로 보내기.
            initData(image, code, name);
        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
        // 메인 프레그먼트에 공지사항 뿌리기.

        setDateMainFragment();
    }
}
