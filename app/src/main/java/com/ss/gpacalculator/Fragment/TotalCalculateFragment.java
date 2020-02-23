package com.ss.gpacalculator.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.ss.gpacalculator.EditTextFormatter.NumberTextWatcher;
import com.ss.gpacalculator.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TotalCalculateFragment extends Fragment {

    // Views
    private View view;
    private ImageView mBack;
    private LinearLayout mSubject;
    private EditText mOldCredit, mOldGPA;
    private int[] mGradeArray, mCreditArray;
    private Button mAdd, mRemove, mCalculate;

    // Functions
    private Dialog Dialog;
    private int RawPosition = 0;
    private ArrayAdapter<String> adapter;
    private double TotalScore, TotalCredit;
    private List<Double> GradeList, CreditList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_total_calculate, container, false);

        init();
        RevealRow();
        Calculate();
        Intent();

        return view;
    }

    private void init(){
        GradeList = new ArrayList<>();
        CreditList = new ArrayList<>();
        Dialog = new Dialog(Objects.requireNonNull(getActivity()));
        mAdd = view.findViewById(R.id.Add);
        mBack = view.findViewById(R.id.Back);
        mRemove = view.findViewById(R.id.Remove);
        mOldGPA = view.findViewById(R.id.OldGPA);
        mCalculate = view.findViewById(R.id.Calculate);
        mOldCredit = view.findViewById(R.id.OldCredit);
        mOldGPA.addTextChangedListener(new NumberTextWatcher(mOldGPA));
        mGradeArray = new int[] {R.id.Grade1, R.id.Grade2, R.id.Grade3, R.id.Grade4, R.id.Grade5,
                R.id.Grade6, R.id.Grade7, R.id.Grade8, R.id.Grade9};
        mCreditArray = new int[] {R.id.TotalCredit1, R.id.TotalCredit2, R.id.TotalCredit3, R.id.TotalCredit4, R.id.TotalCredit5,
                R.id.TotalCredit6, R.id.TotalCredit7, R.id.TotalCredit8, R.id.TotalCredit9};
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row_layout, getResources().getStringArray(R.array.Grades)){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        for (int GradeID : mGradeArray) {
            Spinner Grade = view.findViewById(GradeID);
            Grade.setAdapter(adapter);
            Grade.setSelection(adapter.getCount());
        }
        Animation animationFromRight = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_from_right);
        Animation AnimationFromButtom = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_from_button);
        mBack.setAnimation(animationFromRight);
        mRemove.setAnimation(AnimationFromButtom);
        mAdd.setAnimation(AnimationFromButtom);
        mOldGPA.setAnimation(animationFromRight);
        mOldCredit.setAnimation(animationFromRight);
        mCalculate.setAnimation(AnimationFromButtom);
        mCalculate.setEnabled(false);
    }

    private void RevealRow(){

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RawPosition < 9 && RawPosition >= 0){
                    RawPosition +=1;

                    if (RawPosition == 1) {
                        mSubject = view.findViewById(R.id.Subject1);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 2) {
                        mSubject = view.findViewById(R.id.Subject2);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 3) {
                        mSubject = view.findViewById(R.id.Subject3);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 4) {
                        mSubject = view.findViewById(R.id.Subject4);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 5) {
                        mSubject = view.findViewById(R.id.Subject5);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 6) {
                        mSubject = view.findViewById(R.id.Subject6);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 7) {
                        mSubject = view.findViewById(R.id.Subject7);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 8) {
                        mSubject = view.findViewById(R.id.Subject8);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                    if (RawPosition == 9) {
                        mSubject = view.findViewById(R.id.Subject9);
                        mSubject.setVisibility(View.VISIBLE);
                    }
                }

                if(RawPosition == 0){
                    mCalculate.setEnabled(false);
                }else{
                    mCalculate.setEnabled(true);
                }
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(RawPosition <= 9 && RawPosition > 0){

                    int GradeID = mGradeArray[RawPosition-1];
                    Spinner Grade = view.findViewById(GradeID);
                    Grade.setAdapter(adapter);
                    Grade.setSelection(adapter.getCount());

                    int CreditID = mCreditArray[RawPosition-1];
                    EditText Credit = view.findViewById(CreditID);
                    Credit.getText().clear();

                    RawPosition -=1;

                    if (RawPosition == 0) {
                        mSubject = view.findViewById(R.id.Subject1);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 1) {
                        mSubject = view.findViewById(R.id.Subject2);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 2) {
                        mSubject = view.findViewById(R.id.Subject3);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 3) {
                        mSubject = view.findViewById(R.id.Subject4);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 4) {
                        mSubject = view.findViewById(R.id.Subject5);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 5) {
                        mSubject = view.findViewById(R.id.Subject6);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 6) {
                        mSubject = view.findViewById(R.id.Subject7);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 7) {
                        mSubject = view.findViewById(R.id.Subject8);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                    if (RawPosition == 8) {
                        mSubject = view.findViewById(R.id.Subject9);
                        mSubject.setVisibility(View.INVISIBLE);
                    }
                }

                if(RawPosition == 0){
                    mCalculate.setEnabled(false);
                }else{
                    mCalculate.setEnabled(true);
                }
            }
        });
    }

    private void Calculate(){

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int CreditID : mCreditArray) {
                    double CreditValue;
                    EditText Credit = view.findViewById(CreditID);
                    String CreditString = Credit.getText().toString();
                    if (CreditString.trim().equals("")) {
                        CreditValue = 0.0;
                    }else{
                        CreditValue = Double.parseDouble(CreditString);
                    }
                    CreditList.add(CreditValue);
                }

                for (int GradeID : mGradeArray) {
                    Spinner Grade = view.findViewById(GradeID);
                    String GradeString = Grade.getSelectedItem().toString();
                    final double GradeValue;

                    if (GradeString.trim().equals("")) {
                        GradeValue = 0;
                    } else if (GradeString.equals("A+")) {
                        GradeValue = 5;
                    } else if (GradeString.equals("A")) {
                        GradeValue = 4.75;
                    } else if (GradeString.equals("B+")) {
                        GradeValue = 4.5;
                    } else if (GradeString.equals("B")) {
                        GradeValue = 4;
                    } else if (GradeString.equals("C+")) {
                        GradeValue = 3.5;
                    } else if (GradeString.equals("C")) {
                        GradeValue = 3;
                    } else if (GradeString.equals("D+")) {
                        GradeValue = 2.5;
                    } else if (GradeString.equals("D")) {
                        GradeValue = 2;
                    } else if (GradeString.equals("F")) {
                        GradeValue = 1;
                    } else {
                        GradeValue = 0;
                    }
                    GradeList.add(GradeValue);
                }

                if(mOldCredit.getText().toString().equals("")){
                    mOldCredit.setError("");
                }else if(mOldGPA.getText().toString().equals("")){
                    mOldGPA.setError("");
                }else{
                    Result();
                }
            }
        });
    }

    private void Result() {

        for (int i = 0; i < RawPosition; i++) {
            double Grade = GradeList.get(i);
            double Credit = CreditList.get(i);
            if (Grade > 0){
                double Score = (Credit*Grade);
                TotalCredit += Credit;
                TotalScore += Score;
            } else {
                TotalCredit += 0;
                TotalScore += 0;
            }
        }

        double OldGPA = Double.parseDouble(mOldGPA.getText().toString());
        double OldCredit = Double.parseDouble(mOldCredit.getText().toString());
        double OldScore = OldGPA*OldCredit;
        double GPA = ((TotalScore + OldScore) / (TotalCredit + OldCredit));

        if(String.valueOf(GPA).equals("NaN")){
            GPA = 0;
        }

        TotalCredit = 0.0;
        TotalScore = 0.0;
        GradeList.clear();
        CreditList.clear();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Dialog.setContentView(R.layout.result_popup_massege);
        TextView ResultGPA = Dialog.findViewById(R.id.ResultGPA);
        ResultGPA.setText(decimalFormat.format(GPA));
        Objects.requireNonNull(Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog.show();
    }

    private void Intent() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mMainFragment = new MainFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, mMainFragment).addToBackStack(null).commit();
            }
        });
    }
}
