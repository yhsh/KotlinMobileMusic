package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * AES加密解密
 * Created by itheima
 */
object AESCrypt{

    //AES加密
    fun encrypt(input:String, password:String): String {
        //1.创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //2.初始化cipher:告诉cipher加密还是解密
        //通过秘钥工厂生成秘钥
        val keySpec:SecretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        //3.加密/解密
        val encrypt = cipher.doFinal(input.toByteArray())
        val result = Base64.encode(encrypt)
        return result
    }

    //AES解密
    fun decrypt(input:String, password:String): String {
        //1.创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //2.初始化cipher:告诉cipher加密还是解密
        //通过秘钥工厂生成秘钥
        val keySpec:SecretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        //3.加密/解密
        val encrypt = cipher.doFinal(Base64.decode(input))
        val result = String(encrypt)
        return result
    }
}

fun main(args: Array<String>) {

    val password = "1234567812345678"//长度16位
    println("AES秘钥字节长度="+password.toByteArray().size)
    //AES/CBC/NoPadding (128) :16个字节，16 * 8 = 128 位

    val input = "黑马"

    val encrypt = AESCrypt.encrypt(input, password)
    val decrypt = AESCrypt.decrypt(encrypt, password)
    println("加密="+encrypt)
    println("解密="+decrypt)

}
