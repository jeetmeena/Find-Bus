package com.example.jeetmeena.findbus;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ListBusItemFragment extends Fragment    {
    // Edge effect / overscroll tracking objects.


    // The current destination rectangle (in pixel coordinates) into which the
// chart data should be drawn.
    private Rect mContentRect;
    private long startClickTime;
    private OverScroller mScroller;
    private RectF mScrollerStartViewport;
    Context contextads;
    View fragmentView;
     Context context;
    ViewGroup _root;
    private int _xDelta;
    private int _yDelta;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private GestureDetector gdt;
    @SuppressLint("ValidFragment")
    public ListBusItemFragment(Context context) {
        // Required empty public constructor

    this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      contextads=getActivity().getBaseContext();
        // Inflate the layout for this fragment
        fragmentView=inflater.inflate(R.layout.fragment_list_bus_item, container, false);

          //  mDetector=new GestureDetectorCompat(context, (GestureDetector.OnGestureListener) contextads);
            fragmentView.setClickable(true);
            fragmentView.setFocusable(true);




        return fragmentView;
    }


}
