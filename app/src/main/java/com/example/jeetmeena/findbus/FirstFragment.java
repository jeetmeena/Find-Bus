package com.example.jeetmeena.findbus;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment {
  int position;
  int[] screen;
    @SuppressLint("ValidFragment")
    public FirstFragment(int i) {
        position=i;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        screen=new int[] {R.layout.fragment_first,R.layout.fragment_second,R.layout.fragment_third,R.layout.fragment_fourth,R.layout.fragment_fifthragment};

        // Inflate the layout for this fragment
        return inflater.inflate(screen[position], container, false);
    }

}
