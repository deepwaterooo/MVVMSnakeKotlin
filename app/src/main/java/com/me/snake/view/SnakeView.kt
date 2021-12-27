package com.me.snake.view


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

import com.me.snake.model.AppConfig
/**
 * Class for: 游戏的底层布局: 即蛇的床，蛇所躺着的多个各个方格
 */
class SnakeView constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var mGridWidth = 0f  // 每个方格的宽
    private var mGridHeight = 0f // 每个方格的长

    private var mPaintLine: Paint? = null // 画线
    var mCallback: onOverCallback? = null // 初始化接口

    init {
        initPaint()
    }
    private fun initPaint() {
         // 设置背景色画笔 蓝色
        mPaintLine = Paint()
        mPaintLine?.style = Paint.Style.FILL // 铺满
        mPaintLine?.color = Color.parseColor("#FF82B8E9")
        mPaintLine?.isAntiAlias = true
        mPaintLine?.isDither = true
    }

    override fun onDraw(canvas: Canvas) { // 每桢都需要调用，检查一下有没有无需要或是可以优化的地方
        drawBg(canvas)
    }
     // 画背景方格
    private fun drawBg(canvas: Canvas) {
        mGridWidth = (width / AppConfig.GAME_COL_COUNT).toFloat()
        mGridHeight = mGridWidth
        AppConfig.GRID_WIDTH = mGridWidth
        AppConfig.GRID_HEIGHT = mGridHeight
        drawLine(canvas, AppConfig.GAME_COL_COUNT, AppConfig.GAME_ROW_COUNT)
    }

    /**
     * 将横竖画线分开 便于以后出现行列数量不一致的时候好更改
     *
     * @param canvas
     * @param columnCount 列数
     * @param rowCount    行数
     */
    private fun drawLine(canvas: Canvas, columnCount: Int, rowCount: Int) {
        // 根据行数画对应数量的横线 底部需要画一条 所以加1
        for (i in 0 until rowCount + 1) {
            // 画横线
            canvas.drawLine(0f, i * mGridHeight, width.toFloat(), i * mGridHeight, mPaintLine!!)
            // 把一列中每一格的y坐标存起来
            AppConfig.setSingleColumnY(i, i * mGridHeight)
        }
        // 根据列数画对应数量的竖线
        for (i in 0 until columnCount) {
            // 画竖线
            canvas.drawLine(i * mGridWidth, 0f, i * mGridWidth, mGridWidth * columnCount, mPaintLine!!)
            // 把一行中每一格的x坐标存起来
            AppConfig.setSingleRowX(i, i * mGridWidth)
        }
        mCallback?.onOver(true)
    }

    // 初始化完成之后的回调
    interface onOverCallback {
        fun onOver(isOk: Boolean)
    }
}
