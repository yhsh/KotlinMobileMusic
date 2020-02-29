package cn.xiayiye5.kotlinmobilemusic.kotlin

import android.util.Base64
import java.security.Key
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

fun main(args: Array<String>) {
    val s = Son()
//    s.eat()
//    s.eats()
    s play 3
    var data = listOf<Girl>(
        Girl("小曼", "广东", 35, 165),
        Girl("小花", "湖南", 23, 161),
        Girl("小猫", "广东", 31, 159),
        Girl("小红", "江西", 27, 166),
        Girl("小洛", "吉林", 28, 172),
        Girl("晓燕", "贵州", 23, 159),
        Girl("小芬", "甘肃", 29, 171),
        Girl("小春", "浙江", 26, 163)
    )
    var result = data.maxBy {
        it.age
    }
    val minBy = data.minBy { it.height }
    println(result)
    println(minBy)
    println("打印加密数据：" + SecurityTool("home").encrypt())
//    println("打印解密数据：" + SecurityTool(SecurityTool("home").encrypt()).decrypt())
    println("打印值：${getResult(-2, 5)}")
}

/**
 * 思路：利用递归实现
 * kotlin 版本：计算一个数的n次幂的方法
 * @param number 要计算的数
 * @param count 要计算数的幂
 */
fun getResult(number: Int, count: Int): Int {
    if (count > 0) {
        return number * getResult(number, count - 1)
    }
    return 1;
}

data class Girl(var name: String, var address: String, var age: Int, var height: Int)


/**
 * RSA加解密的工具类
 */
class SecurityTool(var data: String) {
    /**
     * 生成公钥的方法
     */
    fun createPublicKey(): PublicKey? {
        val public = KeyPairGenerator.getInstance("RSA").generateKeyPair().public
        return public
    }

    /**
     * 生成私钥的方法
     */
    fun createPrivateKey(): PrivateKey? {
        val private = KeyPairGenerator.getInstance("RSA").generateKeyPair().private
        return private
    }

    //加密
    fun encrypt(): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, createPrivateKey())
        return String(cipher.doFinal(data.toByteArray()))
    }

    //解密
    fun decrypt(): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, createPublicKey())
        return String(cipher.doFinal(data.toByteArray()))
    }
}