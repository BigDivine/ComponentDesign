package com.divine.yang.lib_common;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class TextChangeUtils {


    public static void setText(TextView textView, int num, String text, int textsize1, int textsize2) {
        SpannableString s1 = new SpannableString(text);

        s1.setSpan(new AbsoluteSizeSpan(textsize1, true), 0, s1.length() - num, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s1.setSpan(new AbsoluteSizeSpan(textsize2, true), s1.length() - num, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setTextColor(Color.parseColor("#000000"));
        textView.setText(s1);
    }

}
