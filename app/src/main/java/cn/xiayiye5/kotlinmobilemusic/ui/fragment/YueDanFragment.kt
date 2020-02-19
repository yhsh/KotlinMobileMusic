package cn.xiayiye5.kotlinmobilemusic.ui.fragment

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.adapter.YueDanAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseFragment
import cn.xiayiye5.kotlinmobilemusic.module.YueDanBean
import cn.xiayiye5.kotlinmobilemusic.presenter.impl.YueDanPresenterImpl
import cn.xiayiye5.kotlinmobilemusic.view.YueDanView
import kotlinx.android.synthetic.main.fragment_list.*

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
 * 创建时间：2019/10/22 17:41
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.ui.fragment
 */
class YueDanFragment : BaseFragment(), YueDanView {
    private val adapter by lazy { YueDanAdapter() }
    private val presenter by lazy { YueDanPresenterImpl(this) }
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_list, null)
    }

    override fun initListener() {
        super.initListener()
        rvRecycleViewList.layoutManager = LinearLayoutManager(context)
        rvRecycleViewList.adapter = adapter
        //初始化刷新控件颜色
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        refreshLayout.setOnRefreshListener { presenter.loadData(0, false) }
        rvRecycleViewList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager !is LinearLayoutManager) return
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == adapter.itemCount - 1) {
                        //最后一个显示加载更多条目
                        presenter.loadData(adapter.itemCount - 1, true)
                    }
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        presenter.loadData(0, false)
    }

    override fun loadMoreList(data: YueDanBean) {
        hideRefresh()

    }

    override fun updateList(data: YueDanBean) {
        hideRefresh()
        adapter.updateList(data.playLists)
    }

    override fun requestFail(message: String?) {
        hideRefresh()
        if (activity != null) {
            Toast.makeText(activity, message + "", Toast.LENGTH_LONG).show()
        }
    }

    private fun hideRefresh() {
        refreshLayout?.let { refreshLayout.isRefreshing = false }
    }
}