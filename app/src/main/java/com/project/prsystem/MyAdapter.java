package com.project.prsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by skplanet on 2016-01-20.
 */

// 로그인 후 메인화면에 과목명을 가로로 뿌려줌
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    int iniLayout;
    ArrayList<SubjectItem> data;
    ProfMainActivity mainActivity;


    android.support.v4.app.FragmentManager manager;
    MainFragment mainFragment;
    MyFragment myFragment;
    FragmentTransaction ft;
    android.support.v4.app.Fragment fragment;

    public MyAdapter(Context context, int iniLayout, ArrayList<SubjectItem> data,  android.support.v4.app.FragmentManager manager) {

        this.context = context;
        this.iniLayout = iniLayout;
        this.data = data;
        this.manager = manager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, iniLayout, null);
        return new ViewHolder(view);
    }


    // 실 데이터 삽입
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubjectItem subject = data.get(position);

//        holder.ImageView.setImageBitmap(subject.image);
        holder.TextName.setText(subject.name);
//        holder.ImageView.setText(subject.image);
//        holder.TextCode.setText(subject.code);



        holder.TextName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 클레스가 맴버변수보다 메모리 할당이 빨리되기 때문에 item.title 은 에러가난다.
                // final로 정의해 놓으면 해결됨.
                Toast.makeText(context, subject.code, Toast.LENGTH_SHORT).show();

                fragment =  manager.findFragmentByTag(subject.code);
                if(fragment == null) {

                    Bundle bundle = new Bundle();
                    bundle.putString("code", subject.code);
                    bundle.putString("name", subject.name);
                    bundle.putString("image", subject.image);

                    myFragment = new MyFragment();
                    myFragment.setArguments(bundle);
                    ft = manager.beginTransaction();
                    ft.replace(R.id.fragment, myFragment, "first");
                    ft.commit();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        //        ImageView ImageView;
        TextView ImageViw;
        TextView TextCode;
        TextView TextName;
        public ViewHolder(View itemView) {
            super(itemView);

//            ImageView = (TextView)itemView.findViewById(R.id.ImageView);
//            TextCode =  (TextView)itemView.findViewById(R.id.TextCode);
            TextName =  (TextView)itemView.findViewById(R.id.TextName);
        }


    }
}

