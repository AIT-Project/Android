package com.project.prsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.project.prsystem.push.Push;

public class NoticeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "NoticeActivity";
    EditText etSub, etCon;
    Push send;

    public NoticeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);
        rootView.findViewById(R.id.bSend1).setOnClickListener(this);
        etSub = (EditText) rootView.findViewById(R.id.etSub1);
        etCon = (EditText) rootView.findViewById(R.id.etcontent1);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSend1:
                doSend();
                break;
        }
    }
    void doSend() {
        String sub = etSub.getText().toString();
        String con = etCon.getText().toString();
        send = new Push(sub, con, "fLFueUGr4OY:APA91bFeJ3M9rficb4DDGXzCO70RPoMGxErnTOtKrkPTdpmjUslHWdHv4XaHEJ7jSXpoZNk7ACxan-TMWXP_S3qpkIvgVzw89MkO7Q6gSKp_-zrCyrOAxhZZSS_V6GuAebUw6V7Se30H");
        //send = new Push(sub, con, "fZTVAZFzNL4:APA91bE7lTZjMMdp9mZfds3YgfkHET0ZRIYhWR7wEDDrEvdPU46vsD14P9SmiHrlgCqfnaOPGrZ_L24i1yyEP4LmJrTWExLLKlgyxgk0PpWei0VrN-unCpSG_tnHt50AGhNkmQLUfAGO");
        //send = new Push(sub, con, "cynxkEwxOig:APA91bFI_ZKI97KhB-s9Jkn4vCAvke56WtI1pwcR4XW_yi-zQho9NmDwXrialoW6UOeWGw4UjHx3AnAMBJTffLsM4rkgDI5NmS_KCAh9ahFxgf1qZHDj0r_jJ9jOq3w4XN2YWHU9JV4K");
        //ssend = new Push(sub, con, "em1a9Xry8uI:APA91bGkKSX1x3Qw0m3CQrwoq-Itk8r7vgkUDZju6kXRfadq0_CLuKBFhdXsg2CkcqlpszB_yimX6H6vuo3F7xdwNN3gyS67AKQPaJSIR0PodE5ChUcgGEV266h991nL0XZqn4Qz3lIw");
    }
}
