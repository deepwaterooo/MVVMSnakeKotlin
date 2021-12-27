package com.me.snake.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.me.snake.viewModel.Pos

// import com.me.snake.bean.Move 
// import com.me.snake.model.Food
// import com.me.snake.model.Snake
import com.me.snake.viewModel.SnakeViewModel

/**
// * Class for: 游戏的顶层布局: 底层？基层 base层
 // class GameView : View {
 */
class GameView (context: Context, attrs: AttributeSet) : View (context, attrs) { // GameView

    private val paintFood: Paint = Paint().apply { color = Color.RED } // 食物用红色画笔
    private val paint: Paint = Paint().apply { color = Color.BLUE }    // 食物用红色画笔
    var snakeBody: List<Pos>? = null 
    var food: Pos? = null // bonus
    var dim = 0
    var gap = 5
    // 画食物 与 画蛇
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            food?.apply {
                drawRect((i * dim + gap).toFloat(), (j * dim + gap).toFloat(),
                          ((i+1) * dim - gap).toFloat(), ((j+1) * dim - gap).toFloat(), paintFood)
            }
            snakeBody?.forEach {
                pos -> drawRect((pos.i * dim + gap).toFloat(), (pos.j * dim + gap).toFloat(),
                                 ((pos.i+1) * dim - gap).toFloat(), ((pos.j+1) * dim - gap).toFloat(), paint)
            }
        }
    }

    fun updateBonus(pos: Pos?) {
        food = pos
    }
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        dim = width / 20  // bug: width
    }
}
