package com.me.snake.model


import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.util.SparseIntArray
import com.me.snake.bean.Pos

/**
 * 贪吃蛇吃的食物 集成后面带()是实现无参构造方法
 * */
class Food : GameModel() {

    var x = -1f // 球的X坐标
    var y = -1f // 球的y坐标
    var mPosition = -1 // 球所在的index

    // 当碰到球之后 重置坐标
    @Synchronized
    fun onEatFood() {
        x = -1f
        y = -1f
    }

    // 生成一个新的球
    @Synchronized
    fun resetFood(canvas: Canvas, paint: Paint, snakeList: List<Pos>) {
        if (x >= 0 && y >= 0) {
            // 没吃到之前 则还是保持原来的位置
            draw(canvas, x, y, paint) // <<<<============== 如果一直是重画的话，那么比较好的策略是当且仅当需要的时候才重画 ？ 那么每桢都需要重画呢？ 想想这个问题
            return
        }

        // 最大的位置数
        var mostPosition = AppConfig.GAME_COL_COUNT * AppConfig.GAME_ROW_COUNT - 1
        // 存放随机生成的位置数
        var newPosition = -1

        // 使用SparseIntArray存放蛇身体的index 便于判断
        var sparryList = SparseIntArray()

        for (item in snakeList) 
            sparryList.put(item.pos, 520) // 随便放个值

        // 如果不在蛇坐标集合中,或值为-1 ,则重新生成
        while (newPosition == -1 || sparryList.get(newPosition) == 520) 
            // 随机生成一个位置数
            newPosition = (Math.random() * mostPosition).toInt()

        mPosition = newPosition

        x = AppConfig.getSingleRowX(newPosition % AppConfig.GAME_COL_COUNT)// 计算x的位置
        y = AppConfig.getSingleColumnY(newPosition / AppConfig.GAME_COL_COUNT)// 计算y的位置

        // 画一个新的球
        draw(canvas, x, y, paint)
        Log.i("随机生成的位置", newPosition.toString())
    }

    // 画球
    override fun draw(canvas: Canvas, x: Float, y: Float, paint: Paint) {
        val width = AppConfig.GRID_WIDTH   // 每个小格的宽和高
        val height = AppConfig.GRID_HEIGHT
        canvas.drawCircle(x + (width / 2), y + (height / 2), width / 2, paint) // 在画布上绘制圆形，通过指定圆形圆心的坐标、和半径来实现
    }
}