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

// 需要加一个加减蛇的滑行速度的按钮：使用databinding得到双向数据绑定，吃了7粒小球，得7分

// 希望snake board是位于一个SurfaceView，方便多开一个线程与主UI逻辑分开，提高效率

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SnakeViewModel 
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding.bg.invalidate()

        viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.snake.observe(this, Observer {
                                    binding.game.snakeBody = it
                                    binding.game.invalidate() // invalidate()只能在UI线程操作。但是从重绘速率讲：invalidate()效率高
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
                                                .setPositiveButton("REPLAY") {
                                                    diaglog, which -> viewModel.start()
                                                }.setNeutralButton("CANCEL", null)
                                                .show()
                                        }
        })
        viewModel.start()
        binding.btnLeft.setOnClickListener { viewModel.move(Dir.LEFT) }
        binding.btnRight.setOnClickListener { viewModel.move(Dir.RIGHT) }
        binding.btnUp.setOnClickListener { viewModel.move(Dir.UP) }
        binding.btnDown.setOnClickListener { viewModel.move(Dir.DOWN) }
    }
}