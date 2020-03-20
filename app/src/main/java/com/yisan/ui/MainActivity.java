package com.yisan.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yisan.ui.ui.AlertDialogActivity;
import com.yisan.ui.ui.PopupWindowActivity;


/**
 * UI控件demo
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_to_alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogActivity.show(MainActivity.this);
            }
        });


        findViewById(R.id.btn_to_progress_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        findViewById(R.id.btn_to_popup_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowActivity.show(MainActivity.this);

            }
        });

    }
}
