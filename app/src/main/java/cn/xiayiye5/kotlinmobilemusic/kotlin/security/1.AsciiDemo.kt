package cn.xiayiye5.kotlinmobilemusic.kotlin.security

/**
 * Created by itheima
 */
fun main(args: Array<String>) {

    //获取字符ascii编码
    val c:Char = 'a'
    //字符转成十进制
    val value:Int = c.toInt()

    println(value)//97

    //获取字符串ascii

    val str = "I love you"
    //遍历获取每一个字符的ascii
    val array = str.toCharArray()

    //val stringBuilder = StringBuilder()
    
   /* for(ch in array){
        val result = ch.toInt()
        stringBuilder.append(result.toString()+" ")
        //println(result)
    }*/

    val result = with(StringBuilder()){
        for(ch in array){
            val result = ch.toInt()
            append(result.toString()+" ")
            //println(result)
        }
        //返回结果
       toString()
    }
    
    println(result)
}

