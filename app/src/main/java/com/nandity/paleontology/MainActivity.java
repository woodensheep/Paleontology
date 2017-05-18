package com.nandity.paleontology;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    private boolean fabOpened=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fabOpened){
                    openMenu(view);
                }else{
                    closeMenu(view);
                }
            }
        });
    }


        private void openMenu(View view) {
            ObjectAnimator animator=ObjectAnimator.ofFloat(view,"rotation",0,-155,-135);
            animator.setDuration(1000);
            animator.start();
            fabOpened=true;
        }


    private void closeMenu(View view) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"rotation",-135,20,0);
        animator.setDuration(1000);
        animator.start();
        fabOpened=false;
    }


}
