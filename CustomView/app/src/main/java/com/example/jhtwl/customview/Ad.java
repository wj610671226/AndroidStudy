package com.example.jhtwl.customview;

/**
 * Created by jhtwl on 16/9/5.
 */
public class Ad {
    private int iconResId;
    private String intro;

    public Ad(int iconResId, String intro) {
        this.iconResId = iconResId;
        this.intro = intro;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
