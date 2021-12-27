package com.me.snake.model

/**
 * Class for: 参数类/游戏应用常数类
 * object :单例类，对于属性类似data class，会自动生成相应的getter、setter；成员的访问，类似java中的static形式；即[className].member
 */
object AppConfig {
    var GRID_WIDTH = 0F  // 每格的宽度 根据分辨率动态设置
    var GRID_HEIGHT = 0F // 每格的高度 根据分辨率动态设置

    val GAME_COL_COUNT = 20 // 布局的列数
    val GAME_ROW_COUNT = 20    // 布局的行数

    var arrRowX: FloatArray? = FloatArray(GAME_COL_COUNT) // 存放横向格子的X坐标的数组
    var arrColY: FloatArray? = FloatArray(GAME_ROW_COUNT)    // 存放纵向格子的Y坐标的数组
    /**
     * 设置横向位置 idx 和x坐标
     */
    fun setSingleRowX(idx: Int, x: Float? = 0f) {
        if (x == null || idx == GAME_COL_COUNT) return
        arrRowX?.set(idx, x)
    }
    /**
     * 设置纵向位置 idx 和x坐标
     */
    fun setSingleColumnY(idx: Int, y: Float? = 0f) {
        if (y == null || idx == GAME_ROW_COUNT) return
        arrColY?.set(idx, y)
    }
    /**
     * 取横向位置坐标
     */
    fun getSingleRowX(idx: Int): Float {
        return arrRowX?.get(if (idx < GAME_ROW_COUNT) idx else GAME_ROW_COUNT - 1) ?: -1f
    }
    /**
     * 取纵向位置坐标
     */
    fun getSingleColumnY(idx: Int): Float {
        return arrColY?.get(if (idx < GAME_COL_COUNT) idx else GAME_COL_COUNT - 1) ?: -1f
    }
}
