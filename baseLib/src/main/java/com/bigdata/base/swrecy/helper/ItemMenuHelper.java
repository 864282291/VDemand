package com.bigdata.base.swrecy.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import com.bigdata.base.R;
import com.bigdata.base.swrecy.inter.SwipeMenuCreator;
import com.bigdata.base.swrecy.recycle.SwipeMenu;
import com.bigdata.base.swrecy.recycle.SwipeMenuItem;

/**
 * Created by Leo on 2017/7/14.
 */

public class ItemMenuHelper {

    //删除
    public static final int VIEW_DELETE = 0x0005;
    //正常
    public static final int VIEW_NORMAL = VIEW_DELETE + 1;

    private static Context mContext;

    private static ItemMenuHelper instance = null;

//    private static HttpInterface okHttpFaceHelper = null;

    private static ItemMenuHelper client = new ItemMenuHelper();

    public static ItemMenuHelper getInstance(Context context) {
        if (instance != null) {
            instance = null;
        }
        mContext = context;
        if (instance == null) {
            synchronized (ItemMenuHelper.class) {
                if (instance == null) {
                    instance = new ItemMenuHelper();
                }
            }
        }

        return instance;
    }

    /**
     * 获取删除菜单方法
     *
     * @return
     */
    public SwipeMenuCreator getMenuDel() {
        return client.swipeDelMenuCreator;
    }

    /**
     * 获取类型删除菜单方法
     * @return
     */
    public SwipeMenuCreator getMenuTypeDel(){
        return client.swipeTypeDelMenuCreator;
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeDelMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
//                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeTypeDelMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            if (viewType == VIEW_DELETE) {// 根据Adapter的ViewType来决定菜单的样式、颜色等属性、或者是否添加菜单。
                // Do nothing.
            } else if (viewType == VIEW_NORMAL) {// 需要添加单个菜单的Item。
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };
}
