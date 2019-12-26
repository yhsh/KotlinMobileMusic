package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

/**
 * 数字签名
 * Created by itheima
 */

object SignatureDemo{
    //签名
    fun sign(input:String, privateKey: PrivateKey): String {


        //获取数字签名实例对象
        val signature = Signature.getInstance("SHA256withRSA")
        //初始化签名
        signature.initSign(privateKey)
        //设置数据源
        signature.update(input.toByteArray())
        //签名
        val sign = signature.sign()
        return Base64.encode(sign)
    }

    fun verify(input: String, publicKey: PublicKey, sign:String):Boolean{
        val signature = Signature.getInstance("SHA256withRSA")
        //初始化签名
        signature.initVerify(publicKey)
        //传入数据源
        signature.update(input.toByteArray())
        //校验签名信息
        val verify = signature.verify(Base64.decode(sign))
        return verify
    }
}

fun main(args: Array<String>) {
    val input = "name=iPhone8&price=7888"
    val privateKey = RSACrypt.getPrivateKey()
    val publicKey = RSACrypt.getPublicKey()

    val sign = SignatureDemo.sign(input, privateKey)
    println("sign="+sign)

    //********************校验********************//

    val verify = SignatureDemo.verify("name=iPhone8&price=8", publicKey, sign)
    println("校验="+verify)
}
