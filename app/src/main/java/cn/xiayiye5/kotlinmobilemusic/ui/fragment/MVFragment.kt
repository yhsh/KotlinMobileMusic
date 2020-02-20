package cn.xiayiye5.kotlinmobilemusic.ui.fragment

import android.view.View
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.adapter.MvPagerAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseFragment
import cn.xiayiye5.kotlinmobilemusic.module.MvAreaBean
import cn.xiayiye5.kotlinmobilemusic.presenter.impl.MvPresenterImpl
import cn.xiayiye5.kotlinmobilemusic.view.MvView
import kotlinx.android.synthetic.main.fragment_mv.*
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
 * #               佛祖保佑        永无BUG             #
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
class MVFragment : BaseFragment(), MvView {
    override fun onSuccess(successMsg: List<MvAreaBean>) {
        tvShowError.visibility = View.GONE
        tabLayout.visibility = View.VISIBLE
        //在fragment中套fragment获取管理者最好使用getChildFragmentManager获取
        val mvPagerAdapter = context?.let { MvPagerAdapter(it, successMsg, childFragmentManager) }
        viewpager.adapter = mvPagerAdapter
        tabLayout.setupWithViewPager(viewpager, true)
    }

    override fun onError(msg: String?) {
        toast(msg.toString())
        tvShowError.visibility = View.VISIBLE
        tabLayout.visibility = View.GONE
        tvShowError.text = msg
    }

    val presenter by lazy { MvPresenterImpl(this) }
    override fun initView(): View? = View.inflate(context, R.layout.fragment_mv, null)
    override fun initData() {
        super.initData()
        presenter.loadData();
    }

    override fun initListener() {
        super.initListener()
    }
}