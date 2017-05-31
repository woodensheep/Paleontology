package com.nandity.paleontology.personneldata;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandity.paleontology.FossilDate.FossiAdapter;
import com.nandity.paleontology.R;
import com.nandity.paleontology.common.Api;
import com.nandity.paleontology.relicdata.ui.PaleontoAdapter;
import com.nandity.paleontology.relicdata.util.L;

import java.util.List;



/**
 * 人员数据界面的 Adapter
 * <p>
 * Created by qingsong on 2017/5/18.
 */

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelAdapter.ViewHolder> {
    private Context context;
    private List<PersonnelBean> beanList;
    public PersonnelAdapter.OnItemClickListener mOnItemClickListener;

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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final PersonnelBean item = beanList.get(position);

        viewHolder.mTextView.setText(item.getName());
        viewHolder.mTextView1.setText(item.getIphone());
        viewHolder.mTextView2.setText(item.getCompany());
        viewHolder.mTextView3.setText(item.getPosition());
        if (TextUtils.isEmpty(item.getPhoto())) {
            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(viewHolder.imageView);
        } else {
            Glide.with(context)
                    .load(new Api(context).getPictureDataUrl() + item.getPhoto())
                    .into(viewHolder.imageView);
        }
        if (mOnItemClickListener != null) {
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return null == beanList ? 0 : beanList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView mTextView, mTextView1, mTextView2, mTextView3;
        public ImageView imageView;
       public LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.name_b);
            mTextView1 = (TextView) view.findViewById(R.id.mobile_b);
            mTextView2 = (TextView) view.findViewById(R.id.company_b);
            mTextView3 = (TextView) view.findViewById(R.id.position_b);
            imageView = (ImageView) view.findViewById(R.id.personnel_image);
            linearLayout= (LinearLayout) view.findViewById(R.id.call_image);

        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(PersonnelAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

