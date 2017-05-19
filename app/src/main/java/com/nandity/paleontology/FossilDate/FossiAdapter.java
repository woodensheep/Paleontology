package com.nandity.paleontology.FossilDate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nandity.paleontology.R;
import com.nandity.paleontology.relicdata.util.PaleontologicalaBean;

import java.util.List;


/**
 * 基础数据界面的 Adapter
 *
 * Created by qingsong on 2017/5/18.
 */

public class FossiAdapter extends RecyclerView.Adapter<FossiAdapter.PaleontoViewHolder>{

    private Context context;
    private List<PaleontologicalaBean>mPaleontoBeanList;

    public FossiAdapter(Context context, List<PaleontologicalaBean> mPaleontoBeanList){
        this.context= context;
        this.mPaleontoBeanList = mPaleontoBeanList;
    }

    @Override
    public PaleontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fossil, null);
        return new PaleontoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaleontoViewHolder holder, int position) {
        try {
          PaleontologicalaBean paleontologicalaBean = mPaleontoBeanList.get(position);
            holder.fossil_name.setText(paleontologicalaBean.getmName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPaleontoBeanList.size();
    }

    public static class PaleontoViewHolder extends RecyclerView.ViewHolder{

        private TextView fossil_name;
        private Button  foosil_btn;


        public PaleontoViewHolder(View itemView) {
            super(itemView);
            fossil_name = (TextView) itemView.findViewById(R.id.fossil_name);
            foosil_btn = (Button) itemView.findViewById(R.id.fossil_btn);

        }
    }


}
