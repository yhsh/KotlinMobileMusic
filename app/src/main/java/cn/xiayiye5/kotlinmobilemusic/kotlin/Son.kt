package cn.xiayiye5.kotlinmobilemusic.kotlin

class Son : Father(), Fathers {
    override fun getMoney() {

    }

    override fun getMoneys() {

    }

    override fun eat() {
        print("儿子吃饭")
    }
    infix fun play(age: Int) {
        println("玩下高级方法dsl")
    }

}