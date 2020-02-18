package com.game.studentworld.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.game.studentworld.R;

public class MainFragment extends Fragment {

    private View view;
    private LinearLayout mCalculate;
    private Fragment mCalculateFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        init();
        Calculate();

        return view;
    }

    private void init(){
        mCalculate = view.findViewById(R.id.CalculateGPA);
    }

    private void Calculate() {
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalculateFragment = new CalculateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, mCalculateFragment).addToBackStack(null).commit();
            }
        });
    }
}