package cn.xiayiye5.kotlinmobilemusic.presenter.impl

import cn.xiayiye5.kotlinmobilemusic.module.HomeItemBeans
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.HomePresenter
import cn.xiayiye5.kotlinmobilemusic.util.ThreadUtil
import cn.xiayiye5.kotlinmobilemusic.util.URLProviderUtils
import cn.xiayiye5.kotlinmobilemusic.view.HomeView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 */
/**
 * @author 下一页5（轻飞扬）
 * 创建时间：2019/10/29 15:16
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.presenter.impl
 */
class HomePresenterImpl(var homeView: HomeView) : HomePresenter {
    override fun loadData(offset: Int, isLoadMore: Boolean) {
        val homeUrl = URLProviderUtils.getHomeUrl(offset, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(homeUrl)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("调用失败" + Thread.currentThread().name)
                ThreadUtil.runOnMainThread(Runnable {
                    homeView.requestFail(e.message)
                })
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                println("调用成功$result")
                val gson = Gson()
                val list = gson.fromJson<HomeItemBeans>(
                    result, object : TypeToken<HomeItemBeans>() {}.type
                )
                println("打印集合${list.data.size}")
                ThreadUtil.runOnMainThread(Runnable {
                    if (isLoadMore) {
                        homeView.loadMoreList(list.data)
                    } else {
                        homeView.updateList(list.data)
                    }
                })
            }
        })
    }
}