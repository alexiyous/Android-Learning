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

        test()

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

    private fun test() {
        val focusTimeInMinutes = 120000 / 60000

        // Create x-axis labels for hours from 0 AM to 12 PM
        val xAxisLabels = arrayOf("0 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM")

        // Create y-axis values for focus time in minutes (multiples of 5)
        val yAxisValues = Array(13) { focusTimeInMinutes.toInt() }

        // Set the chart with the appropriate labels and values
        binding.androidChart1.setChart(
            ChartType.LINE,
            xAxisLabels,
            yAxisValues,
            arrayOf("#FF5733"), // Single color for all points
            "Focus Time (minutes)"
        )
    }
}