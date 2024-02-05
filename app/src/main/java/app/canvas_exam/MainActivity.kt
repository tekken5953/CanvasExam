package app.canvas_exam

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import app.canvas_exam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var canvasView: DrawCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        canvasView = binding.drawerLayout

        binding.apply {
            changeColor.setOnClickListener {
                val color = canvasView.setColor()
                changeColor.imageTintList = ColorStateList.valueOf(color)
            }

            clearCanvas.setOnClickListener {
                canvasView.clearCanvas()
                changeColor.imageTintList = ColorStateList.valueOf(Color.BLACK)
            }

            changeBold.setOnClickListener {
                canvasView.setThickness(DrawCanvas.BOLD)
                changeCircle(DrawCanvas.BOLD)
            }
            changeLight.setOnClickListener {
                canvasView.setThickness(DrawCanvas.LIGHT)
                changeCircle(DrawCanvas.LIGHT)
            }
            changeMedium.setOnClickListener {
                canvasView.setThickness(DrawCanvas.MEDIUM)
                changeCircle(DrawCanvas.MEDIUM)
            }
            undo.setOnClickListener {
                canvasView.undoLast()
            }
        }
    }

    private fun changeCircle(tk: Float) {
        when(tk) {
            DrawCanvas.LIGHT -> {
                binding.changeLight.setImageDrawable(getCheckedCircle())
                binding.changeBold.setImageDrawable(getUncheckedCircle())
                binding.changeMedium.setImageDrawable(getUncheckedCircle())
            }
            DrawCanvas.BOLD -> {
                binding.changeLight.setImageDrawable(getUncheckedCircle())
                binding.changeBold.setImageDrawable(getCheckedCircle())
                binding.changeMedium.setImageDrawable(getUncheckedCircle())
            }
            DrawCanvas.MEDIUM -> {
                binding.changeLight.setImageDrawable(getUncheckedCircle())
                binding.changeBold.setImageDrawable(getUncheckedCircle())
                binding.changeMedium.setImageDrawable(getCheckedCircle())
            }
        }
    }

    private fun getUncheckedCircle(): Drawable? {
        return ResourcesCompat.getDrawable(resources,R.drawable.circle , null)
    }

    private fun getCheckedCircle(): Drawable? {
        return ResourcesCompat.getDrawable(resources,R.drawable.circle_check , null)
    }
}