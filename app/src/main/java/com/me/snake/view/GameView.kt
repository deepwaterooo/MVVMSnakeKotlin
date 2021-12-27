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
    var food: Pos? = null 
    var dim = 0
    var gap = 5
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.run {
            food?.apply { // 画食物 与 画蛇
                drawRect((i * dim + gap).toFloat(), (j * dim + gap).toFloat(),
                          ((i+1) * dim + gap).toFloat(), ((j+1) * dim + gap).toFloat(), paint)
            }
            snakeBody?.forEach { pos -> drawRect((pos.i * dim + gap).toFloat(), (pos.j * dim + gap).toFloat(),
                                                  ((pos.i+1) * dim + gap).toFloat(), ((pos.j+1) * dim + gap).toFloat(), paint)
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
     
    // // 定义食物对象
    // var mFood: Food? = null

    // // 定义蛇对象
    // var mSnake: Snake? = null

    // // 定义初始移动方向
    // var mMoveType: Move = Move.PAUSE
    // var mIsGameOver = false

    // // 球画笔
    // var mFoodPaint: Paint = Paint().apply {
    //     setStyle(Paint.Style.FILL)// 铺满
    //     setColor(Color.RED)
    //     setAntiAlias(true)
    //     setDither(true)
    // }

    // // 蛇头画笔
    // var mSnakeHeadPaint: Paint = Paint().apply {
    //     setStyle(Paint.Style.FILL)
    //     setColor(Color.DKGRAY)
    //     setAntiAlias(true)
    //     setDither(true)
    // }
    // var mSnakeBodyPaint: Paint = Paint().apply {
    //     // 蛇身画笔
    //     setStyle(Paint.Style.FILL)
    //     setColor(Color.GRAY)
    //     setAntiAlias(true)
    //     setDither(true)
    // }

    // private var mTime = 200// 多久移动一次
    // private var mRun = Handler()
    // private var mRunnable: Runnable? = null
    // private var dialog: AlertDialog? = null

    // constructor(context: Context) : this(context, null) {}
    // constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    //     initCycle()
    // }

    // override fun onDraw(canvas: Canvas) {
    //     mSnake?.drawSnake(canvas, mMoveType, mSnakeHeadPaint, mSnakeBodyPaint, mFood!!.mPosition)
    //     mFood?.resetFood(canvas, mFoodPaint, mSnake!!.getPositionList())
    // }

    // /**
    //  * 手动初始化蛇和球
    //  */
    // fun initGameObj() {
    //     if (mFood != null && mSnake != null) return
    //     mFood = Food()  // 为什么两个同时重置呢，不应该是一次只可能重置一个才对吗，除非游戏重新开始的时候；食物被吃掉了需要单独重置食物
    //     mSnake = Snake()

    //     // 碰撞回调
    //     mSnake?.mCrash = object : Snake.OnCrashListener {
    //         override fun onCrash(message: String) {
    //             mMoveType = EnumMoveType.PAUSE
    //             mIsGameOver = true
    //             dialog?.setMessage("游戏结束：" + message)
    //             dialog?.show()

    //         }
    //     }
    //     // 吃到食物回调
    //     mSnake?.mEatFood = object : Snake.OnEatenFoodListener {
    //         override fun onEaten() {
    //             if (mFood != null)
    //             mFood?.onEatFood()
    //         }
    //     }
    // }

    // // 主要方法 设置溜蛇的方向
    // @Synchronized
    // fun StartMove(move: Move) {
    //     mMoveType = move
    // }

    // // 初始化轮训
    // private fun initCycle() {
    //     dialog = AlertDialog.Builder(context).setMessage("GAME OVER!!!") // 开启一个重新开始游戏的会话框
    //         .setPositiveButton("YES", object : DialogInterface.OnClickListener {
    //                                override fun onClick(dialog: DialogInterface?, which: Int) {
    //                                    dialog?.dismiss()

    //                                    // 直接关闭页面 简单粗暴
    //                                    (context as Activity).finish()
    //                                    // 失败则重新初始化
    //                                    // mFood = Food()
    //                                    // mSnake = Snake()
    //                                    // invalidate()
    //                                }
    //         }).create()
    //     dialog?.setCancelable(false)
    //     dialog?.setCanceledOnTouchOutside(false)

    //     // 循环的时候做什么
    //     mRunnable = Runnable {
    //         if (context == null || mIsGameOver) return @Runnable // ???
    //         mRun.postDelayed(mRunnable, mTime.toLong()) // 延迟多久一定的时间才执行
    //         StartMove(mMoveType)
    //         if (mMoveType != Move.PAUSE)
    //         invalidate() // 只要没有暂停，就需要每桢绘制？
    //     }
    //     // 开始循环
    //     mRun.post(mRunnable)                            // 
    // }

    // override fun onDetachedFromWindow() {
    //     mRun?.removeCallbacks(mRunnable)
    //     super.onDetachedFromWindow()
    // }

    // /**
    //  * dp 转 px
    //  */
    // private fun dp2px(dip: Int): Int {
    //     return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics).toInt()
    // }
}
