package com.sugardev.saptet


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sugardev.saptet.extension.showSlideInDown
import com.sugardev.saptet.extension.turnOnFullScreen
import com.sugardev.saptet.extension.visible
import com.sugardev.saptet.service.NotifyCountDownService
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val handle = Handler()
    private var tetHolidays = ""
    private val pattern = "dd/MM/yyyy"
    private lateinit var cd: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer


    object Tet {
        const val tet20 = "25/01/2020"
        const val tet21 = "12/02/2021"
        const val tet22 = "01/02/2022"
        const val tet23 = "22/01/2023"
        const val tet20Title = "Canh Tý 2020"
        const val tet21Title = "Tân Sửu 2021"
        const val tet22Title = "Nhâm Dần 2022"
        const val tet23Title = "Quý Mão 2022"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.turnOnFullScreen()
        initTet()

        starAnim()
        pauseAnim()
        initView()
    }

    private fun initTet() {
        val currentTime = Calendar.getInstance().time
        tetHolidays = when (SimpleDateFormat("yyyy").format(currentTime)) {
            "2020" -> {
                tvTitle.text = Tet.tet20Title
                Tet.tet20
            }
            "2021" -> {
                tvTitle.text = Tet.tet21Title
                Tet.tet21
            }
            "2022" -> {
                tvTitle.text = Tet.tet22Title
                Tet.tet22
            }
            "2023" -> {
                tvTitle.text = Tet.tet23Title
                Tet.tet23
            }
            else -> {
                tvTitle.text = Tet.tet20Title
                Tet.tet20
            }
        }
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
                tvHour.text = String.format("%02d", p0 / (60 * 60 * 1000) % 24)
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

    override fun onPause() {
        mediaPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        handle.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
