package com.game.studentworld.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.game.studentworld.Activity.MainActivity;
import com.game.studentworld.R;

public class MainFragment extends Fragment {

    private View view;
    private ImageView mMainImage, mEmail;
    private AnimatedVectorDrawable vectorDrawable;
    private Button mSemesterCalculate, mTotalCalculate;
    private Fragment mSemesterCalculateFragment, mTotalCalculateFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        init();
        Animation();
        Intent();

        return view;
    }

    private void init(){
        mEmail = view.findViewById(R.id.Email);
        mMainImage = view.findViewById(R.id.MainImage);
        mTotalCalculate = view.findViewById(R.id.TotalCalculate);
        mSemesterCalculate = view.findViewById(R.id.SemesterCalculate);
        Animation appearAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.appear_animation);
        Animation animationFromButtom = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_from_button);
        mEmail.setAnimation(appearAnimation);
        mMainImage.setAnimation(appearAnimation);
        mTotalCalculate.setAnimation(animationFromButtom);
        mSemesterCalculate.setAnimation(animationFromButtom);
    }

    private void Animation() {
        vectorDrawable = (AnimatedVectorDrawable) mMainImage.getDrawable();
        vectorDrawable.start();
        vectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                vectorDrawable.start();
            }
        });
    }

    private void Intent() {
        mSemesterCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSemesterCalculateFragment = new SemesterCalculateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, mSemesterCalculateFragment).addToBackStack(null).commit();
            }
        });

        mTotalCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTotalCalculateFragment = new TotalCalculateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, mTotalCalculateFragment).addToBackStack(null).commit();
            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SendMail = "mailto:XxD7ooom.SSxX@Gmail.Com";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(SendMail));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {

                }
            }
        });
    }
}