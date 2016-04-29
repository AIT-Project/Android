package com.project.prsystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by skplanet on 2016-01-20.
 */

//xml, 실데이터를 가져야 한다.
    // 안드로이드는 문법적으로 Context객체가 있어야 실행되는 부분이 많음.
    // Activity가 아니기 때문에 Context객체가 필요한것.
public class MySubjectAdapter extends BaseAdapter {

    int layout;
    ArrayList<SubjectItem> data;
    Context context;


    // 로직구현  data.add(item); 과
    // UI 부분  notifyDataSetChanged() 은
    // 분리하는게 좋다.
    public void addData(SubjectItem subject) {
        data.add(subject);
        notifyDataSetChanged();

    }

    public MySubjectAdapter(ArrayList<SubjectItem> data, Context context, int layout) {
        this.data = data;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //(ImageView)convertView.findViewById(R.id.imageView);는 시간이 많이 걸린다,
    // 따라서 이것에대한 변수를 따로 클래스화 하고,
    // 한번만 생성되게 제약을 걸어놓아 시간을 단축한다.
    class ViewHolder {
        ImageView ImageView;
        TextView TextCode;
        TextView TextName;

//        TextView TextImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convertView는 화면에서 없어지는 view를 재사용한다. ( 메모리 효율 )
        // 문제점 : 색깔, 크기 등의 속성을 구현할 때
        //          그렇지 않은 경우에 원래대로 돌려주는 소스도 짜야함.
        //          재사용될때 바뀐 속성 그대로 나올 수 있기 때문.
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = View.inflate(context,layout,null);
//            LogManager.logPrint("position : " + position);
            holder = new ViewHolder();
//            holder.ImageView = (ImageView)convertView.findViewById(R.id.ImageView);
//            holder.TextCode =  (TextView)convertView.findViewById(R.id.TextCode);
            holder.TextName =  (TextView)convertView.findViewById(R.id.TextName);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        final SubjectItem subject = data.get(position);

        holder.ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클레스가 맴버변수보다 메모리 할당이 빨리되기 때문에 item.title 은 에러가난다.
                // final로 정의해 놓으면 해결됨.
                Toast.makeText(context, subject.name, Toast.LENGTH_SHORT).show();
            }
        });
    //    holder.ImageView.setImageBitmap(subject.image);
        holder.TextName.setText(subject.name);
//        holder.ImageView.setText(subject.image);
        holder.TextCode.setText(subject.code);



        return convertView;
    }


}
