package com.me.snake.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class SnakeViewModel : ViewModel () {
    private val body = mutableListOf<Pos>()
    private val size = 20
    private var score = 0
    private var food: Pos? = null // bonus 
    private var dir = Dir.LEFT

    var snake = MutableLiveData<List<Pos>>() // snake body List<pos>
    var fpos = MutableLiveData<Pos>() // food pos: bonusPosition
    var gameState = MutableLiveData<GameState>()
    var scoreData = MutableLiveData<Int>()

    fun move(dirlocal: Dir) {
        dir = dirlocal
    }
    
    fun start() {
        body.clear() 
        body.add(Pos(10, 10))
        body.add(Pos(11, 10))
        body.add(Pos(12, 10))
        body.add(Pos(13, 10))
        food = nextFood().also {
            fpos.value = it
        }
        fixedRateTimer("timer", true, 500, 1000/5) {
            val pos = body.first().copy().apply {
                when (dir) {
                    Dir.LEFT -> i--
                    Dir.RIGHT -> i++
                    Dir.UP -> j--
                    Dir.DOWN -> j++
                }
                if (body.contains(this) || i < 0 || i > size || j < 0 || j >= size) {
                    cancel() // 典型的数据驱动：撞墙引发 游戏结束
                    gameState.postValue(GameState.GAMEOVER)
                }
            }
            body.add(0, pos)
            println(pos.i)
            println(pos.j)
            if (pos != food) body.removeLast() 
            else {
                food = nextFood().also {
                    fpos.postValue(it) // 发布更新
                }
                score++ // databinding item
                scoreData.postValue(score) // 发布更新
            } // setValue()只能在主线程中调用，postValue()可以在任何线程中调用
            snake.postValue(body) // LiveData.postValue()可以在任意线程中执行, 抄代码抄错了吧，哭死。。。。。。
        } 
    }
    fun nextFood(): Pos {
        val pos = Pos(Random.nextInt(size), Random.nextInt(size)) // 随机生成食物的下一个位置
        return pos
    }
}
data class Pos(var i: Int, var j: Int) {
}
enum class GameState {
    ONGOING, GAMEOVER
}
enum class Dir {
    UP, DOWN, LEFT, RIGHT
} 