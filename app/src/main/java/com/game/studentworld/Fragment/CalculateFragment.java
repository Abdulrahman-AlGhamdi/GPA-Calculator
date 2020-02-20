package com.game.studentworld.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.game.studentworld.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateFragment extends Fragment {

    // Views
    private View view;
    private Button mAdd;
    private Button mRemove;
    private Button mCalculate;
    private int[] mGradeArray;
    private int[] mCreditArray;
    private LinearLayout mSubject;

    // Functions
    private double GPA;
    private double TotalScore;
    private double TotalCredit;
    private int RawPosition = 0;
    private List<Double> GradeList;
    private List<Double> CreditList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_calculate, container, false);

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
        RevealRow();
        Calculate();

        return view;
    }

    private void init(){
        GradeList = new ArrayList<>();
        CreditList = new ArrayList<>();
        mAdd = view.findViewById(R.id.Add);
        mRemove = view.findViewById(R.id.Remove);
        mCalculate = view.findViewById(R.id.Calculate);
        mGradeArray = new int[] {R.id.Grade1, R.id.Grade2, R.id.Grade3, R.id.Grade4, R.id.Grade5,
                R.id.Grade6, R.id.Grade7, R.id.Grade8, R.id.Grade9, R.id.Grade10};
        mCreditArray = new int[] {R.id.Credit1, R.id.Credit2, R.id.Credit3, R.id.Credit4, R.id.Credit5,
                R.id.Credit6, R.id.Credit7, R.id.Credit8, R.id.Credit9, R.id.Credit10};
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row_layout, getResources().getStringArray(R.array.Grades)){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        mCalculate.setEnabled(false);
    }

    private void RevealRow(){

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int GradeID : mGradeArray) {
                    Spinner Grade = view.findViewById(GradeID);

                    Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            TextView grade = (TextView) view;
                            if (position == adapter.getCount()) {
                                grade.setTextColor(getResources().getColor(R.color.Beige));
                            } else {
                                grade.setTextColor(getResources().getColor(R.color.Beige));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    Grade.setAdapter(adapter);
                    Grade.setSelection(adapter.getCount());
                }

                for (int CreditID : mCreditArray) {
                    EditText Credit = view.findViewById(CreditID);
                    Credit.getText().clear();
                }

                if(RawPosition < 10 && RawPosition >= 0){

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
                    if (RawPosition == 10) {
                        mSubject = view.findViewById(R.id.Subject10);
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

                for (int GradeID : mGradeArray) {
                    Spinner Grade = view.findViewById(GradeID);

                    Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            TextView grade = (TextView) view;
                            if (position == adapter.getCount()) {
                                grade.setTextColor(getResources().getColor(R.color.Beige));
                            } else {
                                grade.setTextColor(getResources().getColor(R.color.Beige));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    Grade.setAdapter(adapter);
                    Grade.setSelection(adapter.getCount());
                }

                for (int CreditID : mCreditArray) {
                    EditText Credit = view.findViewById(CreditID);
                    Credit.getText().clear();
                }

                if(RawPosition <= 10 && RawPosition > 0){

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
                    if (RawPosition == 9) {
                        mSubject = view.findViewById(R.id.Subject10);
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
                    GPA = (TotalScore/TotalCredit);
                }

                if(String.valueOf(GPA) == "NaN"){
                    GPA = 0;
                }

                TotalCredit = 0.0;
                TotalScore = 0.0;
                GradeList.clear();
                CreditList.clear();
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                decimalFormat.format(GPA);
                Log.d("GPAResult", decimalFormat.format(GPA));
            }
        });
    }
}
