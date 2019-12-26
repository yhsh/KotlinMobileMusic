package cn.xiayiye5.kotlinmobilemusic.kotlin.security

import java.security.MessageDigest

/**
 * 消息摘要
 * Created by itheima
 */
object MessageDigetUtil {

    fun md5(input:String): String {
        val digest = MessageDigest.getInstance("MD5")
        val result = digest.digest(input.toByteArray())
       // println("md5加密长度="+result.size)
        //val stringBuilder = StringBuilder()

        //转成16进制
        /*result.forEach {
            val value = it
            val hex = value.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            println(hexStr)
            if(hexStr.length == 1){
                stringBuilder.append("0").append(hexStr)
            }else{
                stringBuilder.append(hexStr)
            }
        }
        return stringBuilder.toString()*/
        return toHex(result)
    }

    fun sha1(input:String):String{
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(input.toByteArray())
        println("sha1加密后="+result.size)
        //转成16进制
        val toHex = toHex(result)
        println("sha1加密后转成16进制="+toHex.toByteArray().size)
        return toHex
    }

    fun sha256(input:String):String{
        val digest = MessageDigest.getInstance("SHA-256")
        val result = digest.digest(input.toByteArray())
        println("sha256加密后="+result.size)
        //转成16进制
        val toHex = toHex(result)
        println("sh256加密后转成16进制="+toHex.toByteArray().size)
        return toHex
    }

    //转成16进制
    fun toHex(byteArray: ByteArray):String{

        //高阶函数
        val result = with(StringBuilder()){
            //转成16进制
            byteArray.forEach {
                val value = it
                val hex = value.toInt() and (0xFF)
                val hexStr = Integer.toHexString(hex)
                //println(hexStr)
                if(hexStr.length == 1){
                    append("0").append(hexStr)
                }else{
                    append(hexStr)
                }
            }
            this.toString()
        }


        return result
    }

}

fun main(args: Array<String>) {
    val input = "黑马程序员黑马程序员黑马程序员黑马程序员"
   /* val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(input.toByteArray())

    val stringBuilder = StringBuilder()

    //转成16进制
    result.forEach {
        val value = it
        val hex = value.toInt() and (0xFF)
        val hexStr = Integer.toHexString(hex)
        println(hexStr)
        if(hexStr.length == 1){
            stringBuilder.append("0").append(hexStr)
        }else{
            stringBuilder.append(hexStr)
        }
    }*/

    //md5:16,32
    
    val md5 = MessageDigetUtil.md5(input)
    println(md5)
    println(md5.toByteArray().size)

    //32
    println("tomcat="+"827fb063bcac90d9a5ce0e9397041e92".toByteArray().size)

    val sha1 = MessageDigetUtil.sha1(input)
    println(sha1)

    val sha256 = MessageDigetUtil.sha256(input)
    println("sha256="+sha256)

}



