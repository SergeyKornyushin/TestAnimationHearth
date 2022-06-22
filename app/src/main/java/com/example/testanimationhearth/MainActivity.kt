package com.example.testanimationhearth

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.*
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
            move(cloneView)
        }
    }

    private fun move(view: TextView) {
        view.alpha = 1f
        view.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            ResourcesUtils.getDimensionPixels(R.dimen.default_emoji_size).toFloat()
        )
        view.setTextColor(ResourcesUtils.getColor(R.color.white))

        val yAnimator = ValueAnimator.ofFloat(350f)
        yAnimator.duration = 2000
        yAnimator.addUpdateListener { animation ->
            view.translationY = -ResourcesUtils.getPxByDp((animation.animatedValue as Float)).toFloat()
        }
        yAnimator.interpolator = LinearInterpolator()

        val xAnimator = ValueAnimator.ofFloat(15f)
        xAnimator.duration = 2000
        xAnimator.addUpdateListener { animation ->
            view.translationX = ResourcesUtils.getPxByDp((animation.animatedValue as Float)).toFloat()
        }
        xAnimator.interpolator = CycleInterpolator(2f)

        val animSet = AnimationUtils.loadAnimation(this, R.anim.emoji_animations)

        AnimatorSet().apply {
            playTogether(xAnimator, yAnimator)
            view.startAnimation(animSet)
            start()
        }
    }
}
