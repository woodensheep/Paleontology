package com.nandity.paleontology.FossilDate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nandity.paleontology.R;
import com.nandity.paleontology.util.LogUtils;

import java.io.Serializable;
import java.util.List;


/**
 * 化石数据界面的 Adapter
 * <p>
 * Created by qingsong on 2017/5/18.
 */

public class FossiAdapter extends RecyclerView.Adapter<FossiAdapter.FossiHolder> {

    private Context context;
    private List<FossiBean> fossiBeanList;
    public FossiAdapter.OnItemClickListener mOnItemClickListener;

    public FossiAdapter(Context context, List<FossiBean> fossiBeanList) {
        this.context = context;
        this.fossiBeanList = fossiBeanList;
    }

    @Override
    public FossiAdapter.FossiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fossil, parent, false);
        return new FossiAdapter.FossiHolder(view);
    }

    @Override
    public void onBindViewHolder(FossiAdapter.FossiHolder holder, final int position) {
        try {
            FossiBean fossiBean = fossiBeanList.get(position);

            holder.fossil_name.setText(fossiBean.getFossil_no());
            LogUtils.i("TAGs", fossiBean.getFossil_no());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (mOnItemClickListener != null) {
            holder.foosil_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fossiBeanList.size();
    }

    public static class FossiHolder extends RecyclerView.ViewHolder {

        private TextView fossil_name;
        private Button foosil_btn;


        public FossiHolder(View itemView) {
            super(itemView);
            fossil_name = (TextView) itemView.findViewById(R.id.fossil_name);
            foosil_btn = (Button) itemView.findViewById(R.id.fossil_btn);

        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(FossiAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}