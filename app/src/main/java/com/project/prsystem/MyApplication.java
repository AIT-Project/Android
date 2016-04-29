package com.project.prsystem;

import android.app.Application;

/**
 * Created by kim on 2016-04-23.
 */
public class MyApplication extends Application {

    public static String SERVER_URL = "http://220.126.178.31:9999/PRsystem/mlogin";
    public static String IMAGE_URL = "http://220.126.178.31:9999/PRsystem/Fileupload/";
    public static String MAIN_URL = "http://220.126.178.31:9999/PRsystem/mmain";

    // 쉐어프리프런스 파일명
    public static String LOGIN = "login";


    // public static String INFO = "info";
    public static String PROFINFO = "profInfo";
    public static String STDINFO = "stdInfo";


    public static String NOTICEINFO = "noticeInfo";
    public static String NOTICEINFO_SIZE = "noticeInfoSize";

    public static String AUTH = "auth";

}