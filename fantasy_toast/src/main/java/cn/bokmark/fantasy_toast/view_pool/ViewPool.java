package cn.bokmark.fantasy_toast.view_pool;

import android.content.Context;
import android.view.View;

/**
 * Created by mashuqian on 2018/4/9.
 */

public interface ViewPool {

    /**
     * 初始化
     *
     * @param appCtx 传入application context
     * @param size   pool的大小
     */
    void init(Context appCtx, int size);

    /**
     * 获取一个可用的全新的没有添加过的view
     * 如果超过size的大小将会跑出 OutOfSizeException
     */
    View get();

    /**
     * 回收view 放进pool中等待复用
     *
     * @param view 等待回收的view
     */
    void recycle(View view);
}
