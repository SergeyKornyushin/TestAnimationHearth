package com.example.testanimationhearth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Path
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.testanimationhearth.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.heartImage.setOnClickListener {
            heartOnClick()
        }
    }

    private fun heartOnClick() {
        // Disable clips on all parent generations
        disableAllParentsClip(binding.heartImage)

        // Create clone
        val imageClone = cloneImage()

        // Animate
        animateFlying(imageClone)
        animateFading(imageClone)
    }

    private fun cloneImage(): ImageView {
        val clone = ImageView(this)
        clone.layoutParams = binding.heartImage.layoutParams
        clone.setImageDrawable(binding.heartImage.drawable)
        binding.cloneContainer.addView(clone)
        return clone
    }

    private fun animateFlying(image: ImageView) {
        val x = 0f
        val y = 0f
        val r = Random.nextInt(1000, 5000)
        val angle = 50f

        val path = Path().apply {
            when (r % 2) {
                0 -> arcTo(RectF(x, y - r, x + 2 * r, y + r), 180f, angle)
                else -> arcTo(RectF(x - 2 * r, y - r, x, y + r), 0f, angle)
            }
        }

        ObjectAnimator.ofFloat(image, View.X, View.Y, path).apply {
            duration = 1000
            start()
        }
    }

    private fun animateFading(image: ImageView) {
        image.animate()
            .alpha(0f)
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.cloneContainer.removeView(image)
                }
            })
    }

    private fun disableAllParentsClip(view: View) {
        var view = view
        view.parent?.let {
            while (view.parent is ViewGroup) {
                val viewGroup = view.parent as ViewGroup
                viewGroup.clipChildren = false
                viewGroup.clipToPadding = false
                view = viewGroup
            }
        }
    }
}