package com.lib.rowview;


import java.util.Map;

import com.lib.R;
import com.lib.adapter.Row;
import com.lib.model.User;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearRowView extends LinearLayout implements Row{
    User user;
    TextView nameTV;
    TextView emailTV;
    public LinearRowView(Context context,int layoutId) {
        super(context);
        View.inflate(context, layoutId, this);
    }

    @Override
    public void init(int position, Object item) {
        nameTV=(TextView)findViewById(R.id.name_tv);
        emailTV=(TextView)findViewById(R.id.email_tv);
    }

    @Override
    public void update(int position, Object item) {
        user=(User)item;
        nameTV.setText(user.getName());
    }


}
