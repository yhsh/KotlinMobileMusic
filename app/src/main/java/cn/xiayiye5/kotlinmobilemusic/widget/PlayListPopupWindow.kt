package cn.xiayiye5.kotlinmobilemusic.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import cn.xiayiye5.kotlinmobilemusic.R
import org.jetbrains.anko.find

/*
 * Copyright (c) 2020, smuyyh@gmail.com All Rights Reserved.
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */

/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2020/2/23 22:15
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.widget
 * 文件说明：
 */
class PlayListPopupWindow(
    context: Context,
    popAdapter: BaseAdapter,
    listener: AdapterView.OnItemClickListener,
    private val window: Window
) : PopupWindow() {
    var oldAlpha = 1.0f

    init {
        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        contentView = view
        //获取listView
        val lvPopListView = view.find<ListView>(R.id.lvPopListView)
        lvPopListView.adapter = popAdapter
        //s设置宽高
        width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置高度为屏幕的3/5
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        height = point.y * 3 / 5
        //设置可获取焦点
        isFocusable = true
        //设置外部可点击
        isOutsideTouchable = true
        //设置背景
        setBackgroundDrawable(ColorDrawable())
        //设置点击事件
        lvPopListView.onItemClickListener = listener
        //设置popupWindow的进入和退出的动画
        animationStyle = R.style.enterAndExit
        //获取播放列表窗体透明度
        oldAlpha = window.attributes.alpha
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        //弹出了popupWindow
        //修改播放裂变透明度
        val attributes = window.attributes
        attributes.alpha = 0.4f
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        val attributes = window.attributes
        attributes.alpha = oldAlpha
        window.attributes = attributes
    }
}
