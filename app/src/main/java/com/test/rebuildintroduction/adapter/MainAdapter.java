package com.test.rebuildintroduction.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.test.rebuildintroduction.R;
import com.test.rebuildintroduction.base.BaseViewHolder;
import com.test.rebuildintroduction.bean.GuideBean;
import com.test.rebuildintroduction.databinding.MainItemBinding;
import com.test.rebuildintroduction.utils.ExpandableViewHoldersUtil;

import java.util.List;

/**
 * 大屏幕设备Recycler 适配器
 *
 * @author sm2886
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
//public class MainAdapter extends BaseAdapter<MainItemBinding, MainAdapter.MainViewHolder> {

    private int expandedPosition = -1;
    boolean isExpanded;
    private OnItemClickListener mClick;
    private final List<GuideBean> mList;


    public ExpandableViewHoldersUtil.KeepOneH<MainViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<>();

    public interface OnItemClickListener {
        /**
         * 回调
         *
         * @param view       View
         * @param position   点击位置
         * @param isExpanded 记录位置
         */
        void onItemClick(View view, int position, boolean isExpanded);
    }

    public MainAdapter(List<GuideBean> list) {
        this.mList = list;
    }

    public MainAdapter(List<GuideBean> list, OnItemClickListener clickListener) {
        this.mList = list;
        this.mClick = clickListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MainItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_item,parent,false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int pos) {
        final GuideBean contact = mList.get(pos);
        holder.bind(pos, contact.getGuideShowTitle(), contact.getGuideShowDetail());
        holder.binding.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keepOne.toggle(holder, holder.binding.iv1);
                isExpanded = expandedPosition == holder.getAdapterPosition();
                if (mClick != null) {
                    mClick.onItemClick(view, holder.getAdapterPosition(), isExpanded);
                }
                expandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MainViewHolder extends BaseViewHolder<MainItemBinding> implements ExpandableViewHoldersUtil.Expandable {
        private final MainItemBinding binding;

        public MainViewHolder(MainItemBinding binding) {
            super(binding);
            this.binding = binding;
        }
        public void bind(int pos, String name, String color) {
            binding.contactName.setText(name);
            binding.infos.setText(color);
            keepOne.bind(this, pos);
        }
        @Override
        public View getExpandView() {
            return binding.infos;
        }
    }
}
