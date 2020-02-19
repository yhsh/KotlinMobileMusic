package cn.xiayiye5.kotlinmobilemusic.net

import cn.xiayiye5.kotlinmobilemusic.util.ThreadUtil
import okhttp3.*
import java.io.IOException

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
 * 创建时间：2019/12/22 16:15
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.net
 * 文件说明：发送网络请求类
 */
class NetManager private constructor() {
    companion object {
        val manager by lazy { NetManager() }
    }

    val client by lazy { OkHttpClient() }

    fun <RESPONSE> sendRequest(req: MRequest<RESPONSE>) {
        val request = Request.Builder()
            .url(req.url)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("调用失败" + Thread.currentThread().name)
                ThreadUtil.runOnMainThread(Runnable {
                    req.handler.onError(req.type, e.message)
                })
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code() == 200) {
                    val result = response.body()?.string()
                    println("调用成功$result")
                    val parseResult = req.parseResult(result)
                    ThreadUtil.runOnMainThread(Runnable {
                        req.handler.onSuccess(req.type, parseResult)
                    })
                } else {
                    ThreadUtil.runOnMainThread(Runnable {
                        req.handler.onError(req.type, response.message())
                    })
                }
            }
        })
    }
}