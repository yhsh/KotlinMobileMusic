package cn.xiayiye5.kotlinmobilemusic.ui.activity

import android.media.MediaPlayer
import android.util.Log
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.base.BaseActivity
import cn.xiayiye5.kotlinmobilemusic.module.AudioBean

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
 * 创建时间：2020/2/22 19:59
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.ui.activity
 * 文件说明：
 */
class AudioPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_audio_palyer
    override fun initData() {
        super.initData()
        val arrayList = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position", -1)
        Log.e("打印歌曲", arrayList.toString())
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.setDataSource(arrayList.get(position).data)
        mediaPlayer.prepareAsync()
    }
}