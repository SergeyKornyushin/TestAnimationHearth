package com.example.testanimationhearth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testanimationhearth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val containerLayout = binding.container
        binding.container.setOnClickListener {
            val cloneView = TextView(this)
            cloneView.layoutParams = binding.emojiText.layoutParams
            cloneView.text = binding.emojiText.text
            containerLayout.addView(cloneView)
        }
//
//        val path1 = Path().apply {
//            arcTo(-50f, 0f, 50f, 150f, 270f, -180f, false)
//        }
        val path1 = Path().apply {
            arcTo(-50f, 450f, 50f, 600f, 90f, -180f, true)
        }
        val path2 = Path().apply {
            arcTo(-50f, 300f, 50f, 450f, 90f, 180f, true)
        }
        val path3 = Path().apply {
            arcTo(-50f, 150f, 50f, 300f, 90f, -180f, true)
        }
        val path4 = Path().apply {
            arcTo(-50f, 0f, 50f, 150f, 90f, 180f, true)
        }

        val animator = ObjectAnimator.ofFloat(
            binding.emojiText, "translationX", "translationY", path1
        ).apply {
            duration = 500
        }
        val animator2 = ObjectAnimator.ofFloat(
            binding.emojiText, "translationX", "translationY", path2
        ).apply {
            duration = 500
        }
        val animator3 = ObjectAnimator.ofFloat(
            binding.emojiText, "translationX", "translationY", path3
        ).apply {
            duration = 500
        }
        val animator4 = ObjectAnimator.ofFloat(
            binding.emojiText, "translationX", "translationY", path4
        ).apply {
            duration = 500
        }
        val valueAnim1 = ValueAnimator.ofFloat(0f, 500f)
        val bouncer = AnimatorSet().apply {
            play(animator).before(animator2)
            play(animator2).before(animator3)
            play(animator3).before(animator4)
            play(animator4).after(animator3)
            interpolator = LinearInterpolator()
        }


        fun move(view: TextView) {
            val yAnimator = ValueAnimator.ofFloat(2000f)
            yAnimator.duration = 2000
            yAnimator.addUpdateListener { animation ->
                view.translationY = -(animation.animatedValue as Float) / 2
            }
            yAnimator.interpolator = LinearInterpolator()

            val xAnimator = ValueAnimator.ofFloat(2000f)
            xAnimator.duration = 2000
            xAnimator.addUpdateListener { animation ->
                view.translationX = -(animation.animatedValue as Float) / 20
            }
            xAnimator.interpolator = CycleInterpolator(5f)

            val alphaAnimator = ValueAnimator.ofFloat(10f)
            alphaAnimator.duration = 1000
            alphaAnimator.addUpdateListener { animation ->
                view.alpha = 1 / (animation.animatedValue as Float)
            }
            alphaAnimator.interpolator = LinearInterpolator()

            val scaleAnimator = ValueAnimator.ofFloat(40f)
            scaleAnimator.duration = 2000
            scaleAnimator.addUpdateListener { animation ->
                Log.i("test4", "move: ${(animation.animatedValue as Float)}")
                view.textSize = (animation.animatedValue as Float)
            }
            scaleAnimator.interpolator = LinearInterpolator()

            AnimatorSet().apply {
                playTogether(yAnimator, xAnimator, scaleAnimator)
                play(alphaAnimator).after(500)
                start()
            }
        }



        binding.emojiText.setOnClickListener {
            move(binding.emojiText)

//            AnimatorSet().apply {
//                play(bouncer)
//                start()
//            }
        }
    }
}
