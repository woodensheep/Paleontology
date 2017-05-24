package com.nandity.paleontology.personneldata;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandity.paleontology.R;
import com.nandity.paleontology.relicdata.ui.PaleontoAdapter;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 人员数据界面的 Adapter
 *
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelAdapter.ViewHolder> {
    private Context context;
    private List<PersonnelBean> beanList;
    public OnItemClickListener mOnItemClickListener=null;

    public PersonnelAdapter(Context context, List<PersonnelBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_personnel, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final PersonnelBean item = beanList.get(position);

        viewHolder.mTextView.setText(item.getName());
        viewHolder.mTextView1.setText(item.getMobile());
        viewHolder.mTextView2.setText(item.getId());
//        viewHolder.mTextView1.setText(item.);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return null == beanList ? 0 : beanList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView, mTextView1,mTextView2;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.name_b);
            mTextView1 = (TextView) view.findViewById(R.id.mobile_b);
            mTextView2= (TextView) view.findViewById(R.id.ids);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v);
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}

