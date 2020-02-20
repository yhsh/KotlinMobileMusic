package cn.xiayiye5.kotlinmobilemusic.ui.fragment

import cn.xiayiye5.kotlinmobilemusic.adapter.MvListAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseListAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseListFragment
import cn.xiayiye5.kotlinmobilemusic.module.MvPagerBean
import cn.xiayiye5.kotlinmobilemusic.module.VideoPlayBean
import cn.xiayiye5.kotlinmobilemusic.module.VideosBean
import cn.xiayiye5.kotlinmobilemusic.presenter.impl.MvListPresenterImpl
import cn.xiayiye5.kotlinmobilemusic.presenter.interf.BaseListPresenter
import cn.xiayiye5.kotlinmobilemusic.ui.activity.VideoPlayerActivity
import cn.xiayiye5.kotlinmobilemusic.view.MvListView
import cn.xiayiye5.kotlinmobilemusic.widget.MvItemView
import org.jetbrains.anko.support.v4.startActivity

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
 * 创建时间：2020/2/20 12:18
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.ui.fragment
 * 文件说明：每个viewpager的fragment
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), MvListView {
    var code: String? = null
    override fun init() {
        super.init()
        code = arguments?.getString("args")
    }

    override fun getList(data: MvPagerBean?): List<VideosBean>? {
        return data?.videos
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun initListener() {
        super.initListener()
        adapter.setMyListener {
            val videoPlayBean = VideoPlayBean(it.id, it.title, it.url)
            startActivity<VideoPlayerActivity>("item" to videoPlayBean)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        basePresenterImpl.destroyView()
    }
}