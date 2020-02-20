package cn.xiayiye5.kotlinmobilemusic.base

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.BaseListPresenter
import kotlinx.android.synthetic.main.fragment_home.*

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
 * 创建时间：2020/2/19 19:57
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.base
 * 文件说明：所有页面的上拉刷新下拉加载更多的P层基类
 */
abstract class BaseListFragment<RESPONSE_DATA, ITEM_BEAN, ITEM_VIEW : View> : BaseFragment(),
    BaseView<RESPONSE_DATA> {
    val adapter by lazy { getSpecialAdapter() }
    val basePresenterImpl by lazy { getSpecialPresenter() }
    override fun initView(): View? = View.inflate(context, R.layout.fragment_home, null)

    override fun initListener() {
        super.initListener()
        rvRecycleViewList.layoutManager = LinearLayoutManager(context)
        rvRecycleViewList.adapter = adapter
        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        //监听刷新控件刷新
        refreshLayout.setOnRefreshListener { basePresenterImpl.loadData(0, false) }
        rvRecycleViewList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //空闲状态
                    val layoutManager = rvRecycleViewList.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        if (manager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                            //证明已滑动到最下面一个条目了
                            basePresenterImpl.loadData(adapter.itemCount - 1, true)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        basePresenterImpl.loadData(0, false)
    }

    private fun hideRefresh() {
        //隐藏刷新控件
        refreshLayout?.let {
            refreshLayout.isRefreshing = false
        }
    }

    override fun loadMoreList(data: RESPONSE_DATA?) {
        //隐藏刷新控件
        hideRefresh()
        adapter.loadMoreList(getList(data))
        tvShowError.visibility = View.GONE
    }

    override fun updateList(data: RESPONSE_DATA?) {
        //隐藏刷新控件
        hideRefresh()
        adapter.updateList(getList(data))
        tvShowError.visibility = View.GONE
    }

    abstract fun getList(data: RESPONSE_DATA?): List<ITEM_BEAN>?

    /**
     * 获取适配器的adapter
     */
    abstract fun getSpecialAdapter(): BaseListAdapter<ITEM_BEAN, ITEM_VIEW>

    /**
     * 获取Presenter的方法
     */
    abstract fun getSpecialPresenter(): BaseListPresenter

    override fun requestFail(message: String?) {
        //隐藏刷新控件
        hideRefresh()
        if (activity != null) {
            Toast.makeText(activity, message + "", Toast.LENGTH_LONG).show()
        }
        tvShowError.visibility = View.VISIBLE
        tvShowError.text = message
    }
}