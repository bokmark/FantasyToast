package cn.bokmark.fantasy_toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bokmark.logger.ToastLogger;
import cn.bokmark.view_pool.MyViewPool;
import cn.bokmark.view_pool.OutOfSizeException;
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


    private Context appContext;
    private Toast toast;
    private WindowManager mWM;

    private ToastLogger logger;
    private ViewPool viewPool;

    public void init(Context context, ViewPool viewPool) {
        if (viewPool == null) {
            this.viewPool = new SingleViewPool();
        }
        appContext = context.getApplicationContext();
        this.viewPool.init(appContext, 10);

    }

    public void setLogger(ToastLogger logger) {
        this.logger = logger;
    }

    public void destroy() {
        appContext = null;
        mWM = null;
        toast = null;
    }

    private void checkContext() {
        if (appContext == null) {
            throw new RuntimeException("必须先调用init方法");
        }
    }


    public static final int LEVEL_DEFAULT = 0;
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
    @SuppressLint("ShowToast")
    private Toast makeToast(int level, String string) {

        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(appContext);
        View view = viewPool.get();
        TextView messageTv = (TextView) view.findViewById(R.id.toast_message);
        messageTv.setText(string);
        messageTv.setTextColor(appContext.getResources().getColor(getLevelColor(level)));
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tip);
        imageView.setImageResource(getLevelDrawable(level));
        toast.setView(view);
        return toast;
    }

// this is the future function make the toast
    /*

    public void many(String string) {
        makeManyToast(LEVEL_FAIL, string);
    }

    public FantasyToast duration(int durationMS) {
        duration = durationMS;
        return FantasyToast.getInstance();
    }
*/

    /**
     * 默认toast消失值为4000
     */
    /*
    private int duration = 4000;
 */
/*

    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    @SuppressLint("ShowToast")
    private Toast makeManyToast(int level, String string) {

        mWM = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        */
/*mParams.windowAnimations = toast.getView().getAnimation().INFINITE;*//*

        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        View view = viewPool.get();

        final Configuration config = view.getContext().getResources().getConfiguration();
        final int gravity = Gravity.getAbsoluteGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, config.getLayoutDirection());
        mParams.gravity = gravity;
        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.y = 60;
        if (view.getParent() != null) {
            mWM.removeView(view);
        }

        try {

            mWM.addView(toast.getView(), mParams);
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
                    */
/* ignore *//*

        }
        return toast;
    }
*/

}
