package com.lib;

import java.util.ArrayList;
import java.util.List;

import com.lib.adapter.CommonAdapter;
import com.lib.adapter.Row;
import com.lib.adapter.RowFactory;
import com.lib.model.User;
import com.lib.rowview.LinearRowView;

import android.app.ListActivity;
import android.os.Bundle;

public class TestActivity extends ListActivity implements RowFactory {
    CommonAdapter<User> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new CommonAdapter<User>(getUsers(), this);
        setListAdapter(adapter);
    }
    private List<User> getUsers() {
        List<User> users=new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
           users.add(new User(i, "zqt"+i, "zqt"+i+"@gmail.com"));
        }
        return users;
    }
    @Override
    public Row newRow() {
        return new LinearRowView(this, R.layout.user_listitem);
    }
}