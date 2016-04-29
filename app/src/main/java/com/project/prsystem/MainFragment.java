package com.project.prsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kim on 2016-04-24.
 */
public class MainFragment extends Fragment{
    Context context;
    Bundle bundle;
    ListView listView;
    String name;

    MainAdapter adapter;
    ArrayList<MainItem> item = new ArrayList<MainItem>();
    public MainFragment() {
    }

//    interface MyListener {
//        public void receiveMessage(String data);
//    }
//    MyListener listener;
//    public void setOnMyListener(MyListener listener) {
//        this.listener = listener;
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    TextView tv;
    EditText et;
    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()) {
                case R.id.button :
                    tv.setText(et.getText().toString());
                    break;
                case R.id.button7 :
//                    doAction2();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null, false);

        tv = (TextView)view.findViewById(R.id.textView);
        et = (EditText)view.findViewById(R.id.editText);
        view.findViewById(R.id.button).setOnClickListener(handler);

        view.findViewById(R.id.button7).setOnClickListener(handler);

        bundle = getArguments();
        Map<String,String> noticeMap = new HashMap<>();
        if( bundle.getInt(MyApplication.NOTICEINFO_SIZE) != 0) {
            for(int i=0 ; i< bundle.getInt(MyApplication.NOTICEINFO_SIZE) ; i++) {
                noticeMap = Parsing.noticeInfoParsing(bundle.getString(MyApplication.NOTICEINFO + i));
                item.add(new MainItem(Integer.parseInt(noticeMap.get("noti_number")), noticeMap.get("noti_name"),noticeMap.get("noti_mod_date")));
            }
            name = bundle.getString(MyApplication.NOTICEINFO+"0");
            LogManager.logPrint(name);
        } else {
            LogManager.logPrint(bundle.getInt(MyApplication.NOTICEINFO_SIZE)+"");
        }

        tv.setText("로그인 메인 화면 ");


        //view = (ListView)findViewById(R.id.listView); // inflate 한 것임.

        listView = (ListView)view.findViewById(R.id.view);
//      //  adapter = new MainAdapter(item,this,R.layout.main_item);
        adapter = new MainAdapter(item,getActivity(), R.layout.main_item);
        listView.setAdapter(adapter);

        //  ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, item) ;





        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}
