package com.yisan.ui.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yisan.ui.R;
import com.yisan.ui.utils.ToastUtils;

public class AlertDialogActivity extends AppCompatActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, AlertDialogActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);


        findViewById(R.id.btn_alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal();
            }
        });


        findViewById(R.id.btn_custom_alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom();
            }
        });
    }


    /**
     * 系统自带的布局
     */
    private void normal() {
        AlertDialog alertDialog = new AlertDialog.Builder(AlertDialogActivity.this)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("确定", positiveClick)
                .setNegativeButton("取消", negativeClick)
                .create();
        alertDialog.show();
    }

    /**
     * 自定义布局的
     * setView(view)
     * setContentView(view)
     * 区别：
     * setView():替换中间布局，不能修改title的布局
     * setContentView()：完全自定义布局
     */
    private void custom() {
        final AlertDialog alertDialog = new AlertDialog.Builder(AlertDialogActivity.this)
                .setCancelable(false)
                .create();
        alertDialog.show();
        //自定义布局必须在dialog显示之后设置setContentView()的内容
        View view = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog_view, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        Button btnOk = view.findViewById(R.id.btn_ok);
        Button btnNo = view.findViewById(R.id.btn_no);
        tvTitle.setText("title");
        btnOk.setText("ok");
        btnNo.setText("no");
        alertDialog.setContentView(view);
        //设置显示的位置
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("ok");
                alertDialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("no");
                alertDialog.dismiss();
            }
        });
    }


    DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ToastUtils.show("确定");
        }
    };

    DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ToastUtils.show("取消");
        }
    };


}
