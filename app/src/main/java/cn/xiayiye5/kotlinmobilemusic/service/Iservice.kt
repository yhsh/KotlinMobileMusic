package cn.xiayiye5.kotlinmobilemusic.service

import cn.xiayiye5.kotlinmobilemusic.module.AudioBean
import java.util.ArrayList

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
 * 创建时间：2020/2/23 12:36
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.service
 * 文件说明：
 */
interface Iservice {
    //更新播放按钮的状态的方法
    abstract fun updatePlayState()

    //判断音乐是否播放的方法
    abstract fun isPlaying(): Boolean

    //拿到当前播放的进度的方法
    abstract fun getProgress(): Int

    //拿到歌曲的总长度的方法
    abstract fun getDuration(): Int

    //跳转到指定位置播放歌曲的方法
    abstract fun seekTo(progress: Int)

    //更新歌曲的播放模式的方法
    abstract fun updatePlayMode()

    //获取当前歌曲的播放模式的方法
    abstract fun getPlayMode(): Int

    //播放上一首歌曲
    abstract fun playPre()

    //播放下一首歌曲
    abstract fun playNext()

    //获取播放列表的方法
    abstract fun getpLayList(): ArrayList<AudioBean>

    //播放点击的歌曲的方法
    abstract fun playPosition(playPosition: Int)

}