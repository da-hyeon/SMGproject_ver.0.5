package com.example.hdh.smgproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class UserPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        int ptTime = bundle.getInt("pageNumber");   // 현재 페이지 넘버. 추후 시간으로 바꿀 예정

        view = inflater.inflate(R.layout.fragment_user_page, container, false);
        TextView textView = (TextView)view.findViewById(R.id.ptTime);
        textView.setText(Integer.toString(ptTime));

        return view;

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }
}
