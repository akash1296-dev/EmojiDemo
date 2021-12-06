package com.example.emojidemo.reactions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.example.emojidemo.MainActivity
import java.util.*

/**
 * Entry point for reaction popup.
 */
class ReactionPopup @JvmOverloads constructor(
    context: Context,
    reactionsConfig: ReactionsConfig,
    var reactionSelectedListener: ReactionSelectedListener? = null,
    var reactionPopupStateChangeListener: ReactionPopupStateChangeListener? = null
) : PopupWindow(context), View.OnTouchListener {

    private val rootView = FrameLayout(context).also {
        it.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    val view: ReactionViewGroup by lazy(LazyThreadSafetyMode.NONE) {
        // Lazily inflate content during first display
        ReactionViewGroup(context, reactionsConfig).also {
            it.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )

            it.reactionSelectedListener = reactionSelectedListener

            it.selectedReaction = context as MainActivity

            it.reactionPopupStateChangeListener = reactionPopupStateChangeListener

            // Just add the view,
            // it will position itself depending on the display preference.
            rootView.addView(it)

            it.dismissListener = ::dismiss
        }
    }

    init {
        contentView = rootView
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    /*@SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (!isShowing) {
                    // Show fullscreen with button as context provider
                    showAtLocation(v, Gravity.NO_GRAVITY, 0, 0)
                    view.show(event, v)
                    reactionPopupStateChangeListener?.invoke(true)
                }
                return view.onTouchEvent(event)
            }

            MotionEvent.ACTION_UP -> {
                return view.onTouchEvent(event)
            }

            else -> {
                return view.onTouchEvent(event)
            }
        }
    }*/

    private val MAX_CLICK_DURATION = 200
    private var startClickTime: Long = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if(clickDuration > MAX_CLICK_DURATION) {
                    if (!isShowing) {
                        // Show fullscreen with button as context provider
                        showAtLocation(v, Gravity.NO_GRAVITY, 0, 0)
                        view.show(event, v)
                        reactionPopupStateChangeListener?.invoke(true)
                    }
                    return view.onTouchEvent(event)
                }
            }

            MotionEvent.ACTION_UP -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if(clickDuration > MAX_CLICK_DURATION) {
                    if (!isShowing) {
                        // Show fullscreen with button as context provider
                        showAtLocation(v, Gravity.NO_GRAVITY, 0, 0)
                        view.show(event, v)
                        reactionPopupStateChangeListener?.invoke(true)
                    }
                    Toast.makeText(v.context, "long-click", Toast.LENGTH_SHORT).show()
                    return view.onTouchEvent(event)
                } else {
                    //click event has occurred
                    Toast.makeText(v.context, "single-click", Toast.LENGTH_SHORT).show()
                }
            }

            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis

            }
        }
        return true
    }

    override fun dismiss() {
        view.dismiss()
        super.dismiss()
    }
}
