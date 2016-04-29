package com.project.prsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Created by kim on 2016-04-24.
 */
public class MyFragment extends Fragment {
    Context context;
    Bundle bundle;

    String name;
    String code;
    String image;

    public MyFragment() {
    }


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

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null, false);

        tv = (TextView)view.findViewById(R.id.textView);
        et = (EditText)view.findViewById(R.id.editText);
        view.findViewById(R.id.button).setOnClickListener(handler);

        bundle = getArguments();
        name = bundle.getString("name");
        code = bundle.getString("code");
        image = bundle.getString("image");


        tv.setText("code : " + code + "\n" + "name : " + name + "\n" + "image : " + image);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}
