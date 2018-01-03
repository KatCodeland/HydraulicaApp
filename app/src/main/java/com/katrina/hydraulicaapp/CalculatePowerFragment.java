package com.katrina.hydraulicaapp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CalculatePowerFragment extends Fragment implements OnDialogDoneListener {

    public static final String LOGTAG = "DialogFragDemo";

    public static String ALERT_DIALOG_TAG = "CALCULATION DIALOG";
    private Button mCalculate;
    private EditText mPressure;
    private EditText mCylinderArea;
    private ImageView mAnimation;
    private String mAnswer;

    public CalculatePowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calculate_power, container, false);
        mPressure = (EditText)rootView.findViewById(R.id.enter_pressure);
        mCylinderArea = (EditText)rootView.findViewById(R.id.enter_cylinder_area);
        mAnimation = (ImageView)rootView.findViewById(R.id.piston_animation);

        mCalculate = (Button) rootView.findViewById(R.id.calculate_button);
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
                calculate();

            }
        });

        return rootView;
    }

    //================= CALCULATE DIALOG ====================


    private void calculate() {

        mAnswer = "Cylinder Force Pressure = " + Integer.parseInt(mPressure.getText().toString()) * Integer.parseInt(mCylinderArea.getText().toString());

       FragmentTransaction ft = getFragmentManager().beginTransaction();
        CalculateAlertDialogFragment adf = CalculateAlertDialogFragment.newInstance(mAnswer);

        adf.show(ft, ALERT_DIALOG_TAG);
    }
    public void onDialogDone(String tag, boolean cancelled, CharSequence message) {
        String s = tag + " responds with: " + message;

        if(cancelled) s = tag + " was cancelled by the user";

        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        Log.v(LOGTAG, s);
    }

    private void animate() {
        mAnimation.setVisibility(ImageView.VISIBLE);
        mAnimation.setBackgroundResource(R.drawable.frame_animation);

        AnimationDrawable frameAnimation =
                (AnimationDrawable)mAnimation.getBackground();
        frameAnimation.start();
    }

}
