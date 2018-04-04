package cn.bokmark.fantasy_toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


    private Context appContext;
    private Toast toast;
    private LayoutInflater layoutInflater;
    private View view;
    private WindowManager mWM;


    public void init(Context context) {
        appContext = context.getApplicationContext();
        layoutInflater = LayoutInflater.from(appContext);
        view = layoutInflater.inflate(R.layout.my_transient_notification, null);
    }

    public void destroy() {
        appContext = null;
    }

    private void checkContext() {
        if (appContext == null) {
            throw new RuntimeException("必须先调用init方法");
        }
    }


    private static final int LEVEL_DEFAULT = 0;
    private static final int LEVEL_ERROR = 1;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_SUCCESS = 3;
    private static final int LEVEL_FAIL = 4;


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
        makeToast(LEVEL_DEFAULT, string).show();
    }

    /**
     * default show use default style
     */
    public void show(int stringRes) {
        checkContext();
        makeToast(LEVEL_DEFAULT, appContext.getString(stringRes)).show();
    }


    /**
     * use success style to show the string
     */
    public void success(String string) {
        checkContext();
        makeToast(LEVEL_SUCCESS, string).show();
    }

    /**
     * use success style to show the stringRes
     */
    public void success(int stringRes) {
        checkContext();
        makeToast(LEVEL_SUCCESS, appContext.getString(stringRes)).show();
    }


    /**
     * use error style to show the string
     */
    public void error(String string) {
        checkContext();
        makeToast(LEVEL_ERROR, string).show();
    }

    /**
     * use error style to show the stringRes
     */
    public void error(int stringRes) {
        checkContext();
        makeToast(LEVEL_ERROR, appContext.getString(stringRes)).show();
    }


    /**
     * use info style to show the string
     */
    public void info(String string) {
        checkContext();
        makeToast(LEVEL_INFO, string).show();
    }

    /**
     * use info style to show the stringRes
     */
    public void fail(String string) {
        checkContext();
        makeToast(LEVEL_FAIL, string).show();
    }


    /**
     * use info style to show the stringRes
     */
    public void fail(int stringRes) {
        checkContext();
        makeToast(LEVEL_FAIL, appContext.getString(stringRes)).show();
    }

    public void many(String string) {
        makeManyToast(LEVEL_FAIL, string);
    }



    @SuppressLint("ShowToast")
    private Toast makeToast(int level, String string) {
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(appContext);
        TextView messageTv = (TextView) view.findViewById(R.id.toast_message);
        messageTv.setText(string);
        messageTv.setTextColor(appContext.getResources().getColor(getLevelColor(level)));
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tip);
        imageView.setImageResource(getLevelDrawable(level));
        toast.setView(view);
        return toast;
    }

    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    @SuppressLint("ShowToast")
    private Toast makeManyToast(int level, String string) {
        Toast toast = makeToast(level, string);

        mWM = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = toast.getView().getAnimation().INFINITE;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.verticalMargin = 10;
        mParams.horizontalMargin = 10;

        mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

        mParams.y = 64;

        try {
            mWM.removeView(toast.getView());

        } catch (Exception e) {
            e.printStackTrace();
                    /* ignore */
        }

        try {

            mWM.addView(toast.getView(), mParams);
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
                    /* ignore */
        }

/*
        if (toast != null && toast.getView() != null) {

            TextView messageTv = view.findViewById(R.id.toast_message);
            messageTv.setText(messageTv.getText().toString() + "\n" + string);
            messageTv.setTextColor(appContext.getResources().getColor(getLevelColor(level)));
            ImageView imageView = view.findViewById(R.id.iv_tip);
            imageView.setImageResource(getLevelDrawable(level));
            toast.setView(view);
        } else {

            toast = new Toast(appContext);
            TextView messageTv = view.findViewById(R.id.toast_message);
            messageTv.setText(string);
            messageTv.setTextColor(appContext.getResources().getColor(getLevelColor(level)));
            ImageView imageView = view.findViewById(R.id.iv_tip);
            imageView.setImageResource(getLevelDrawable(level));
            toast.setView(view);
        }*/
        return toast;
    }

}
