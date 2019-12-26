package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import java.io.BufferedReader
import java.io.FileReader

/**
 * 对称加密应用场景演示
 * Created by itheima
 */

fun main(args: Array<String>) {

    val key = "1234567812345678"//AES秘钥

    //获取联系人数据
    val json = HttpUtil.request("http://127.0.0.1:8080/crypt/UserInfo")
    println("服务器="+json)

    //QQ将联系人缓存到本地：加密
 /*   val br = BufferedWriter(FileWriter("UserInfo.db"))
    val encrypt = AESCrypt.encrypt(json, key)
    br.write(encrypt)
    br.close()*/

    //显示：解密
    val br = BufferedReader(FileReader("UserInfo.db"))
    val readLine = br.readLine()
    println("读取缓存数据="+ AESCrypt.decrypt(readLine, key))

}
