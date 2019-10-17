package com.android.ct7liang.signatureview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.android.ct7liang.signaturelib.view.Signature02View;

public class Main3Activity extends AppCompatActivity {

    private Signature02View signature02View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main3);

        signature02View = findViewById(R.id.sign_view);

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature02View.clear();
            }
        });
    }
}
