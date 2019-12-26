package cn.xiayiye5.kotlinmobilemusic.kotlin.security

/**
 * 消息摘要应用场景：用户登录
 * Created by itheima
 */

fun main(args: Array<String>) {
    //登录：用户的密码，必须加密，传输中，是以密文传输

    val passwordMd5 = MessageDigetUtil.md5("123456")
    println(passwordMd5)

    val result = HttpUtil.request("http://127.0.0.1:8080/crypt/Login?username=heima&password=" + passwordMd5)
    println("服务器返回="+result)

}
