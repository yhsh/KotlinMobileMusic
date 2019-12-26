package cn.xiayiye5.kotlinmobilemusic.kotlin.security

/**
 * Created by itheima
 */
fun main(args: Array<String>) {
    val ch:Char = 'A'
    //获取字符ascii
    val asill = ch.toInt()
    println(asill)
    //二进制
    val binary = Integer.toBinaryString(asill)
    println(binary)//1000001 ，8位，一个英文字符占1个字节，
}
