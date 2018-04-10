package cn.bokmark.view_pool;

import android.content.Context;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import cn.bokmark.fantasy_toast.R;

/**
 * Created by mashuqian on 2018/4/9.
 */

public class MyViewPool implements ViewPool {
    LayoutInflater layoutInflater;
    WindowManager mWM;
    Queue<View> queue;


    @Override
    public void init(Context appCtx, int size) {
        layoutInflater = LayoutInflater.from(appCtx);
        mWM = (WindowManager) appCtx.getSystemService(Context.WINDOW_SERVICE);
        queue = new ArrayBlockingQueue<View>(size);
    }

    private View createView() {
        return layoutInflater.inflate(R.layout.my_transient_notification, null);
    }



    @Override
    public View get() {
        View view  = queue.poll();
        if (view == null) {
            return createView();
        }
        if (view.getParent() != null) {
            mWM.removeView(view);
        }
        return view;
    }

    @Override
    public void recycle(View view) {

    }
}
