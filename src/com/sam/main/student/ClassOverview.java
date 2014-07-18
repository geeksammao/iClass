package com.sam.main.student;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.sam.R;


public class ClassOverview extends Activity {

    private ClassSelected classSelected; // 显示已选课程的fragment
    private boolean isClassSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_overview);


        if (isClassSelected == false||savedInstanceState == null){    // 当没有选课时展示默认界面、显示选课对话框
            getFragmentManager().beginTransaction().add(R.id.select_class,new NoClass()).commit();
            showAddClassDialog();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.add_class_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_class_bar:
                Intent intent = new Intent(ClassOverview.this,SelectClass.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    private void showAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加课程");
        builder.setMessage("你还没有任何课程，现在要添加课程吗");
        setPositiveButton(builder);
        setNegativeButton(builder).create().show();
    }

    private AlertDialog.Builder setPositiveButton(final AlertDialog.Builder builder){
        return  builder.setPositiveButton("添加",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ClassOverview.this,SelectClass.class);  // 打开选课界面
                startActivity(intent);
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder){
        return builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 返回
            }
        });
    }
}