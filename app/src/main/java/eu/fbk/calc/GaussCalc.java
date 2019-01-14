package eu.fbk.calc;

/**
 * Created by lestat on 27/04/2017.
 */

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testslibrary.TestLoader;

import java.text.DecimalFormat;
import java.util.List;



public class GaussCalc extends Activity {
//AppCompatActivity {

    private char CURRENT_OPERATION;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decimalFormat = new DecimalFormat("#.##########");

        final EditText editText = (EditText) findViewById(R.id.editText);
        // message(editText);
        final TextView infoTextView = (TextView) findViewById(R.id.infoTextView);

        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + ".");
            }
        });

        Button buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "0");
            }
        });

        Button buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "1");
            }
        });

        Button buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "2");
            }
        });


        Button buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "3");
            }
        });

        Button buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "4");
            }
        });

        Button buttonFive = (Button) findViewById(R.id.buttonFive);
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "5");
            }
        });

        Button buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText editText = (EditText) findViewById(R.id.editText);
                editText.setText(editText.getText() + "6");
            }
        });

        Button buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "7");
            }
        });

        Button buttonEight = (Button) findViewById(R.id.buttonEight);
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText() + "8");
            }
        });

        Button buttonNine = (Button) findViewById(R.id.buttonNine);
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText editText = (EditText) findViewById(R.id.editText);
                editText.setText(editText.getText() + "9");
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computation.storeFirst(Double.parseDouble(editText.getText().toString()));
                CURRENT_OPERATION = '+';
                infoTextView.setText(decimalFormat.format(Computation.getValueOne()) + "+");
                editText.setText("");
            }
        });

        Button buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computation.storeFirst(Double.parseDouble(editText.getText().toString()));
                CURRENT_OPERATION = '-';
                infoTextView.setText(decimalFormat.format(Computation.getValueOne()) + "-");
                editText.setText("");
            }
        });

        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computation.storeFirst(Double.parseDouble(editText.getText().toString()));
                CURRENT_OPERATION = '*';
                infoTextView.setText(decimalFormat.format(Computation.getValueOne()) + "*");
                editText.setText("");
            }
        });

        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computation.storeFirst(Double.parseDouble(editText.getText().toString()));
                CURRENT_OPERATION = '/';
                infoTextView.setText(decimalFormat.format(Computation.getValueOne()) + "/");
                editText.setText("");

            }
        });

        Button buttonEqual = (Button) findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computation.compute(CURRENT_OPERATION, Double.parseDouble(editText.getText().toString()));
                editText.setText("");
                infoTextView.setText(infoTextView.getText().toString() +
                        decimalFormat.format(Computation.getValueTwo()) + " = " + decimalFormat.format(Computation.getValueOne()));
                Computation.setValueOne(Double.NaN);
                CURRENT_OPERATION = '0';


            }
        });

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0) {
                    CharSequence currentText = editText.getText();
                    editText.setText(currentText.subSequence(0, currentText.length() - 1));
                } else {
                    Computation.setValueOne(Double.NaN);
                    Computation.setValueTwo(Double.NaN);
                    editText.setText("");
                    infoTextView.setText("");
                }

                //Run Unit tests
                try {
                   // runUnitTests();

                   // runTests();

                    requestPermission();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void requestPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {

            Log.i("GaussCalc", "check permission status 1");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i("GaussCalc", "check permission status 2");

            } else {

                Log.i("GaussCalc", "check permission status 3");

                // No explanation needed, we can request the permission.
                String[] permissions = {
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                };

                ActivityCompat.requestPermissions(this,
                        permissions, 4400);

            }
        } else {
            Log.i("GaussCalc", "check permission status 4");

              TestLoader loader = new TestLoader(GaussCalc.this);
              loader.downloadTests();
        }


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 4400:
                  TestLoader loader = new TestLoader(GaussCalc.this);
                  loader.downloadTests();
        }
    }
}