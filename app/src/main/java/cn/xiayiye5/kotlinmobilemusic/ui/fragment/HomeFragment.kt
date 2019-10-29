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
import cn.xiayiye5.kotlinmobilemusic.util.ThreadUtil
import cn.xiayiye5.kotlinmobilemusic.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

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
class HomeFragment : BaseFragment() {
    val adapter by lazy { HomeAdapter() }
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
        refreshLayout.setOnRefreshListener { loadData(0, false) }
        rv_recycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //空闲状态
                    val layoutManager = rv_recycleview.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        if (manager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                            //证明已滑动到最下面一个天目了
                            loadData(adapter.itemCount - 1, true)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        loadData(0, false)
    }

    private fun loadData(offset: Int, isLoadMore: Boolean) {
        val homeUrl = URLProviderUtils.getHomeUrl(offset, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(homeUrl)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                hideRefresh()
                println("调用失败" + Thread.currentThread().name)
                showFragmentToast("调用失败" + Thread.currentThread().name)
            }

            override fun onResponse(call: Call, response: Response) {
                hideRefresh()
                val result = response.body()?.string()
                println("调用成功$result")
//                showFragmentToast("调用成功$result")
                val gson = Gson()
                val list = gson.fromJson<List<HomeItemBean>>(
                    result,
                    object : TypeToken<List<HomeItemBean>>() {}.type
                )
                println("打印集合${list.size}")
                ThreadUtil.runOnMainThread(Runnable {
                    if (isLoadMore) {
                        adapter.loadMoreList(list)
                    } else {
                        adapter.updateList(list)
                    }
                })
            }
        })
    }

    fun hideRefresh() {
        ThreadUtil.runOnMainThread(Runnable {
            //隐藏刷新控件
            refreshLayout.isRefreshing = false
        })
    }
}