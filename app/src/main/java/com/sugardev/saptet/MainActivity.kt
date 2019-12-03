package com.sugardev.saptet


import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sugardev.saptet.extension.showSlideInDown
import com.sugardev.saptet.extension.turnOnFullScreen
import com.sugardev.saptet.extension.visible
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val handle = Handler()
    private val tetHolidays = "25/01/2020"
    private val pattern = "dd/MM/yyyy"
    private lateinit var cd: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.turnOnFullScreen()
        starAnim()
        pauseAnim()
        initView()
    }


    private fun initView() {
        val now = Calendar.getInstance().time.time
        val tet = SimpleDateFormat(pattern, Locale.getDefault()).parse(tetHolidays).time
        val period = tet - now
        cd = object : CountDownTimer(period, 1000) {
            override fun onFinish() {
            }

            override fun onTick(p0: Long) {
                tvDate.text = String.format("%02d", p0 / (24 * 60 * 60 * 1000))
                tvHour.text = String.format("%02d",  p0 / (60 * 60 * 1000) % 24)
                tvMinute.text = String.format("%02d", p0 / (60 * 1000) % 60)
                tvSecond.text = String.format("%02d", p0 / 1000 % 60)
            }
        }
        cd.start()

    }

    private fun pauseAnim() {
        handle.postDelayed({
            animLottie.pauseAnimation()
            animLottie.showSlideInDown(400) {
                vgContent.visible()
            }

        }, 2000)
    }

    private fun starAnim() {
        animLottie.playAnimation()
    }

    override fun onDestroy() {
        handle.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
