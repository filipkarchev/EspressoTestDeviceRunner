package com.example.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestClass2 {
    private Context mContext=null;
    boolean testPassed = true;

    public void setContext(Context context)
    {
        this.mContext = context;
    }

    public boolean runTest1() {

        View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);

        //Getting the views and performing specific actions
        int idButtonTwo = mContext.getResources().getIdentifier("buttonTwo", "id", mContext.getPackageName());
        final  Button btnTwo =  ((Button) rootView.findViewById(idButtonTwo));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                btnTwo.performClick();
            }
        });

        int idButtonAdd = mContext.getResources().getIdentifier("buttonAdd", "id", mContext.getPackageName());
        final  Button btnAdd =  ((Button) rootView.findViewById(idButtonAdd));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                btnAdd.performClick();
            }
        });

        int idButtonFive = mContext.getResources().getIdentifier("buttonFive", "id", mContext.getPackageName());
        final  Button btnFive =  ((Button) rootView.findViewById(idButtonFive));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                btnFive.performClick();
            }
        });

        int idButtonEquals = mContext.getResources().getIdentifier("buttonEqual", "id", mContext.getPackageName());
        final  Button btnEquals =  ((Button) rootView.findViewById(idButtonEquals));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                btnEquals.performClick();
            }
        });


        int idTxtInfo = mContext.getResources().getIdentifier("infoTextView", "id", mContext.getPackageName());
        final TextView txtInfo = ((TextView) rootView.findViewById(idTxtInfo));

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String result =  txtInfo.getText().toString();

                // Asserting the result is what we expect
                if(result.equals("2+5 = 7"))
                {
                    Log.i("TestClass","Test passed");
                    testPassed = true;
                }else
                {
                    Log.i("TestClass","Test failed");
                    testPassed = false;
                }
            }
        });

        int idBtnClear = mContext.getResources().getIdentifier("buttonClear", "id", mContext.getPackageName());
        //Clear the content, so the application continues as in the entry point
        final  Button btnClear =  ((Button) rootView.findViewById(idBtnClear));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                btnClear.performClick();
            }
        });

        return testPassed;

    }
}
