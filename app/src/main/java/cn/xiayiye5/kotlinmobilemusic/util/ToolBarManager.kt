package cn.xiayiye5.kotlinmobilemusic.util

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.ui.activity.SettingActivity
import org.jetbrains.anko.startActivity

/*
 * Copyright (c) 2019, smuyyh@gmail.com All Rights Reserved.
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
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */
/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2019/10/19 11:03
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.util
 */
interface ToolBarManager {
    val toolBar: Toolbar
    /**
     * 初始化主页面的toolBar
     */
    fun initIndexToolBar() {
        toolBar.setTitle("手机影音v1.0")
        toolBar.inflateMenu(R.menu.index_menu_setting)
        /*toolBar.setOnMenuItemClickListener {
            println("item=$it")
            true
        }
        toolBar.setOnMenuItemClickListener { text ->
            println("item=$text")
            true
        }*/
        toolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.index_right_setting ->
                        //跳转到设置页面
                        //方法一
                        //                        toolBar.context.startActivity<SettingActivity>()
                        //方法二
                        toolBar.context.startActivity(
                            Intent(
                                toolBar.context,
                                SettingActivity::class.java
                            )
                        )
                }
                return false
            }
        })
    }

    /*
    处理设置界面的ToolBar
     */
    fun initSettingToolBar() {
        //方法一
        toolBar.setTitle("设置")
        //方法二
//        toolBar.title = "设置"
    }
}