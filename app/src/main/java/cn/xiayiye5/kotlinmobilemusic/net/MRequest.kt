package cn.xiayiye5.kotlinmobilemusic.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

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
 * 创建时间：2019/12/22 16:04
 * 个人小站：http://yhsh.wap.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 项目名称：KotlinMobileMusic
 * 项目包名：cn.xiayiye5.kotlinmobilemusic.net
 * 文件说明：所有网络请求的基类封装
 */
open class MRequest<RESPONSE>(val url: String, val handler: ResponseHandler<RESPONSE>) {
    /**
     * 解析网络请求的方法
     */
    fun parseResult(result: String?): RESPONSE {
        val gson = Gson()
        //获取泛型的方法
        val type =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return gson.fromJson<RESPONSE>(result, type)
    }

    /**
     * 抽取在基类中发送网络请求
     */
    fun execute() {
        NetManager.manager.sendRequest(this)
    }
}