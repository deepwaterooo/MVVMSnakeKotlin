package com.me.snake.bean

import com.me.snake.model.AppConfig

/**
 * Class for: 坐标对象 实体类
 * indexX:X坐标
 * indexY:y坐标
 * position:位于网格图中的第几个位置 横着数
 * isHead:这个坐标是不是头部                 todo 暂时没什么用  以后说不定有用
 * isBack:这个坐标是不是蛇尾                 todo 暂时没什么用  以后说不定有用
 * 2个参数构成一个点
 */
data class Pos (var pos: Int, var head: Boolean, var tail: Boolean, var i: Float, var j: Float) {

    // 无参构造函数
    constructor() : this(0, false, false, 0f, 0f)
    // 有参构造函数
    constructor(pos: Int, head: Boolean, tail: Boolean) : this(pos, head, tail, 0f, 0f) {
        setIJ(pos)
    }
    // 添加一个新的蛇头
    fun addHeadPosition(p: Int) : Pos { //  Pos: 函数的返回值类型
        head = true
        tail = false
        setIJ(p)
        return this
    }
    // 添加一个新的蛇尾
    fun addTailPosition(p: Int) : Pos {
        head = false
        tail = true
        setIJ(p)
        return this
    }
    private fun setIJ(p: Int) {
        pos = p // pos: 不知道是从哪里崩出来的变量名: data class Pos ()传进来的参数 ？
        i = AppConfig.getSingleRowX(pos % AppConfig.GAME_COL_COUNT)    // 计算x的位置
        j = AppConfig.getSingleColumnY(pos / AppConfig.GAME_COL_COUNT) // 计算y的位置
    }
} 
