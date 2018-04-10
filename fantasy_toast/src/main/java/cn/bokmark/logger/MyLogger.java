package cn.bokmark.logger;

import android.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;
import static cn.bokmark.fantasy_toast.FantasyToast.LEVEL_DEFAULT;
import static cn.bokmark.fantasy_toast.FantasyToast.LEVEL_ERROR;
import static cn.bokmark.fantasy_toast.FantasyToast.LEVEL_FAIL;
import static cn.bokmark.fantasy_toast.FantasyToast.LEVEL_INFO;
import static cn.bokmark.fantasy_toast.FantasyToast.LEVEL_SUCCESS;

/**
 * Created by mashuqian on 2018/4/10.
 * <p>
 * 输出的的内容将会以
 * =======================================
 * = [this is message]
 * =======================================
 * 的形式出现
 */
public class MyLogger implements ToastLogger {

    private String TAG = "MyLogger";


    @Override
    public void e(String msg) {
        print(LEVEL_ERROR, msg);
    }

    @Override
    public void w(String msg) {
        print(LEVEL_FAIL, msg);

    }

    @Override
    public void d(String msg) {
        print(LEVEL_DEFAULT, msg);
    }

    @Override
    public void i(String msg) {
        print(LEVEL_INFO, msg);
    }

    @Override
    public void f(String msg) {
        print(LEVEL_SUCCESS, msg);
    }

    @Override
    public void print(int level, String msg) {
        Log.println(convert(level), TAG, "==================================================");
        Log.println(convert(level), TAG, "= " + msg);
        Log.println(convert(level), TAG, "==================================================");
    }

    /**
     * public static final int VERBOSE = 2;
     * <p>
     * public static final int DEBUG = 3;
     * <p>
     * public static final int INFO = 4;
     * <p>
     * public static final int WARN = 5;
     * <p>
     * public static final int ERROR = 6;
     * <p>
     * public static final int ASSERT = 7;
     *
     * @param level fantasy toast level
     * @return log level
     */
    private int convert(int level) {
        switch (level) {
            case LEVEL_DEFAULT:
                return VERBOSE;
            case LEVEL_FAIL:
                return WARN;
            case LEVEL_SUCCESS:
                return DEBUG;
            case LEVEL_ERROR:
                return ERROR;
            case LEVEL_INFO:
                return INFO;
        }
        return VERBOSE;
    }

    @Override
    public void setTag(String tag) {
        TAG = tag;
    }


    /**
     * 这个功能将在下个版本中实现
     *
     * @param gravity Gravity.TOP Gravity.BOTTOM
     */
    @Override
    @Deprecated
    public void setGravity(int gravity) {

    }
}
