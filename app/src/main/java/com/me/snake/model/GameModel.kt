package com.me.snake.model

import android.graphics.Canvas
import android.graphics.Paint

// import com.me.snake.model.AppConfig
// import com.me.snake.model.AppConfig
// import com.me.snake.model.AppConfig.GRID_HEIGHT
// import com.me.snake.model.AppConfig.GRID_WIDTH

/**
 *  Class : 游戏对象基类
 *  open  : 这个类具有`open`属性，可以被其他类继承
 */
open class GameModel { // Base class needs open statement // 这个类没什么意义 用于学习kt 和可能用于转化子类
    
    var row: Int = AppConfig.GAME_ROW_COUNT
    var col: Int = AppConfig.GAME_COL_COUNT

    constructor() : super() // 我觉得这句话没有用: 有用，去掉后后面一堆bug等着你！

    constructor(row: Int, col: Int) : super() {
        this.row = row
        this.col = col;
    }

    // `open`属性的方法，可以被继承和覆写 没有则不可被子类样覆写 // `final`修饰一个原本具有`open`属性的方法，使其变得不可再被覆写
    open fun draw(canvas: Canvas, x: Float, y: Float, paint: Paint) {
        canvas.drawRect(x, y, x + AppConfig.GRID_WIDTH, y + AppConfig.GRID_HEIGHT, paint) // 在画布上用画笔画出一个长方形的区域块出来
    }
}
