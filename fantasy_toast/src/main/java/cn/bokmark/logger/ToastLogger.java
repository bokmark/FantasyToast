package cn.bokmark.logger;

/**
 * Created by mashuqian on 2018/3/4.
 * 输出的的内容将会以
 * =======================================
 * = [TAG] : [this is message]
 * =======================================
 * 的形式出现在设置的地方，默认出现的地方是出现在顶部
 */
public interface ToastLogger {

    /**
     * 输出红色的error信息
     *
     * @param msg 需要展示的内容
     */
    void e(String msg);

    /**
     * 输出黄色的warn信息
     *
     * @param msg 需要展示的内容
     */
    void w(String msg);

    /**
     * 输出蓝色的debug信息
     *
     * @param msg 需要展示的内容
     */
    void d(String msg);

    /**
     * 输出绿色的info信息
     *
     * @param msg 需要展示的内容
     */
    void i(String msg);

    /**
     * 输出黑色的普通信息
     *
     * @param msg 需要展示的内容
     */
    void f(String msg);

    /**
     * 根据 level 输出相应的颜色信息
     *
     * @param level LEVEL_DEFAULT = 0;
     *              LEVEL_ERROR = 1;
     *              LEVEL_INFO = 2;
     *              LEVEL_SUCCESS = 3;
     *              LEVEL_FAIL = 4;
     * @param msg 需要展示的内容
     */
    void print(int level, String msg);

    /**
     * 设置toast的tag
     * @param tag
     */
    void setTag(String tag);

    /**
     *
     * gravity 设置debug信息出现在顶部还是出现在底部
     *
     * @param gravity Gravity.TOP Gravity.BOTTOM
     */
    void setGravity(int gravity);

}
