package cn.xiayiye5.kotlinmobilemusic.ui.activity

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.adapter.PopAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseActivity
import cn.xiayiye5.kotlinmobilemusic.module.AudioBean
import cn.xiayiye5.kotlinmobilemusic.service.AudioConnection
import cn.xiayiye5.kotlinmobilemusic.service.AudioService
import cn.xiayiye5.kotlinmobilemusic.util.StringUtils
import cn.xiayiye5.kotlinmobilemusic.widget.PlayListPopupWindow
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.jetbrains.anko.toast

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
class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    AdapterView.OnItemClickListener {

    private val audioConnection by lazy { AudioConnection() }
    var audioBean: AudioBean? = null
    private var animationDrawable: AnimationDrawable? = null
    private val updateTime = 101
    //歌曲当前进度
    private var duration: Int = 0
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                updateTime -> startUpdateProgress()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_audio_palyer
    override fun initData() {
        super.initData()
        val extra = intent.getStringExtra("key")
        extra?.let { toast(extra) }
        animationDrawable = audio_anim.drawable as AnimationDrawable
        //注册EventBus
        EventBus.getDefault().register(this)
        //先开启服务,再绑定服务，下面的intent(getIntent)也就是上个页面传过来的intent里面包含了arrayList和position数据
        val intent = intent
        intent.setClass(this, AudioService::class.java)
        //绑定service
        bindService(intent, audioConnection, Context.BIND_AUTO_CREATE)
        //开启service
        startService(intent)
    }

    override fun initListener() {
        super.initListener()
        state.setOnClickListener(this)
        back.setOnClickListener { finish() }
        //设置手指拖动进度条的监听
        progress_sk.setOnSeekBarChangeListener(this)
        //设置播放模式
        mode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)
    }

    /**
     * fromUser：true表示是人为手指拖动改变的进度条，false表示是代码改变的进度条
     * progress ：改变的进度
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            //用户手指拖动进度条了
            AudioConnection.iservice?.seekTo(progress)
            //更新歌曲时间
            updateProgress(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    /**
     * 接收EventBus的方法，此方法为主线程
     *  PostThread,当发送方法里面是什么线程，此命名就为什么线程
     *  MainThread,当发送方法里面不管是什么线程，此命名为主线程
     *  BackgroundThread,当发送方法里面是什么线程，此命名就为后台线程，执行方法是一个个执行
     *  Async;当发送方法里面是什么线程，此命名就为异步线程，执行方法不是一个个
     */
    fun onEventMainThread(itemBean: AudioBean) {
        //设置歌词名称
        lyricView.setSongName(itemBean.display_name!!)
        audioBean = itemBean
        //更新歌曲名和歌手以及播放按钮
        audio_title.text = itemBean.display_name
        artist.text = itemBean.artist
        updatePlayStateBtn()
        //更新歌曲时长进度条显示
        duration = AudioConnection.iservice?.getDuration() ?: 0
        progress_sk.max = duration
        startUpdateProgress()
        //更新图片
        updatePlayModeBtn()
    }

    private fun startUpdateProgress() {
        val progress = AudioConnection.iservice?.getProgress()
        //更新时长进度
        updateProgress(progress)
        handler.sendEmptyMessageDelayed(updateTime, 200)
    }

    /**
     * 更新歌曲时间的方法
     */
    private fun updateProgress(songProgress: Int?) {
        progress.text = StringBuffer(StringUtils.parseProgress(songProgress ?: 0)).append("/")
            .append(StringUtils.parseProgress(duration))
        //更新进度条进度
        progress_sk.progress = songProgress ?: 0
        //设置歌曲总时间
        lyricView.setSongDuration(duration)
        //更新歌词进度
        lyricView.updateProgress(songProgress!!)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
            R.id.mode -> updatePlayMode()
            R.id.pre -> AudioConnection.iservice?.playPre()
            R.id.next -> AudioConnection.iservice?.playNext()
            R.id.playlist -> showSongList()
        }
    }

    /**
     * 显示歌曲列表的方法
     */
    private fun showSongList() {
        val arrayExtra: ArrayList<AudioBean> = AudioConnection.iservice!!.getpLayList()
//        val arrayExtra: ArrayList<AudioBean> = intent.getParcelableArrayListExtra<AudioBean>("list")
        val popAdapter = PopAdapter(arrayExtra, this)
        val playListPopupWindow = PlayListPopupWindow(this, popAdapter, this, window)
        val height = audio_player_bottom.height
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //api19高版本使用
            playListPopupWindow.showAsDropDown(audio_player_bottom, 0, -height, Gravity.CENTER)
        } else {
            //api19版本以下使用
            playListPopupWindow.showAtLocation(audio_player_bottom, Gravity.BOTTOM, 0, -height)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //播放点击的歌曲
        AudioConnection.iservice?.playPosition(position)
    }

    /**
     * 设置播放模式
     */
    private fun updatePlayMode() {
        AudioConnection.iservice?.updatePlayMode()
        //根据播放模式设置对应的图片资源
        updatePlayModeBtn()
    }

    /**
     * 更新播放模式的图标
     */
    private fun updatePlayModeBtn() {
        when (AudioConnection.iservice?.getPlayMode()) {
            AudioService.modeAll -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
            AudioService.modeSingle -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
            AudioService.modeRandom -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
        }
    }

    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新播放歌曲状态
        AudioConnection.iservice?.updatePlayState()
        //更新播放按钮的状态
        updatePlayStateBtn()
    }

    /**
     * 更新播放按钮图标的方法
     */
    private fun updatePlayStateBtn() {
        if (AudioConnection.iservice!!.isPlaying()) {
            //正在播放
            state.setImageResource(R.drawable.selector_btn_audio_play)
            //开启播放动画
            animationDrawable?.start()
            //开启handler后台发送延迟消息
            handler.sendEmptyMessage(updateTime)
        } else {
            //暂停播放
            state.setImageResource(R.drawable.selector_btn_audio_pause)
            //暂停播放动画
            animationDrawable?.stop()
            //移除handler后台发送消息,防止内存泄漏
            handler.removeMessages(updateTime)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑服务
        unbindService(audioConnection)
        //解绑EventBus
        EventBus.getDefault().unregister(this)
        //移除handler消息
        handler.removeCallbacksAndMessages(null)
    }
}