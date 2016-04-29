package com.project.prsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.project.prsystem.push.Push;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "NoticeActivity";
    EditText etSub, etCon;
    Push send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        findViewById(R.id.bSend).setOnClickListener(this);
        etSub = (EditText) findViewById(R.id.etSub);
        etCon = (EditText) findViewById(R.id.etcontent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSend:
                doSend();
                break;
        }
    }
    void doSend() {
        String sub = etSub.getText().toString();
        String con = etCon.getText().toString();
        send = new Push(sub, con, "dcBN8zyEnlE:APA91bGEHWjHr5KxJhaFd7BOdSEtTPPatysOpt_xFOxe6bABGThhV3Xs9_vdwsy0nc_hlOHqBUNCX37PFnavfNuozZQ9JyjwyBNJiTES2ks514piokTpelYIJ8C3YR3-VECUnk5Lu8Hj");
        //send = new Push(sub, con, "fZTVAZFzNL4:APA91bE7lTZjMMdp9mZfds3YgfkHET0ZRIYhWR7wEDDrEvdPU46vsD14P9SmiHrlgCqfnaOPGrZ_L24i1yyEP4LmJrTWExLLKlgyxgk0PpWei0VrN-unCpSG_tnHt50AGhNkmQLUfAGO");
        //send = new Push(sub, con, "cynxkEwxOig:APA91bFI_ZKI97KhB-s9Jkn4vCAvke56WtI1pwcR4XW_yi-zQho9NmDwXrialoW6UOeWGw4UjHx3AnAMBJTffLsM4rkgDI5NmS_KCAh9ahFxgf1qZHDj0r_jJ9jOq3w4XN2YWHU9JV4K");
        //ssend = new Push(sub, con, "em1a9Xry8uI:APA91bGkKSX1x3Qw0m3CQrwoq-Itk8r7vgkUDZju6kXRfadq0_CLuKBFhdXsg2CkcqlpszB_yimX6H6vuo3F7xdwNN3gyS67AKQPaJSIR0PodE5ChUcgGEV266h991nL0XZqn4Qz3lIw");
    }
}
