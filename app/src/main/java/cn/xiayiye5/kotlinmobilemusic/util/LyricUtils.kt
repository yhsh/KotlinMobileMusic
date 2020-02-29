package cn.xiayiye5.kotlinmobilemusic.util

import android.util.Log
import cn.xiayiye5.kotlinmobilemusic.module.LyricBean
import java.io.File

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
 * 创建时间：2020/2/28 22:35
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.util
 * 文件说明：解析歌词的工具类
 */
object LyricUtils {
    /**
     * 解析歌词的方法
     */
    fun parseLyric(file: File): List<LyricBean> {
        val list = ArrayList<LyricBean>()
        if (!file.exists()) {
            list.add(LyricBean(0, "歌词放入手机根目录：XiaYiYeMusic/lyric下即可正常加载歌词"))
        } else {
            val lineListData = file.readLines()
            for (line in lineListData) {
                val lineList: List<LyricBean> = parseLine(line)
                list.addAll(lineList)
            }
        }
        //歌词按照时间排序下
        list.sortBy { it.startTime }
        return list
    }

    /**
     * 解析每一行的歌词
     */
    private fun parseLine(line: String): List<LyricBean> {
        val list = ArrayList<LyricBean>()
        val split = line.split("]")
        //拿到歌词内容
        val content = split[split.size - 1]
        //拿到时间
        for (index in 0 until split.size - 1) {
            val lineTime = split[index]
            val startTime: Int = parseTime(lineTime)
            //添加到歌词bean中
            list.add(LyricBean(startTime, content))
        }
        return list
    }

    /**
     *解析歌词时间
     */
    private fun parseTime(lineTime: String): Int {
        val newLineTime = lineTime.replace("[", "").split(":")
        Log.e("打印集合", newLineTime[0] + "=" + newLineTime[1])
        var hour = 0
        var minute = 0
        var second = 0f
        if (newLineTime.size == 3) {
            //有小时
            hour = (newLineTime[0].toInt()) * 60 * 60 * 1000
            minute = (newLineTime[1].toInt()) * 60 * 1000
            second = (newLineTime[2].toFloat()) * 1000
        } else {
            //没有小时
            minute = (newLineTime[0].toInt()) * 60 * 1000
            second = (newLineTime[1].toFloat()) * 1000
        }
        return hour + minute + second.toInt()
    }
}