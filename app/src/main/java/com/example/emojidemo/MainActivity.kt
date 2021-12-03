package com.example.emojidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.emojidemo.databinding.ActivityMainBinding
import com.example.emojidemo.reactions.ReactionPopup
import com.example.emojidemo.reactions.ReactionsConfigBuilder
import android.view.MotionEvent

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    private val strings = arrayOf("like", "love", "happy", "care", "shock", "sad", "angry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val popup = ReactionPopup(
            this,
            ReactionsConfigBuilder(this)
                .withReactions(
                    intArrayOf(
                        R.drawable.ic_like_emoji,
                        R.drawable.ic_love_emoji,
                        R.drawable.ic_happy_emoji,
                        R.drawable.ic_care_emoji,
                        R.drawable.ic_shock_emoji,
                        R.drawable.ic_sad_emoji,
                        R.drawable.ic_angry_emoji
                    )
                )
                .withReactionTexts { position -> strings[position] }
                .build())

        mBinding.btnReaction.setOnLongClickListener(popup)
    }
}