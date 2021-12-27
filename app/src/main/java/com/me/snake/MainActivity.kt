package com.me.snake

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.me.snake.databinding.ActivityMainBinding
import com.me.snake.viewModel.Dir
import com.me.snake.viewModel.GameState

import com.me.snake.viewModel.SnakeViewModel

//import kotlinx.android.synthetic.main.activity_main.*

// import com.me.snake.databinding.ActivityMainBinding // 应该还不是viewbinding，仍然是databinding

// 需要加一个得分：使用databinding得到双向数据绑定，吃了7粒小球，得7分
// 需要加一个加减蛇的滑行速度的按钮：使用databinding得到双向数据绑定，吃了7粒小球，得7分

// 贪吃蛇游戏页面, 这里activity与view之间没有想好，activity本身应该就是view才对？
// acitivity本身当view, 要重新设计activity与view
// 希望snake board是位于一个SurfaceView，方便多开一个线程与主UI逻辑分开，提高效率
// databingding: activity_snake.xml layout 布局，要生存相关java 类，下午再做

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SnakeViewModel // dataBinding的关键逻辑被跳过了，需要自己爬这个坑。。。。。。
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main) // 这是古老的旧版方法:

        // binding = ActivityMainBinding.inflate(getLayoutInflater())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 我希望在这里把背景的方格画好，然后就不用每次每桢再画了

        viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.snake.observe(this, Observer {
                                    binding.game.snakeBody = it
                                    binding.game.invalidate()
        })
        viewModel.fpos.observe(this, Observer {
                                   binding.game.updateBonus(it)
        })
        viewModel.scoreData.observe(this, Observer {
                                        binding.score.setText(it.toString())
        })
        viewModel.gameState.observe(this, Observer {
                                        if (it == GameState.GAMEOVER) {
                                            AlertDialog.Builder(this).setTitle("Game Over").setMessage("GAME OVER")
                                                .setPositiveButton("REPLAY") { diaglog, which -> viewModel.start()
                                                }.setNeutralButton("CANCEL", null)
                                                .show()
                                        }
        })
        viewModel.start()
        binding.btnLeft.setOnClickListener { viewModel.move(Dir.LEFT) }
        binding.btnRight.setOnClickListener { viewModel.move(Dir.RIGHT) }
        binding.btnUp.setOnClickListener { viewModel.move(Dir.UP) }
        binding.btnDown.setOnClickListener { viewModel.move(Dir.DOWN) }
        
        // tvStart.setOnClickListener { // start: 这个界面是多此一举，完全没有必要，扔之
        //     startActivity(Intent(this, SnakeActivity::class.java))
        // }
    }
}