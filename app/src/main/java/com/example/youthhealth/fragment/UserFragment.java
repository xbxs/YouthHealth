package com.example.youthhealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.youthhealth.R;
import com.example.youthhealth.activity.CollectActivity;
import com.example.youthhealth.activity.HistoryActivity;
import com.example.youthhealth.activity.ThemeActivity;

public class UserFragment extends Fragment implements View.OnClickListener {


    private LinearLayout mLl_collect,mll_history, mll_theme,mll_fontsize,mll_checkupdate,mlll_about;
    private ImageView iv_checkupdate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mLl_collect = view.findViewById(R.id.ll_collect);
        mll_history = view.findViewById(R.id.ll_history);
        mll_theme = view.findViewById(R.id.ll_theme);
        mll_fontsize = view.findViewById(R.id.ll_fontsize);
        mll_checkupdate = view.findViewById(R.id.ll_checkupdate);
        mlll_about = view.findViewById(R.id.ll_about);

        iv_checkupdate = view.findViewById(R.id.iv_checkupdate);

        mLl_collect.setOnClickListener(this);
        mll_history.setOnClickListener(this);
        mll_theme.setOnClickListener(this);
        mll_fontsize.setOnClickListener(this);
        mll_checkupdate.setOnClickListener(this);
        mlll_about.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ll_collect:
                intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_history:
                intent = new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_theme:
                intent = new Intent(getContext(), ThemeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.ll_fontsize:

                break;
            case R.id.ll_checkupdate:
                initAnimation();

                break;
            case R.id.ll_about:
                break;
            default:
                break;
        }
    }

    public void initAnimation(){
        RotateAnimation rotateAnimation = new RotateAnimation(0,720, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        iv_checkupdate.startAnimation(rotateAnimation);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    

}
