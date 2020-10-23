package com.divine.yang.basecomponent.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.divine.yang.basecomponent.R;

import androidx.annotation.Nullable;

/**
 * Author: Divine
 * CreateDate: 2020/10/23
 * Describe:
 */
public class BaseToolbar {
    private Context mContext;
    private View toolbar;
    private ToolbarClickListener listener;

    private String title;
    private LinearLayout leftLayout, centerLayout, rightLayout;

    public BaseToolbar(Context context) {
        mContext = context;
    }

    public void setToolbarClickListener(ToolbarClickListener listener) {
        this.listener = listener;
    }

    public View getToolbar() {

        View actionBar = LayoutInflater.from(mContext).inflate(R.layout.action_bar_layout, null, false);
        leftLayout = actionBar.findViewById(R.id.action_bar_left);
        leftLayout.setVisibility(View.VISIBLE);
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });

        centerLayout = actionBar.findViewById(R.id.action_bar_center);
        centerLayout.setVisibility(View.VISIBLE);
        TextView headerTitle = centerLayout.findViewById(R.id.action_bar_title);
        headerTitle.setText(title);

        rightLayout = actionBar.findViewById(R.id.action_bar_right);
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        actionBar.setLayoutParams(params);
        return actionBar;
    }

    private CharSequence getTitle() {
        return title;
    }
}
