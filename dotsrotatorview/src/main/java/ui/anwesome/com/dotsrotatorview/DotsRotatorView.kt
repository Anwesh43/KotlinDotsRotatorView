package ui.anwesome.com.dotsrotatorview

/**
 * Created by anweshmishra on 16/04/18.
 */

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent

class DotsRotatorView(ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        return true
    }
}