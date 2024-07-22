package com.example.testchart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dewakoding.androidchartjs.model.BubbleEntity
import com.dewakoding.androidchartjs.util.ChartType
import com.example.testchart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.androidChart1.setChart(
            ChartType.BAR,
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"),
            arrayOf(10, 3, 5, 8, 9),
            arrayOf("#FF5733", "#FF5733", "#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
        binding.androidChart2.setChart(
            ChartType.LINE,
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"),
            arrayOf(10, 3, 5, 8, 9),
            arrayOf("#FF5733", "#FF5733", "#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
        binding.androidChart3.setChart(
            ChartType.PIE,
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"),
            arrayOf(10, 3, 5, 8, 9),
            arrayOf("#FF5733", "#FF5733", "#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
        binding.androidChart4.setChart(
            ChartType.DOUGHNUT,
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"),
            arrayOf(10, 3, 5, 8, 9),
            arrayOf("#FF5733", "#FF5733", "#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
        binding.androidChart5.setChart(
            ChartType.POLAR_AREA,
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"),
            arrayOf(10, 3, 5, 8, 9),
            arrayOf("#FF5733", "#FF5733", "#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
        binding.androidChart6.setBubbleChart(
            arrayOf(
                BubbleEntity(10,20,30),
                BubbleEntity(25, 45, 20),
                BubbleEntity(15, 25, 5)
            ),
            arrayOf("#FF5733", "#FF5733", "#FF5733"),
            "of quantity")
    }
}