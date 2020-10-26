package com.divine.yang.basecomponent.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.divine.yang.basecomponent.R;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Author: Divine
 * CreateDate: 2020/10/23
 * Describe:
 */
public class BaseToolbar {
    private Context mContext;
    private View toolbar;
    private ToolbarClickListener listener;

    private String leftText, title, rightText;
    private int leftTextResId, titleResId, rightTextResId;
    private int leftTextColor, leftBgColor, titleColor, rightTextColor, rightBgColor;
    private int leftDrawable, rightDrawable;
    private ConstraintLayout headerContainLayout;
    private LinearLayout leftLayout, centerLayout, rightLayout;

    public BaseToolbar(Context context) {
        mContext = context;
        initToolbar();
    }

    public void setToolbarClickListener(ToolbarClickListener listener) {
        this.listener = listener;
    }

    public void setLeftText(String leftText) {
        TextView backText = leftLayout.findViewById(R.id.action_bar_back_text);
        backText.setText(leftText);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.leftClick();
            }
        });
        this.leftText = leftText;
    }

    public void setLeftText(int leftTextResId) {
        TextView backText = leftLayout.findViewById(R.id.action_bar_back_text);
        backText.setText(leftTextResId);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.leftClick();
            }
        });
        this.leftTextResId = leftTextResId;
    }

    public void setLeftDrawable(int leftDrawable) {
        ImageButton backBtn = leftLayout.findViewById(R.id.action_bar_back);
        backBtn.setImageResource(leftDrawable);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.leftClick();
            }
        });
        this.leftDrawable = leftDrawable;
    }

    public void setLeftVisible(boolean isVisible) {
        if (isVisible) {
            leftLayout.setVisibility(View.VISIBLE);
        } else {
            leftLayout.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        TextView headerTitle = centerLayout.findViewById(R.id.action_bar_title);
        headerTitle.setText(title);
        this.title = title;
    }

    public void setTitle(int titleResId) {
        TextView headerTitle = centerLayout.findViewById(R.id.action_bar_title);
        headerTitle.setText(titleResId);
        this.titleResId = titleResId;
    }

    public void setTitleColor(int titleColor) {
        TextView headerTitle = centerLayout.findViewById(R.id.action_bar_title);
        headerTitle.setTextColor(titleColor);
        this.titleColor = titleColor;
    }

    public void setCenterVisible(boolean isVisible) {
        if (isVisible) {
            centerLayout.setVisibility(View.VISIBLE);
        } else {
            centerLayout.setVisibility(View.GONE);
        }
    }

    public void setRightText(String rightText) {
        Button menuText = rightLayout.findViewById(R.id.action_bar_menu_text);
        ImageView menuImg = rightLayout.findViewById(R.id.action_bar_menu);
        menuText.setVisibility(View.VISIBLE);
        menuImg.setVisibility(View.GONE);
        menuText.setText(rightText);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.rightClick();
            }
        });
        this.rightText = rightText;
    }

    public void setRightText(int rightTextResId) {
        Button menuText = rightLayout.findViewById(R.id.action_bar_menu_text);
        ImageView menuImg = rightLayout.findViewById(R.id.action_bar_menu);
        menuText.setVisibility(View.VISIBLE);
        menuImg.setVisibility(View.GONE);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.rightClick();
            }
        });
        this.rightTextResId = rightTextResId;
    }

    public void setRightTextColor(int rightTextColor) {
        Button menuText = rightLayout.findViewById(R.id.action_bar_menu_text);
        menuText.setTextColor(rightTextColor);
        this.rightTextColor = rightTextColor;
    }

    public void setRightBgColor(int rightBgColor) {
        Button menuText = rightLayout.findViewById(R.id.action_bar_menu_text);
        menuText.setBackgroundColor(rightBgColor);
        this.rightBgColor = rightBgColor;
    }

    public void setRightDrawable(int rightDrawable) {
        TextView menuText = rightLayout.findViewById(R.id.action_bar_menu_text);
        ImageView menuImg = rightLayout.findViewById(R.id.action_bar_menu);
        menuText.setVisibility(View.GONE);
        menuImg.setVisibility(View.VISIBLE);
        menuImg.setImageResource(rightDrawable);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.rightClick();
            }
        });
        this.rightDrawable = rightDrawable;
    }

    public void setRightVisible(boolean isVisible) {
        if (isVisible) {
            rightLayout.setVisibility(View.VISIBLE);
        } else {
            rightLayout.setVisibility(View.GONE);
        }
    }

    public void initToolbar() {
        toolbar = LayoutInflater.from(mContext).inflate(R.layout.action_bar_layout, null, false);
        headerContainLayout = toolbar.findViewById(R.id.action_bar_layout);
        leftLayout = toolbar.findViewById(R.id.action_bar_left);
        leftLayout.setVisibility(View.VISIBLE);
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.leftClick();
            }
        });

        centerLayout = toolbar.findViewById(R.id.action_bar_center);
        centerLayout.setVisibility(View.VISIBLE);
        TextView headerTitle = centerLayout.findViewById(R.id.action_bar_title);
        headerTitle.setText(title);

        rightLayout = toolbar.findViewById(R.id.action_bar_right);
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.rightClick();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.setLayoutParams(params);
    }

    public View getToolbar() {
        return toolbar;
    }

    public ToolbarClickListener getListener() {
        return listener;
    }

    public ConstraintLayout getHeaderContainLayout() {
        return headerContainLayout;
    }

    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    public LinearLayout getCenterLayout() {
        return centerLayout;
    }

    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    public String getLeftText() {
        return leftText;
    }

    public String getTitle() {
        return title;
    }

    public String getRightText() {
        return rightText;
    }

    public int getLeftTextResId() {
        return leftTextResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getRightTextResId() {
        return rightTextResId;
    }

    public int getLeftDrawable() {
        return leftDrawable;
    }

    public int getRightDrawable() {
        return rightDrawable;
    }


}
