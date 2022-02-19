package com.example.emojidemo

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.emojidemo.databinding.ActivityMainBinding
import com.example.emojidemo.reactions.ReactionPopup
import com.example.emojidemo.reactions.ReactionsConfigBuilder
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.emojidemo.reactions.OnReactionSelectedListener
import com.example.emojidemo.reactions.Reaction


class MainActivity : Activity(), OnReactionSelectedListener {

    lateinit var mBinding: ActivityMainBinding
    private val strings = arrayOf("like", "love", "happy", "care", "shock", "sad", "angry")
    val reactionList = ArrayList<Reaction>()

    companion object {
        var like_status = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_like_emoji)!!, "like"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_love_emoji)!!, "love"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_happy_emoji)!!, "happy"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_care_emoji)!!, "care"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_shock_emoji)!!, "shock"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_sad_emoji)!!, "sad"))
        reactionList.add(Reaction(ContextCompat.getDrawable(this, R.drawable.ic_angry_emoji)!!, "angry"))

        val popup = ReactionPopup(
            this,
            ReactionsConfigBuilder(this)
                .withReactions(
                    reactionList
                ).build()
        )


        popup.reactionSelectedListener?.let {
            Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show()
        }

        mBinding.btnReaction.setOnTouchListener(popup)
    }

    override fun selectedReaction(reaction: Reaction?, isLongPress: Boolean) {
        like_status = if (isLongPress) {
            mBinding.btnReaction.setImageDrawable(reaction?.image)
            true
        } else {
            if (like_status) {
                mBinding.btnReaction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_disable_like_emoji))
                false
            } else {
                mBinding.btnReaction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_like_emoji))
                true
            }
        }
    }
}