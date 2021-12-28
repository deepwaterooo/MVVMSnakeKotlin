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

    private var mPaintLine: Paint = Paint() // 画线

    init {
        // 设置背景色画笔 蓝色
        mPaintLine?.strokeWidth = 10f //设置画笔的宽度,单位是px.
        mPaintLine?.style = Paint.Style.STROKE // 背景填空心的，Paint.Style.FILL 是 铺满
        mPaintLine?.color = Color.parseColor("#FF82B8E9")
        mPaintLine?.isAntiAlias = true
        mPaintLine?.isDither = true
        // initPaint()
    }
    // private fun initPaint() {
    // }  

    override fun onDraw(canvas: Canvas) {
        drawBg(canvas)
    }
    // 画背景方格
    private fun drawBg(canvas: Canvas) { // width: 
        mGridWidth = (width / AppConfig.GAME_COL_COUNT).toFloat()
        mGridHeight = mGridWidth
        AppConfig.GRID_WIDTH = mGridWidth
        AppConfig.GRID_HEIGHT = mGridHeight
        drawLine(canvas, AppConfig.GAME_COL_COUNT, AppConfig.GAME_ROW_COUNT)
    }

    /**
     * 将横竖画线分开 便于以后出现行列数量不一致的时候好更改
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
    }
}
