package com.salmanwahed.barcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTvResult;
    private Button mButton;
    private String mResult = null;
    private static final String KEY_STATE = "key_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvResult = findViewById(R.id.tv_code_result);
        mButton = findViewById(R.id.button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//                integrator.setCameraId(0);
//                integrator.setBeepEnabled(false);
//                integrator.setBarcodeImageEnabled(false);
//                integrator.initiateScan();

                List<String> oDesiredFormats = Arrays.asList("PDF_417".split(","));
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan(oDesiredFormats);
            }
        });
        if (mResult != null){
            mTvResult.setText(mResult);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.i("SMW", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.i("SMW", "Scanned");
                mResult = result.getContents();
                mTvResult.setText(mResult);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResult != null){
            outState.putString(KEY_STATE, mResult);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mResult = savedInstanceState.getString(KEY_STATE, null);
    }
}
