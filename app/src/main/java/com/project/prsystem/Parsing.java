package com.project.prsystem;

import android.util.Log;

import com.project.prsystem.vo.Notice;
import com.project.prsystem.vo.ProfessorInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kim on 2016-04-24.
 */
public class Parsing {

    // 교수 정보 파싱.
    public static Map<String, String> profInfoParsing(String data) {

        Map<String, String> map = new HashMap<String, String>();

        try {
//            LogManager.logPrint(data);
            JSONObject jObject = new JSONObject(data);

            JSONArray jAr = new JSONArray(jObject.get("login").toString());
            JSONObject info = jAr.getJSONObject(0);

            ProfessorInfo profInfo = new ProfessorInfo();

            map.put("prof_email", info.getString("prof_email"));
            map.put("prof_name", info.getString("prof_name"));
            map.put("prof_auth", info.getString("prof_auth"));
            map.put("prof_id", Integer.toString(info.getInt("prof_id")));

        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
        return map;
    }

    // 공지사항 파싱
    public static Map<String, String> noticeInfoParsing(String data) {

        Map<String, String> map = new HashMap<String, String>();
        LogManager.logPrint(data);
        try {
//            LogManager.logPrint(data);
            JSONObject jObject = new JSONObject(data);
            LogManager.logPrint(data);
            JSONArray jAr = new JSONArray(jObject.get("notice").toString());
            JSONObject info = jAr.getJSONObject(0);

            Notice notice = new Notice();

            map.put("noti_number", Integer.toString(info.getInt("noti_number")));
            map.put("noti_name", info.getString("noti_name"));
            map.put("noti_mod_date", info.getString("noti_mod_date"));

        } catch (JSONException e) {
            Log.d("tag", "Parse Error");
        }
        return map;
    }
}
