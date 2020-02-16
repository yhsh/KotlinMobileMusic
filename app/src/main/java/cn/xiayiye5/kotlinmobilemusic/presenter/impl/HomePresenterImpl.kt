package cn.xiayiye5.kotlinmobilemusic.presenter.impl

import cn.xiayiye5.kotlinmobilemusic.module.HomeItemBeans
import cn.xiayiye5.kotlinmobilemusic.net.HomeRequest
import cn.xiayiye5.kotlinmobilemusic.net.ResponseHandler
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.HomePresenter
import cn.xiayiye5.kotlinmobilemusic.view.HomeView

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
class HomePresenterImpl(var homeView: HomeView?) : HomePresenter, ResponseHandler<HomeItemBeans> {
    var TYPE_INIT_OR_REFRESH = 1
    var TYPE_LOAD_MORE = 2
    override fun onError(type: Int, msg: String?) {
        homeView?.requestFail(msg)
    }

    override fun onSuccess(type: Int, successMsg: HomeItemBeans) {
        if (type == TYPE_LOAD_MORE) {
            homeView?.loadMoreList(successMsg.data)
        } else {
            homeView?.updateList(successMsg.data)
        }
    }

    override fun loadData(offset: Int, isLoadMore: Boolean) {
        if (isLoadMore) {
            HomeRequest(TYPE_INIT_OR_REFRESH, offset, this).execute()
        } else {
            HomeRequest(TYPE_LOAD_MORE, offset, this).execute()
        }

    }

    /**
     * 解绑view的操作
     */
    fun destroyView() {
        if (homeView != null) {
            homeView = null
        }
    }
}