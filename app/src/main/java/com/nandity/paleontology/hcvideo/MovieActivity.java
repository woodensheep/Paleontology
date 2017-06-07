package com.nandity.paleontology.hcvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.nandity.paleontology.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.fajuequ)
    LinearLayout fajuequ;
    @BindView(R.id.zhushitu)
    LinearLayout zhushitu;
    @BindView(R.id.fajuequ4)
    LinearLayout fajuequ4;
    @BindView(R.id.fushitu)
    LinearLayout fushitu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fajuequ, R.id.zhushitu, R.id.fajuequ4, R.id.fushitu})
    public void onViewClicked(View view) {
        Intent intent=new Intent(this,HcActivity.class);
        switch (view.getId()) {
            case R.id.fajuequ:
                intent.putExtra("IP","183.230.144.221");
                intent.putExtra("PORT",8001);
                intent.putExtra("USER","admin");
                intent.putExtra("PASSWORD","a208208208");
                startActivity(intent);
                break;
            case R.id.zhushitu:
                intent.putExtra("IP","183.230.144.221");
                intent.putExtra("PORT",8004);
                intent.putExtra("USER","admin");
                intent.putExtra("PASSWORD","a208208208");
                startActivity(intent);
                break;
            case R.id.fajuequ4:
                intent.putExtra("IP","183.230.144.221");
                intent.putExtra("PORT",8003);
                intent.putExtra("USER","admin");
                intent.putExtra("PASSWORD","a208208208");
                startActivity(intent);
                break;
            case R.id.fushitu:
                intent.putExtra("IP","183.230.144.221");
                intent.putExtra("PORT",8002);
                intent.putExtra("USER","admin");
                intent.putExtra("PASSWORD","a208208208");
                startActivity(intent);
                break;
        }
    }
}
