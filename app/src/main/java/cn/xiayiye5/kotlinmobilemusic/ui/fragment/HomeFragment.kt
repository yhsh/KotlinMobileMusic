package cn.xiayiye5.kotlinmobilemusic.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.adapter.HomeAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseFragment
import cn.xiayiye5.kotlinmobilemusic.module.HomeItemBean
import cn.xiayiye5.kotlinmobilemusic.presenter.impl.HomePresenterImpl
import cn.xiayiye5.kotlinmobilemusic.util.ThreadUtil
import cn.xiayiye5.kotlinmobilemusic.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

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
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */
/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2019/10/22 17:41
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.ui.fragment
 */
class HomeFragment : BaseFragment(), HomeView {

    val adapter by lazy { HomeAdapter() }
    val homePresenterImpl by lazy { HomePresenterImpl(this) }
    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        tv.text = javaClass.simpleName
        return View.inflate(context, R.layout.fragment_home, null)
    }

    override fun initListener() {
        super.initListener()
        rv_recycleview.layoutManager = LinearLayoutManager(context)
        rv_recycleview.adapter = adapter
        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        //监听刷新控件刷新
        refreshLayout.setOnRefreshListener { homePresenterImpl.loadData(0, false) }
        rv_recycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //空闲状态
                    val layoutManager = rv_recycleview.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        if (manager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                            //证明已滑动到最下面一个条目了
                            homePresenterImpl.loadData(adapter.itemCount - 1, true)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        homePresenterImpl.loadData(0, false)
    }

    private fun hideRefresh() {
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
    }

    override fun loadMoreList(data: List<HomeItemBean>) {
        //隐藏刷新控件
        hideRefresh()
        adapter.loadMoreList(data)
    }

    override fun updateList(data: List<HomeItemBean>) {
        //隐藏刷新控件
        hideRefresh()
        adapter.updateList(data)
    }

    override fun requestFail(message: String?) {
        //隐藏刷新控件
        hideRefresh()
        ThreadUtil.runOnMainThread(Runnable { toast(message + "") })
    }
}