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

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 人员数据界面的 Adapter
 *
 * Created by qingsong on 2017/5/18.
 */

public class DuanziAdapter extends RecyclerView.Adapter<DuanziAdapter.PersonnelViewHolder>{

    private Context context;
    private List<PersonnelBean> mPersonnelBeanList;

    public DuanziAdapter(Context context, List<PersonnelBean> duanziBeanList){
        this.context= context;
        this.mPersonnelBeanList = duanziBeanList;
    }

    @Override
    public PersonnelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personnel, null);
        return new PersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonnelViewHolder holder, int position) {
        try {
            PersonnelBean personnelBean = mPersonnelBeanList.get(position);
            Glide.with(context).load(personnelBean.getmIcon()).into(holder.mCivAvatar);
            holder.mCompany.setText(personnelBean.getmCompany());
            holder.mMobile.setText(personnelBean.getmMobile());
            holder.mName.setText(personnelBean.getmName());
            holder.mPosition.setText(personnelBean.getmPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPersonnelBeanList.size();
    }

    public static class PersonnelViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mCivAvatar;
        private TextView mName;
        private TextView mCompany;
        private TextView mMobile;
        private TextView mPosition;

        public PersonnelViewHolder(View itemView) {
            super(itemView);
            mCivAvatar = (CircleImageView) itemView.findViewById(R.id.duanzi_civ_avatar);
            mName = (TextView) itemView.findViewById(R.id.name_b);
            mCompany = (TextView) itemView.findViewById(R.id.company_b);
            mMobile = (TextView) itemView.findViewById(R.id.mobile_b);
            mPosition = (TextView) itemView.findViewById(R.id.position_b);
        }
    }


}
