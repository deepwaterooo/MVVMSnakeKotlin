package com.me.snake.model

import android.graphics.Canvas
import android.graphics.Paint
import java.util.*
import com.me.snake.bean.Move
import com.me.snake.bean.Pos

/**
 * 因为蛇的组成是块状的，此对象就是组成蛇的块集合
 * */
class Snake : GameModel {

    var mFirstLength = 7 // 初始的长度

    @Volatile
    var mHeadPosition = mFirstLength - 1 // 头部的位置
    @Volatile
    var mBackPosition = 0 // 尾部的位置
    @Volatile
    var mNextPosition = 0 // 下一个移动的位置  每移动一次更新一次
    @Volatile
    var moveType = Move.PAUSE // 初始方向为暂停 因为从第一行开始 第一次跑 只能向右或者向下

    // 蛇身体的位置集合
    var mSnakePoss = LinkedList<Pos>()  // 这个用起来效率也不是很高呀，继续用SparseArray? MutableLiveData<List<Pos>>

    var mCrash: OnCrashListener? = null // 两个回调函数？
    var mEatFood: OnEatenFoodListener? = null

    /**
     * 蛇撞墙 和 吃到自己
     */
    interface OnCrashListener {
        fun onCrash(message: String)
    }
    /**
     * 贪吃蛇吃到食物 的回调
     * */
    interface OnEatenFoodListener {
        fun onEaten()
    }

    constructor() {
        if (mSnakePoss == null) mSnakePoss = LinkedList() // 这个用起来效率也不是很高呀，继续用SparseArray?

        // todo 第一步 构建一条皮皮蛇
        for (i in mFirstLength - 1 downTo 0) {
            var data = Pos(i, false, false)
            if (i == mFirstLength - 1) // 如果下标是最大的 则是蛇头
            data.addHeadPosition(i)
            if (i == 0)
            data.addTailPosition(i) // 如果下标是0 则是蛇尾
            mSnakePoss.add(data)
        }
    }

    // 获取蛇的肉体
    fun getPosList(): LinkedList<Pos> {
        if (mSnakePoss == null) mSnakePoss = LinkedList()
        return mSnakePoss
    }

    // 画蛇第一步: 这是一个同步方法，在一个同步方法里做这些检测，感觉很混乱呀？不是应该数据驱动，当撞墙壁了或是吃到了自己自动触发游戏结束
    // 而不应该是在这样一个同步方法里，做这些乱七八糟的检测呀
    @Synchronized
    fun drawSnake(canvas: Canvas, type: Move, paintHead: Paint, paintBody: Paint, mBallPosition: Int) {
        // 1.移动方向暂停则不走
        if (type == Move.PAUSE) {
            moveType = type
            drawList(canvas, paintHead, paintBody)
            return
        }

        // 2.检查一 , 方向不包含自己身体 可以移动才将最新的方向赋给全局变量 对nextposition进行了操作
        if (checkPosition(type))
        moveType = type

        // 3.检查二 ,检查是否撞墙 是的话over
        isCrashWall()

        // 4.检查三 ,检查是否吃到了自己 吃到的话直接over
        checkEatMySelf()

        // 5.检查四,检查下一个位置是不是球
        if (mNextPosition == mBallPosition) {
            mEatFood?.onEaten()
        } else {
            getPosList().removeLast() // 移除旧的蛇尾
            getPosList().last.tail = true  // 设置新的蛇尾
        }

        getPosList().first.head = false // 把旧的蛇头置为false

        var head = Pos()
        getPosList().addFirst(head.addHeadPosition(mNextPosition)) // 建一个新的蛇头

        mHeadPosition = getPosList().first.pos
        mBackPosition = getPosList().last.pos

        drawList(canvas, paintHead, paintBody)
    }

    // 画蛇第二步: 稍微区分一下蛇头/蛇身与蛇尾，使用不同（着色）的画笔
    private fun drawList(canvas: Canvas, paintHead: Paint, paintBody: Paint) {
        getPosList().forEach { // forEach: it field this ......
                               draw(canvas, it.i, it.j, if (it.head) paintHead else paintBody)
        }
    }

    /**
     * 检查一, 检查要移动的方向不能是反的
     */
    fun checkPosition(type: Move): Boolean {
        var canMove = true // 是否可以向某个方向移动
        mNextPosition = getNext(type) // 获取位置
        // 比较第二块是否跟下一个点一样 一样则说明是反的 则不改变方向
        if (getPosList().get(1).pos == mNextPosition) canMove = false // 紧挨着蛇头的一格
        // 不能移动则使用上一个移动方向的点
        if (!canMove) mNextPosition = getNext(moveType)  // 获取位置
        return canMove
    }

    /**
     * 检查二, 检查是否撞墙
     */
    private fun isCrashWall() {
        // 上下是否撞墙    i in [0, 399) 排除了 399
        var isTop_Bottom = mNextPosition !in 0 until AppConfig.GAME_ROW_COUNT * AppConfig.GAME_COL_COUNT
        // 左边是否撞墙                             列为0
        var isLeft = getPosList().first.pos % 20 == 0 && mNextPosition % 20 == AppConfig.GAME_COL_COUNT - 1 // -1 % = 19
        // 右边是否撞墙
        var isRight = getPosList().first.pos % 20 == AppConfig.GAME_COL_COUNT - 1 && mNextPosition % 20 == 0
        if (isTop_Bottom || isLeft || isRight) {
            mNextPosition = getPosList().first.pos
            mCrash?.onCrash("You hit the wall!")
        }
    }

    /**
     * 检查三, 检查是否碰到了自己
     * 只要碰到除了第二个位置以外的身体就算吃到自己(第三个位置也碰不到 不过无所谓)
     */
    private fun checkEatMySelf() {
        var theTwoPosition = getPosList().get(1).pos // 第二个位置的index
        // 这里用foreach形式是因为  遍历速度 快于普通i++方式 (linklist)
        getPosList().forEach {
            if (!it.head && it.pos != theTwoPosition && it.pos == mNextPosition) {
                mCrash?.onCrash("You have bited yourself!")  // gameover
                return
            }
        }
    }

    // 获取下一个移动的坐标点
    private fun getNext(type: Move): Int {
        when (type) {
            Move.LEFT -> return mHeadPosition - 1
            Move.RIGHT -> return mHeadPosition + 1
            Move.UP -> return mHeadPosition - 20
            Move.DOWN -> return mHeadPosition + 20
        }
        return 0
    }
}