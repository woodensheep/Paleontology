package com.nandity.paleontology.relicdata.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandity.paleontology.R;
import com.nandity.paleontology.personneldata.PersonnelBean;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 基础数据界面的 Adapter
 *
 * Created by qingsong on 2017/5/18.
 */

public class PaleontoAdapter extends RecyclerView.Adapter<PaleontoAdapter.PaleontoViewHolder>{

    private Context context;
    private List<PaleontologicalaBean>mPaleontoBeanList;

    public PaleontoAdapter(Context context, List<PaleontologicalaBean> mPaleontoBeanList){
        this.context= context;
        this.mPaleontoBeanList = mPaleontoBeanList;
    }

    @Override
    public PaleontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paleonto, null);
        return new PaleontoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaleontoViewHolder holder, int position) {
        try {
          PaleontologicalaBean paleontologicalaBean = mPaleontoBeanList.get(position);
            holder.paleonto_name.setText(paleontologicalaBean.getmName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPaleontoBeanList.size();
    }

    public static class PaleontoViewHolder extends RecyclerView.ViewHolder{

        private TextView paleonto_name;


        public PaleontoViewHolder(View itemView) {
            super(itemView);
            paleonto_name = (TextView) itemView.findViewById(R.id.paleonto_name);

        }
    }


}
