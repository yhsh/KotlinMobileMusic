package cn.xiayiye5.kotlinmobilemusic.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.xiayiye5.kotlinmobilemusic.module.YueDanBean
import cn.xiayiye5.kotlinmobilemusic.widget.LoadMoreView
import cn.xiayiye5.kotlinmobilemusic.widget.YueDanItemView

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
 * 创建时间：2020/2/19 16:42
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.adapter
 * 文件说明：
 */
class YueDanAdapter : RecyclerView.Adapter<YueDanAdapter.YueDanHolder>() {
    private var list = ArrayList<YueDanBean.PlayListsBean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YueDanHolder {
        if (viewType == 0) {
            return YueDanHolder(YueDanItemView(parent?.context))
        } else {
            return YueDanHolder(LoadMoreView(parent?.context))
        }
    }

    fun updateList(fromList: List<YueDanBean.PlayListsBean>?) {
        list.clear()
        fromList?.let {
            this.list.addAll(fromList)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (list.size == 0) {
            0
        } else {
            list.size + 1
        }
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        if (position == list.size) {
            return
        }
        val data = list.get(position)
        val yueDanItemView = holder?.itemView as YueDanItemView
        yueDanItemView.setData(data)
    }

    class YueDanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 获取不同条目的方法
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            1
        } else {
            0
        }
    }
}