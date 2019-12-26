package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec


/**
 * DES加密，解密
 * Created by itheima
 */

object DESCrypt{

    //算法/工作模式/填充模式
    //val transformation = "DES/ECB/PKCS5Padding"
    val transformation = "DES/CBC/PKCS5Padding"

    //算法
    val algorithm = "DES"

    //des加密
    fun encrypt(input:String, password:String): String {
        //1.创建cipher对象:学习查看api文档
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher(参数1：加密/解密模式)
        val kf = SecretKeyFactory.getInstance(algorithm)
        val keySpec = DESKeySpec(password.toByteArray())

        val key:Key = kf.generateSecret(keySpec)
        val iv = IvParameterSpec(password.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE,key, iv)// CBC模式需要额外参数
        //3.加密/解密
        val encrypt = cipher.doFinal(input.toByteArray())
        println("加密后字节数组长度="+encrypt.size)//8
        // base64编码
        return Base64.encode(encrypt)
    }

    //des解密
    fun decrypt(input:String, password:String): ByteArray {
        //1.创建cipher对象:学习查看api文档
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher(参数1：加密/解密模式)
        val kf = SecretKeyFactory.getInstance(algorithm)
        val keySpec = DESKeySpec(password.toByteArray())

        val key:Key = kf.generateSecret(keySpec)

        val iv = IvParameterSpec(password.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE,key, iv)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())
        //base64解密
        val encrypt = cipher.doFinal(Base64.decode(input))
        return encrypt
    }
}

fun main(args: Array<String>) {



    //1.创建cipher对象:学习查看api文档
   /* val cipher = Cipher.getInstance("DES")

    //2.初始化cipher(参数1：加密/解密模式)
    val kf = SecretKeyFactory.getInstance("DES")
    val keySpec = DESKeySpec(password.toByteArray())

    val key:Key = kf.generateSecret(keySpec)
    cipher.init(Cipher.ENCRYPT_MODE,key)
    //3.加密/解密
    val encrypt = cipher.doFinal(input.toByteArray())*/

    //原文
    val input = "黑马"
    val password = "12345678" //秘钥,des长度8位
    println(password.toByteArray().size)//8个字节

    //DES/CBC/NoPadding (56) -> 56:8个字节 ，8*8 = 64位，DES前7位参与加密计算，最后一位作为校验码

    val array = input.toByteArray()//转成字节数组
    println("2个中文字节长度="+array.size)//6
    array.forEach {
        println(it) //it代表遍历的每一个元素
    }

    val encrypt = DESCrypt.encrypt(input, password)

    println("des加密="+encrypt)//加密后乱码：加密后变成8个字节，到编码表中找不到对应的字符

    val decrypt = DESCrypt.decrypt(encrypt, password)
    println("des解密="+String(decrypt))

}
