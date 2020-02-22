package cn.xiayiye5.kotlinmobilemusic.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.adapter.VbangAdapter
import cn.xiayiye5.kotlinmobilemusic.base.BaseFragment
import cn.xiayiye5.kotlinmobilemusic.module.AudioBean
import cn.xiayiye5.kotlinmobilemusic.ui.activity.AudioPlayerActivity
import cn.xiayiye5.kotlinmobilemusic.util.AudioTask
import cn.xiayiye5.kotlinmobilemusic.util.CursorUtil
import cn.xiayiye5.kotlinmobilemusic.util.ThreadUtil
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

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
 * #               佛祖保佑         永无BUG            #
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
class VBangFragment : BaseFragment() {
    var adapter: VbangAdapter? = null
    //开启查询的标识，查询完成可根据token标识区分是查询的哪一个
    private var token: Int = 0
    internal var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val cursor: Cursor = msg.obj as Cursor
            println(CursorUtil.logCursor(cursor))
        }
    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_vbang, null)
    }

    override fun initData() {
        super.initData()
        //加载音乐列表
//        loadMusicOne()
//        loadMusicTwo()
        adapter = VbangAdapter(context, null)
        listView.adapter = adapter
        requestPermissionFunction()
        listView.setOnItemClickListener { parent, view, position, id ->
            val list = AudioBean.getAudioBeans(adapter?.getItem(position) as Cursor)
            startActivity<AudioPlayerActivity>("list" to list, "position" to position)
        }
    }

    private fun requestPermissionFunction() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            loadMusicThree()
        } else {
            val dataPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
            val checkResult = ActivityCompat.checkSelfPermission(context!!, dataPermission)
            if (checkResult == PackageManager.PERMISSION_GRANTED) {
                //已经获取权限了
                loadMusicThree()
            } else {
                val data = arrayOf(dataPermission)
                //没有权限，开始申请权限
                val rationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(activity!!, dataPermission)
                if (rationale) {
                    alert("我们只会读取您手机的音乐数据,不会访问隐私照片", "温馨提示") {
                        yesButton { requestPermissions(data, 101) }
                        noButton { }
                    }.show()
                } else {
                    //开始申请授权
                    requestPermissions(data, 101)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadMusicThree()
        }
    }

    private fun loadMusicOne() {
        //创建子线程和handler回调主线程方法一
        ThreadUtil.newThread(Runnable {
            val resolver = context?.contentResolver
            val cursor = resolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                queryArgs(),
                null,
                null,
                null
            )
            println(CursorUtil.logCursor(cursor))
            val msg = Message.obtain()
            msg.obj = cursor
            handler.sendMessage(msg)
        })
    }

    private fun loadMusicTwo() {
        //通过Android原生自带的异步框架AsyncTask方法二
        AudioTask().execute(context?.contentResolver)
    }

    private fun loadMusicThree() {
        //专门查询数据库的方法三
        val handler = @SuppressLint("HandlerLeak")
        object : AsyncQueryHandler(context?.contentResolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                super.onQueryComplete(token, cookie, cursor)
                println(CursorUtil.logCursor(cursor))
                (cookie as VbangAdapter).swapCursor(cursor)
            }
        }
        handler.startQuery(
            token, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            queryArgs(),
            null,
            null,
            null
        )
    }

    /**
     * 查询音乐的参数
     */
    private fun queryArgs(): Array<String> {
        return arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //关闭cursor
        adapter?.changeCursor(null)
    }
}