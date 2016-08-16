package CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jhtwl.safty360.R;

/**
 * Created by jhtwl on 16/7/28.
 */
public class SettingItemView extends RelativeLayout {

    private TextView titleView;
    private TextView descView;
    private CheckBox stateBox;

    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
//    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.jhtwl.safty360";
    // new对象的时候会调用
    public SettingItemView(Context context) {
        super(context);
        initSettingItemView();
    }

    // 设置属性时会调用
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int attributeCount = attrs.getAttributeCount();

         for (int i = 0; i < attributeCount; i++) {
         String attributeName = attrs.getAttributeName(i);
         String attributeValue = attrs.getAttributeValue(i);

         System.out.println(attributeName + "=" + attributeValue);
         }

        mTitle = attrs.getAttributeValue(NAMESPACE, "title");
        mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
        initSettingItemView();
    }

    // 设置style
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSettingItemView();
    }

//    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private void initSettingItemView() {
        View.inflate(getContext(), R.layout.seting_item, this);
        titleView = (TextView) findViewById(R.id.titleTv);
        descView = (TextView) findViewById(R.id.desc);
        stateBox = (CheckBox) findViewById(R.id.state);

        // 设置标题
        setTitle(mTitle);
    }

    private void setTitle(String title) {
        titleView.setText(title);
    }

    private void setDesc(String desc) {
        descView.setText(desc);
    }


    /*
    *   勾选状态
    * */
    public boolean isChecked() {
        return stateBox.isChecked();
    }

    public void setChecked(boolean checked) {
        stateBox.setChecked(checked);
        if (checked) {
            setDesc(mDescOn);
        } else {
            setDesc(mDescOff);
        }
    }
}
