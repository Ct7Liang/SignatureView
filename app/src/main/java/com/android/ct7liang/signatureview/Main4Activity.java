package com.android.ct7liang.signatureview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.android.ct7liang.signaturelib.view.Signature01View;
import com.android.ct7liang.signaturelib.view.Signature03View;

public class Main4Activity extends AppCompatActivity {

    private Signature03View signature03View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main4);

        signature03View = findViewById(R.id.sign_view);

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature03View.clear();
            }
        });
    }
}
