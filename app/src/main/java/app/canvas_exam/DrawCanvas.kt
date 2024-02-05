package app.canvas_exam

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
class DrawCanvas(context: Context, attrs: AttributeSet): View(context, attrs) {
    companion object {
        const val LIGHT = 3F
        const val MEDIUM = 9F
        const val BOLD = 15F
    }
    private var currentColor = Color.BLACK
    private var thickness = MEDIUM
    private val paths = mutableListOf<Pair<Path,Paint>>()
    private val path = Path()

    private var lastX = 0F
    private var lastY = 0F

    private val paintConfig = Paint().apply {
        isAntiAlias = true
        strokeWidth = thickness
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
    }

    init {
        setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> startDrawing(x,y)
                MotionEvent.ACTION_MOVE -> continueDrawing(x,y)
                MotionEvent.ACTION_UP -> stopDrawing()
            }
            invalidate()
            true
        }
    }

    private fun startDrawing(x: Float, y: Float) {
        path.reset()
        path.moveTo(x,y)
        lastX = x
        lastY = y
        paintConfig.strokeWidth = thickness
        paintConfig.color = currentColor
    }

    private fun continueDrawing(x: Float, y: Float) {
        path.quadTo(lastX,lastY,(x+lastX) / 2, (y+lastY) / 2)
        lastX = x
        lastY = y
    }

    private fun stopDrawing() {
        val paint = Paint(paintConfig)
        paths.add(Pair(Path(path), paint))
        path.lineTo(lastX,lastY)
        path.reset()
    }

    private fun getRandomColor(): Int {
        return Color.rgb(
            (0..255).random(),(0..255).random(),(0..255).random()
        )
    }

    fun setColor(): Int {
        currentColor = getRandomColor()
        return currentColor
    }

    fun setThickness(thick: Float) {
        thickness = thick
    }

    fun clearCanvas() {
        if (paths.size > 0) {
            currentColor = Color.BLACK
            thickness = MEDIUM
            paths.clear()
            invalidate()
        }
    }

    fun undoLast() {
        if (paths.size > 0) {
            paths.removeLast()
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for ((p,paint) in paths) {
            canvas?.drawPath(p,paint)
        }
        canvas?.drawPath(path, paintConfig)
    }
}