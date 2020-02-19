package cn.xiayiye5.kotlinmobilemusic.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.xiayiye5.kotlinmobilemusic.widget.LoadMoreView

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
 * 创建时间：2020/2/19 20:15
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.base
 * 文件说明：
 */
abstract class BaseListAdapter<ITEM_BEAN, ITEM_VIEW : View> :
    RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {
    private var list = ArrayList<ITEM_BEAN>()
    //更新数据的方法
    fun updateList(list: List<ITEM_BEAN>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    //加载更多数据的方法
    fun loadMoreList(list: List<ITEM_BEAN>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListHolder {
        if (viewType == 1) {
            //返回刷新进度条布局
            return BaseListHolder(LoadMoreView(parent?.context))
        } else {
            //返回正常条目布局
            return BaseListHolder(getItemView(parent?.context))
        }
    }

    override fun getItemCount(): Int {
        if (list.size == 0) {
            return 0
        } else {
            return list.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            //返回最后一条数据进度条布局
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: BaseListHolder, position: Int) {
        //等于最后一条数据不刷新
        if (position == list.size) {
            return
        }
        //获取条目数据
        val data = list.get(position)
        //获取条目view
        val itemView = holder.itemView as ITEM_VIEW
        //刷新条目数据
        refreshItemView(itemView, data)
    }

    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 刷新条目的view
     */
    abstract fun refreshItemView(itemView: ITEM_VIEW, data: ITEM_BEAN)

    /**
     * 获取条目的viw
     */
    abstract fun getItemView(context: Context?): ITEM_VIEW
}