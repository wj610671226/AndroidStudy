package CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jhtwl.safty360.R;

/**
 * Created by jhtwl on 16/8/17.
 */
public class SettingClickView extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_desc;

    public SettingClickView(Context context) {
        super(context);
        initSettingClickView();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSettingClickView();
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSettingClickView();
    }



    private void initSettingClickView() {
        View.inflate(getContext(), R.layout.setting_click_item_layout, this);
        tv_title = (TextView) findViewById(R.id.titleTv);
        tv_desc = (TextView) findViewById(R.id.desc);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setDesc(String desc) {
        tv_desc.setText(desc);
    }
}
