package com.sam.main.student;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.sam.R;

/**
 * 选课的主界面
 * 用于显示所有课程
 */
public class SelectClass extends Activity implements SearchView.OnQueryTextListener {

    private SearchView searchView; // 用于搜索课程的搜索框
    private ListView svShowList; // 展示自动完成的列表
    private String [] classToSelect; // 可供选择的课程

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_class);

        initView();
    }

    private void initView() {
        classToSelect = new String[] {"离散数学","C语言程序设计","微积分（上）","国际金融与商业概述","电工实习","面向对象技术与设计","数据库技术","软件工程概论","线性代数","电路理论"};
        searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        svShowList = (ListView)findViewById(R.id.search_list);
        svShowList.setAdapter(new ArrayAdapter<String>(this,R.layout.class_list_item,classToSelect));
        svShowList.setTextFilterEnabled(true);
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        if (TextUtils.isEmpty(newText)){
            svShowList.clearTextFilter();  // 清除listView的过滤
        }
        else {
            svShowList.setFilterText(newText); // 使用用户输入内容对listview列表项进行过滤
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}