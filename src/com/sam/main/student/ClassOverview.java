package com.sam.main.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.sam.R;
import com.sam.main.application.MyApplication;
import com.sam.main.database.MyDatabaseHelper;
import android.database.sqlite.SQLiteDatabase;
import com.sam.main.main.LoginActivity;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassOverview extends Activity {

    private ClassSelected classSelected; // 显示已选课程的fragment

    private MyDatabaseHelper dbHelper;

    private String stuID;
    private long exitTime;

    private ArrayList<String> classNames;
    private ArrayList<String> teacherNames;
    private ArrayList<String> days;

    private MyApplication myApplication;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_overview);

        myApplication = (MyApplication)getApplication();
        myApplication.addActivity(this);

        exitTime = 0;
        stuID = getIntent().getStringExtra("StuID"); // 获取登录ID
        dbHelper = new MyDatabaseHelper(this, "Class.db", null, 1);
//
//        if (savedInstanceState == null){
//            insertData(dbHelper.getReadableDatabase(),stuID);
//        }
//

        Cursor cursor_1 = dbHelper.getReadableDatabase().rawQuery("select  * from student", null);
        Cursor cursor_2 = dbHelper.getReadableDatabase().rawQuery("select * from class " +
                "where stu_id=?",new String[]{stuID});

        Log.e(getLocalClassName(), String.valueOf(cursor_1.getCount()));

        if (cursor_2.getCount() == 0) {    // 当没有选课时展示默认界面、显示选课对话框
            getFragmentManager().beginTransaction().add(R.id.select_class, new NoClass()).commit();
//          判断ID存在与否并决定是否插入数据
            if (!isIdExist(cursor_1)) {
                insertData(dbHelper.getReadableDatabase(), stuID);
            }
//            分割
            showAddClassDialog();
        }

        else {
            getFragmentManager().beginTransaction().add(R.id.select_class,new ClassSelected()).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                myApplication.finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    判断登录ID是否在数据库中存在的方法
    private boolean isIdExist(Cursor cursor) {
        Boolean isExist = true;   // 保存当前登录ID是否存在的情况
        if (cursor.getCount() == 0) {
            isExist = false;
        }
        while (cursor.moveToNext()) {
            String currentID = cursor.getString(0);
            Log.e(getLocalClassName(), currentID);
            if (currentID.equals(stuID)) {
                isExist = true;
                break;
            } else {
                isExist = false;
            }
        }
        return isExist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.add_class_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_class_bar:
                Intent intent = new Intent(ClassOverview.this, SelectClass.class);
                intent.putExtra("StuID", stuID);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    //    创建对话框询问添加课程与否
    private void showAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加课程");
        builder.setMessage("你还没有任何课程，现在要添加课程吗");
        setPositiveButton(builder);
        setNegativeButton(builder).create().show();
    }

    private AlertDialog.Builder setPositiveButton(final AlertDialog.Builder builder) {
        return builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ClassOverview.this, SelectClass.class);  // 打开选课界面
                intent.putExtra("StuID", stuID);
                startActivity(intent);
                finish();
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 返回
            }
        });
    }

    //    执行插入数据的方法
    private void insertData(SQLiteDatabase db, String stuID) {
        db.execSQL("insert into student values (?)", new String[]{stuID});

        Log.e(getClass().toString(), "insert data"); // 测试
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // 关闭数据库
        }
    }

    // 内部类fragment，用于共享外部数据
    public class ClassSelected extends Fragment implements AdapterView.OnItemClickListener {
        private ListView listView;
        private SimpleAdapter simpleAdapter;



        private List<Map<String,String>> classInfoList; // 为simpleAdapter提供数据的list

        private Cursor cursor;

        public ClassSelected() {

        }

        ;

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            cursor = dbHelper.getReadableDatabase().rawQuery("select * from class " +
                    "where stu_id=?", new String[]{stuID});
            classNames = new ArrayList<String>();
            days = new ArrayList<String>();
            teacherNames = new ArrayList<String>();
//            将课程信息装载到列表里
            while (cursor.moveToNext()) {
                String className = cursor.getString(1);
//                Log.e(getLocalClassName(),className);
                String day = cursor.getString(2);
//                Log.e(getLocalClassName(),day);
                String teacherName = cursor.getString(4);
//                Log.e(getLocalClassName(),teacherName);
                classNames.add(className);
                days.add(day);
                teacherNames.add(teacherName);
            }

            classInfoList = new ArrayList<Map<String, String>>();
            for (int i = 0;i < classNames.size();i++){
                Map<String,String> map = new HashMap<String, String>();
                map.put("className",classNames.get(i));
                map.put("day","时间： " + days.get(i));
                map.put("teacherName","老师： " + teacherNames.get(i));
                classInfoList.add(map);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.class_selected_frag, container, false);

            Log.e(getLocalClassName(),"onCreatView");

            listView = (ListView) rootView.findViewById(R.id.class_selected_list);
            simpleAdapter = new SimpleAdapter(getActivity(),classInfoList,
                    R.layout.class_selected_item,
                    new String[]{"className","day","teacherName"},
                    new int[]{R.id.class_name_selected,R.id.day_selected,R.id.teacher_name_selected});
            listView.setAdapter(simpleAdapter);
            listView.setOnItemClickListener(this);
            return rootView;
        }
//  通过点击Item查看课程详情页面，并可在该页面删除课程
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent(ClassOverview.this,ClassDelete.class);
            intent.putExtra("className",classNames.get(position)); // 将课程数据传递，用于完成新页面绘制
            intent.putExtra("day",days.get(position));
            intent.putExtra("teacherName",teacherNames.get(position));
            intent.putExtra("StuID",stuID);
            startActivity(intent);
        }
    }
   
}