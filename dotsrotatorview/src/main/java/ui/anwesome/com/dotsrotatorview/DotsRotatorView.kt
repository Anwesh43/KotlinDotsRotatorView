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

    data class State (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f, 0f)

        fun update (stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating (startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : DotsRotatorView, var animated : Boolean = false) {

        fun animate (updatecb : () -> Unit) {
            if (animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class DotsRotator(var i : Int = 0, private val state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val l : Float = Math.min(w,h) * 0.4f
            val r : Float = Math.min(w, h)/15
            var sf : Float = 0f
            for (i in 0..3) {
                sf += state.scales[i+2]
            }
            paint.color = Color.parseColor("#212121")
            canvas.save()
            canvas.translate(w/2, h/2)
            canvas.rotate(90f * sf)
            for (i in 0..2) {
                val ox : Float = 0f
                val dx : Float = -w/4 + i * w/4
                canvas.save()
                canvas.translate(ox + (dx - ox) * state.scales[1],l * state.scales[0])
                canvas.drawCircle(0f, 0f, r, paint)
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer (var view : DotsRotatorView) {
        private val animator : Animator = Animator(view)
        private val dotsRotator : DotsRotator = DotsRotator(0)
        fun render(canvas : Canvas, paint : Paint) {
            dotsRotator.draw(canvas, paint)
            animator.animate {
                dotsRotator.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            dotsRotator.startUpdating {
                animator.start()
            }
        }
    }
}