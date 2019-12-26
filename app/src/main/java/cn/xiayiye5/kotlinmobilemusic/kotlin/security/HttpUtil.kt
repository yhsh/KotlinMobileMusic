package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 网络请求工具类
 * Created by itheima
 */
object HttpUtil {

    //发起请求
    fun request(url: String): String {
        //val urlNet = URL("http://127.0.0.1:8080/crypt/UserInfo").openConnection()
        val urlNet = URL(url).openConnection()
        val connection: HttpURLConnection = urlNet as HttpURLConnection

        val ins = connection.inputStream
        val bos = ByteArrayOutputStream()
        var buffer = ByteArray(1024)//缓冲区
        var len = ins.read(buffer)
        while(len != -1){
            bos.write(buffer, 0, len)
            len = ins.read(buffer)
        }

        ins.close()
        bos.close()
        return bos.toString()
    }

}