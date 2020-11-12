package com.divine.yang.lib_common;

import android.widget.PopupWindow;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class PopupUtil {

    private static PopupWindow mPopupWindow;

    //    private static TipsView mTipsView;
    //
    //    private static PopupYearAdapter popupYearAdapter;
    //
    //    public static void showTips(Context context, View mTvBindFlag, final List<String> list, int height
    //            , final PopupOnItemClick popupOnItemClick, String unit) {
    //        mTipsView = new TipsView(context);
    //        mPopupWindow = new PopupWindow(mTipsView, RelativeLayout.LayoutParams.MATCH_PARENT,
    //                                       height, true);
    //        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
    //        mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    //        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color
    //                .transparent));
    //        mPopupWindow.setFocusable(true);
    //        mPopupWindow.setOutsideTouchable(true);
    //
    //        popupYearAdapter = new PopupYearAdapter(context,R.layout.item_popup_year,list,unit);
    //        RelativeLayout popup_layout = mTipsView.findViewById(R.id.popup_layout);
    //        RecyclerView popup_recyclerView = mTipsView.findViewById(R.id.popup_recyclerView);
    //        popup_recyclerView.setLayoutManager(new LinearLayoutManager(context));
    //        popup_recyclerView.setAdapter(popupYearAdapter);
    //        popup_layout.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                mPopupWindow.dismiss();
    //            }
    //        });
    //        popupYearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
    //            @Override
    //            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    //                popupOnItemClick.popupItemClick(list.get(position));
    //                mPopupWindow.dismiss();
    //            }
    //        });
    //
    //        mPopupWindow.showAsDropDown(mTvBindFlag, 0, 0);
    //    }
}
