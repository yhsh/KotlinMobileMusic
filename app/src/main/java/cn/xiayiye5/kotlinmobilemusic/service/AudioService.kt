package cn.xiayiye5.kotlinmobilemusic.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import cn.xiayiye5.kotlinmobilemusic.module.AudioBean
import de.greenrobot.event.EventBus
import java.util.*

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
 * 创建时间：2020/2/23 12:03
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 文件包名：cn.xiayiye5.kotlinmobilemusic.service
 * 文件说明：播放音乐的服务
 */
class AudioService : Service() {
    companion object {
        //顺序播放
        const val modeAll = 1
        //单曲循环
        const val modeSingle = 2
        //随机播放
        const val modeRandom = 3
    }

    var arrayList: ArrayList<AudioBean>? = null
    var position: Int = -2
    var mediaPlayer: MediaPlayer? = null
    //默认顺序播放
    var mode = modeAll
    private val audioBinder by lazy { AudioBind() }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //解决播放同一首歌曲
        val pos = intent?.getIntExtra("position", -1) ?: -1
        if (pos != position) {
            position = pos
            arrayList = intent?.getParcelableArrayListExtra<AudioBean>("list")
            Log.e("打印歌曲", arrayList.toString())
            //播放歌曲
            audioBinder.playItem()
        } else {
            audioBinder.notifyDataAndUi()
        }
        //START_STICKY粘性的,会重新启动,不会传递intent数据
        //START_NOT_STICKY 不会重新启动
        //START_REDELIVER_INTENT 会重新启动,会传递intent数据
        //所以建议使用 START_NOT_STICKY不重启
//        return super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return audioBinder
    }

    //kotlin里面内部类关键字：inner
    inner class AudioBind : Binder(), Iservice, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
        override fun playPosition(playPosition: Int) {
            position = playPosition
            playItem()
        }

        override fun getpLayList(): ArrayList<AudioBean> {
            return arrayList!!
        }

        override fun playPre() {
            position = when (mode) {
                modeRandom -> Random().nextInt(arrayList!!.size)
                else -> {
                    if (position == 0) {
                        arrayList!!.size - 1
                    } else {
                        position - 1
                    }
                }
            }
            playItem()
        }

        override fun playNext() {
            position = when (mode) {
                modeRandom -> Random().nextInt(arrayList!!.size)
                else -> {
                    (position + 1) % arrayList!!.size
                }
            }
            playItem()
        }

        override fun getPlayMode(): Int {
            //返回设置的播放模式
            return mode
        }

        override fun updatePlayMode() {
            when (mode) {
                modeAll -> mode = modeSingle
                modeSingle -> mode = modeRandom
                modeRandom -> mode = modeAll
            }
        }

        override fun onCompletion(mp: MediaPlayer?) {
            when (mode) {
                modeAll -> position = (position + 1) % arrayList!!.size
//                modeSingle ->
                modeRandom -> position = Random().nextInt(arrayList!!.size)
            }
            //继续播放
            playItem()
        }

        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        override fun updatePlayState() {
            if (isPlaying()) {
                //暂停播放
                mediaPlayer?.pause()
            } else {
                //继续播放
                mediaPlayer?.start()
            }
        }

        override fun isPlaying(): Boolean {
            return mediaPlayer!!.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
            //播放歌曲后更新UI相关
            notifyDataAndUi()
        }

        fun notifyDataAndUi() {
            //通过EventBus发送消息
            EventBus.getDefault().post(arrayList?.get(position))
        }

        fun playItem() {
            //解决多首歌曲多次播放的方法创建mediaPlay前先释放
            if (mediaPlayer != null) {
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setDataSource(arrayList?.get(position)?.data)
            mediaPlayer!!.prepareAsync()
            //设置播放完成的监听
            mediaPlayer!!.setOnCompletionListener(this)
        }
    }
}