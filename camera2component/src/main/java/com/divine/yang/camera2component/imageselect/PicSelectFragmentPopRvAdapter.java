package com.divine.yang.camera2component.imageselect;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divine.yang.camera2component.R;
import com.divine.yang.camera2component.imageselect.interfaces.OnFolderChangeListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
public class PicSelectFragmentPopRvAdapter extends RecyclerView.Adapter<PicSelectFragmentPopRvViewHolder> {
    private Context mContext;
    private List<Folder> data;
    private PicSelectConfig config;
    private int selected = 0;

    private OnFolderChangeListener listener;

    public PicSelectFragmentPopRvAdapter(Context mContext, List<Folder> data, PicSelectConfig config) {
        this.mContext = mContext;
        this.data = data;
        this.config = config;
    }

    @NonNull
    @Override
    public PicSelectFragmentPopRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pic_select_fragment_pop_rv_item, parent);
        PicSelectFragmentPopRvViewHolder mPicSelectFragmentPopRvViewHolder = new PicSelectFragmentPopRvViewHolder(rootView);
        return mPicSelectFragmentPopRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicSelectFragmentPopRvViewHolder holder, int position) {
        Folder itemData = this.data.get(position);
        if (position == 0) {
            holder.mAdapterPicSelectFragmentPopRvItemTitle.setText("所有图片");
            holder.mAdapterPicSelectFragmentPopRvItemNum.setText("共" + getTotalImageSize() + "张");
            if (data.size() > 0) {
                holder.mAdapterPicSelectFragmentPopRvItemImg.setImageBitmap(BitmapFactory.decodeFile(itemData.cover.path));
            }
        } else {
            holder.mAdapterPicSelectFragmentPopRvItemTitle.setText(itemData.name);
            holder.mAdapterPicSelectFragmentPopRvItemNum.setText("共" + itemData.images.size() + "张");
            if (data.size() > 0) {
                holder.mAdapterPicSelectFragmentPopRvItemImg.setImageBitmap(BitmapFactory.decodeFile(itemData.cover.path));
            }
        }

        if (selected == position) {
            holder.mAdapterPicSelectFragmentPopRvItemSelect.setVisibility(View.VISIBLE);
        } else {
            holder.mAdapterPicSelectFragmentPopRvItemSelect.setVisibility(View.GONE);
        }


        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectIndex(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != this.data ? this.data.size() : 0;
    }


    public void setData(List<Folder> folders) {
        data.clear();
        if (folders != null && folders.size() > 0) {
            data.addAll(folders);
        }
        notifyDataSetChanged();
    }

    private int getTotalImageSize() {
        int result = 0;
        if (data != null && data.size() > 0) {
            for (Folder folder : data) {
                result += folder.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int position) {
        if (selected == position)
            return;
        if (listener != null)
            listener.onChange(position, data.get(position));
        selected = position;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selected;
    }

    public void setOnFolderChangeListener(OnFolderChangeListener listener) {
        this.listener = listener;
    }
}

