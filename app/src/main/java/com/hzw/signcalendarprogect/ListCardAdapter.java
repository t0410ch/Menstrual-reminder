package com.hzw.signcalendarprogect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListCardAdapter extends BaseAdapter {

    private Context mContext;
    private List<DingDan> mList = new ArrayList<DingDan>();

    class ViewHolder {
        TextView dingdan_bianhao, dingdan_xingming, dingdan_dianhua, dingdan_dizhi;
        ImageView dingdan_tupian, dingdan_bianji, dingdan_shanchu;

    }

    public ListCardAdapter(Context context, List<DingDan> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //初始化卡片样式
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.control_listcard, null);
            viewHolder.dingdan_tupian = view.findViewById(R.id.dingdan_tupian);
            viewHolder.dingdan_bianji = view.findViewById(R.id.dingdan_bianji);
            viewHolder.dingdan_shanchu = view.findViewById(R.id.dingdan_shanchu);
            viewHolder.dingdan_bianhao = view.findViewById(R.id.dingdan_bianhao);
            viewHolder.dingdan_xingming = view.findViewById(R.id.dingdan_xingming);
            viewHolder.dingdan_dianhua = view.findViewById(R.id.dingdan_dianhua);
            viewHolder.dingdan_dizhi = view.findViewById(R.id.dingdan_dizhi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //加载卡片数据
        viewHolder.dingdan_tupian.setImageResource(mList.get(i).getTupian());
        viewHolder.dingdan_bianhao.setText(mList.get(i).getBianhao());
        viewHolder.dingdan_xingming.setText(mList.get(i).getXingming());
        viewHolder.dingdan_dianhua.setText(mList.get(i).getDianhua());
        viewHolder.dingdan_dizhi.setText(mList.get(i).getDizhi());

        //定义编辑图标的点击事件
        viewHolder.dingdan_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemUpdateListener.onUpdateClick(i);
            }
        });

        //创建删除图标的点击事件
        viewHolder.dingdan_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });

        return view;
    }

    /**
     * 删除订单的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }


    /**
     * 修改订单的监听接口
     */
    public interface onItemUpdateListener {
        void onUpdateClick(int i);
    }

    private onItemUpdateListener mOnItemUpdateListener;

    public void setOnItemUpdateClickListener(onItemUpdateListener mOnItemUpdateListener) {
        this.mOnItemUpdateListener = mOnItemUpdateListener;
    }
}
