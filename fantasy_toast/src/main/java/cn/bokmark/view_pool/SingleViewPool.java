package cn.bokmark.view_pool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import cn.bokmark.fantasy_toast.R;

/**
 * Created by mashuqian on 2018/4/10.
 */

public class SingleViewPool implements ViewPool {


    private WindowManager mWM;
    private View view;


    @Override
    public void init(Context appCtx, int size) {

        mWM = (WindowManager) appCtx.getSystemService(Context.WINDOW_SERVICE);
        view = LayoutInflater.from(appCtx).inflate(R.layout.my_transient_notification, null);
    }

    @Override
    public View get() {
        return view;
    }

    @Override
    public void recycle(View view) {
        if (view.getParent() != null) {
            mWM.removeView(view);
        }
    }
}
