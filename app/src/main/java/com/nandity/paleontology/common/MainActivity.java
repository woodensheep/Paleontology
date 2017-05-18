package com.nandity.paleontology.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nandity.paleontology.R;
import com.nandity.paleontology.personneldata.PersonnelDataActivity;
import com.nandity.paleontology.util.ToActivityUtlis;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.ll_personal_data)
    LinearLayout llPersonalData;
    @BindView(R.id.action_update)
    FloatingActionButton actionUpdate;
    @BindView(R.id.action_signout)
    FloatingActionButton actionSignout;
    @BindView(R.id.actions_menu)
    FloatingActionsMenu actionsMenu;
    private boolean fabOpened = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        llPersonalData.setOnClickListener(this);

//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!fabOpened){
//                    openMenu(view);
//                }else{
//                    closeMenu(view);
//                }
//            }
//        });
    }


    private void openMenu(View view) {
        fabOpened = true;
    }


    private void closeMenu(View view) {
        fabOpened = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_update:

                break;
            case R.id.action_signout:

                break;
            case R.id.ll_personal_data:
                ToActivityUtlis.toNextActivity(MainActivity.this, PersonnelDataActivity.class);
                break;

        }
    }
}
