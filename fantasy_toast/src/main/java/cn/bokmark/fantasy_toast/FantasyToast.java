package cn.bokmark.fantasy_toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bokmark.logger.MyLogger;
import cn.bokmark.logger.ToastLogger;
import cn.bokmark.view_pool.SingleViewPool;
import cn.bokmark.view_pool.ViewPool;

/**
 * Created by mashuqian on 2018/4/2.
 * FantasyToast
 */
public class FantasyToast {
    private FantasyToast() {
    }


    private static class FantasyToastInstance {
        @SuppressLint("StaticFieldLeak")
        private static volatile FantasyToast fantasyToast = new FantasyToast();
    }

    public static FantasyToast getInstance() {
        return FantasyToastInstance.fantasyToast;
    }

    private FTH fth;


    private class FTH extends Handler {


        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SHOW) {

                String string = msg.getData().getString("string");
                int level = msg.getData().getInt("level");
                if (TextUtils.isEmpty(string) || level == 0) {
                    return;
                }

                if (toast != null) {
                    toast.cancel();
                }
                if (toast!= null && toast.getView() != null) {
                    viewPool.recycle(toast.getView());
                }
                toast = new Toast(appContext);
                View view = viewPool.get();
                TextView messageTv = (TextView) view.findViewById(R.id.toast_message);
                messageTv.setText(string);
                messageTv.setTextColor(appContext.getResources().getColor(getLevelColor(level)));
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_tip);
                imageView.setImageResource(getLevelDrawable(level));
                toast.setView(view);
                toast.show();
            }

        }
    }

    private static final int MSG_SHOW = 1231;
    private static final int MSG_HIDE = 1232;


    private Context appContext;
    private Toast toast;

    private ToastLogger logger;
    private ViewPool viewPool;

    public void init(Context context, ViewPool viewPool) {
        if (viewPool == null) {
            this.viewPool = new SingleViewPool();
        }
        appContext = context.getApplicationContext();
        this.viewPool.init(appContext, 10);
        this.logger = new MyLogger();
        fth = new FTH();

    }

    /**
     * 设置为null 将不输出log
     * @param logger
     */
    public void setLogger(ToastLogger logger) {
        this.logger = logger;
    }


    public void configLogger(String TAG) {
        if (this.logger != null) {
            this.logger.setTag(TAG);
        }
    }

    public void destroy() {
        if (fth != null) {
            fth.removeMessages(MSG_SHOW);
            fth.removeMessages(MSG_HIDE);
            fth = null;
        }
        appContext = null;
        toast = null;
    }

    private void checkContext() {
        if (appContext == null) {
            throw new RuntimeException("必须先调用init方法");
        }
    }


    public static final int LEVEL_DEFAULT = -1;
    public static final int LEVEL_ERROR = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_SUCCESS = 3;
    public static final int LEVEL_FAIL = 4;


    private int getLevelColor(int level) {
        switch (level) {
            case LEVEL_DEFAULT:
                return R.color.toast_default;
            case LEVEL_ERROR:
                return R.color.toast_error;
            case LEVEL_INFO:
                return R.color.toast_info;
            case LEVEL_SUCCESS:
                return R.color.toast_success;
            case LEVEL_FAIL:
                return R.color.toast_fail;
        }
        return R.color.toast_default;
    }



    private int getLevelDrawable(int level) {
        switch (level) {
            case LEVEL_DEFAULT:
                return R.drawable.toast_default;
            case LEVEL_ERROR:
                return R.drawable.toast_error;
            case LEVEL_INFO:
                return R.drawable.toast_info;
            case LEVEL_SUCCESS:
                return R.drawable.toast_success;
            case LEVEL_FAIL:
                return R.drawable.toast_fail;
        }
        return R.drawable.toast_default_none;
    }

    /**
     * default show use default style
     */
    public void show(String string) {
        checkContext();
        makeToast(LEVEL_DEFAULT, string);
    }

    /**
     * default show use default style
     */
    public void show(int stringRes) {
        checkContext();
        makeToast(LEVEL_DEFAULT, appContext.getString(stringRes));
    }


    /**
     * use success style to show the string
     */
    public void success(String string) {
        checkContext();
        makeToast(LEVEL_SUCCESS, string);
    }

    /**
     * use success style to show the stringRes
     */
    public void success(int stringRes) {
        checkContext();
        makeToast(LEVEL_SUCCESS, appContext.getString(stringRes));
    }


    /**
     * use error style to show the string
     */
    public void error(String string) {
        checkContext();
        makeToast(LEVEL_ERROR, string);
    }

    /**
     * use error style to show the stringRes
     */
    public void error(int stringRes) {
        checkContext();
        makeToast(LEVEL_ERROR, appContext.getString(stringRes));
    }


    /**
     * use info style to show the string
     */
    public void info(String string) {
        checkContext();
        makeToast(LEVEL_INFO, string);
    }

    /**
     * use info style to show the stringRes
     */
    public void fail(String string) {
        checkContext();
        makeToast(LEVEL_FAIL, string);
    }


    /**
     * use info style to show the stringRes
     */
    public void fail(int stringRes) {
        checkContext();
        makeToast(LEVEL_FAIL, appContext.getString(stringRes));
    }
    @SuppressLint("ShowToast")
    private void makeToast(int level, String string) {
        if (logger != null) {
            logger.print(level, string);
        }
        Message message = new Message();
        message.what = MSG_SHOW;
        Bundle bundle = new Bundle();
        bundle.putString("string", string);
        bundle.putInt("level", level);
        message.setData(bundle);
        fth.sendMessage(message);
    }

}
