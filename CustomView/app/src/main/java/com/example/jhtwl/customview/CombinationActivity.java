package com.example.jhtwl.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class CombinationActivity extends AppCompatActivity {

    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;

    private boolean isShowLevel2 = true;
    private boolean isShowLevel3 = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
    }

    public void ckickHome(View view) {
        int startOffset = 0;
        if (isShowLevel3) { // 判断level3是否打开
            closeAnimation(level3, startOffset);
            startOffset += 300;
            if (isShowLevel2) {
                closeAnimation(level2, startOffset);
                isShowLevel2 = !isShowLevel2;
            }
        } else {
            if (!isShowLevel2) {
                showAnimation(level2, startOffset);
                startOffset += 200;
                isShowLevel2 = !isShowLevel2;
            }
            showAnimation(level3, startOffset);
        }
        isShowLevel3 = !isShowLevel3;
    }

    public void ckickMenu(View view) {
        if (isShowLevel3) {
            closeAnimation(level3, 0);
        } else  {
            showAnimation(level3, 0);
        }
        isShowLevel3 = !isShowLevel3;
    }


    public void closeAnimation(RelativeLayout r, long startOffset) {
        for (int i = 0; i < r.getChildCount(); i ++) {
            // 禁止交互
            r.getChildAt(i).setEnabled(false);
        }

        RotateAnimation rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(startOffset);
        r.startAnimation(rotateAnimation);
    }

    public void showAnimation(RelativeLayout r, long startOffset) {
        for (int i = 0; i < r.getChildCount(); i ++) {
            // 开启交互
            r.getChildAt(i).setEnabled(true);
        }

        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(startOffset);
        r.startAnimation(rotateAnimation);
    }
}
