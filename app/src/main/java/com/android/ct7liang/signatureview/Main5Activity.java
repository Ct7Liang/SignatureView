package com.android.ct7liang.signatureview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.android.ct7liang.signaturelib.view.Signature03View;
import com.android.ct7liang.signaturelib.view.SignatureView;

public class Main5Activity extends AppCompatActivity {

    private SignatureView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main5);

        signatureView = findViewById(R.id.sign_view);

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.save();
            }
        });
    }
}
