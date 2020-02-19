package cn.xiayiye5.kotlinmobilemusic.presenter.impl

import cn.xiayiye5.kotlinmobilemusic.base.BaseView
import cn.xiayiye5.kotlinmobilemusic.module.YueDanBean
import cn.xiayiye5.kotlinmobilemusic.net.ResponseHandler
import cn.xiayiye5.kotlinmobilemusic.net.YueDanRequest
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.BaseListPresenter
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.YueDanListPresenter

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
 * 创建时间：2020/2/19 17:08
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.presenter.impl
 * 文件说明：
 */
class YueDanListPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanListPresenter,
    ResponseHandler<YueDanBean> {

    override fun onError(type: Int, msg: String?) {
        yueDanView?.requestFail(msg)
    }

    override fun onSuccess(type: Int, successMsg: YueDanBean) {
        if (type == BaseListPresenter.TYPE_LOAD_MORE) {
            yueDanView?.loadMoreList(successMsg)
        } else {
            yueDanView?.updateList(successMsg)
        }
    }

    override fun loadData(offset: Int, isLoadMore: Boolean) {
        if (isLoadMore) {
            YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
        } else {
            YueDanRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, offset, this).execute()
        }
    }

    /**
     * 解绑View的方法
     */
    override fun destroyView() {
        if (yueDanView != null) {
            yueDanView == null
        }
    }
}