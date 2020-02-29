package cn.xiayiye5.kotlinmobilemusic.kotlin

abstract class Father {
    abstract fun getMoney()
    open fun eat() {
        print("父亲吃饭")
    }
}