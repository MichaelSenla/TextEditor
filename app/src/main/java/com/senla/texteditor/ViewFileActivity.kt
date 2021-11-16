package com.senla.texteditor

import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import com.senla.texteditor.CreatingFileActivity.Companion.DATA_TXT
import com.senla.texteditor.MainActivity.Companion.sharedPreferences
import com.senla.texteditor.databinding.ActivityViewFileBinding
import java.io.File

class ViewFileActivity : Activity() {

    companion object {
        private const val COUNTER = 1
        const val DEFAULT_TEXT_SIZE = 18
        const val DEFAULT_TEXT_COLOR = "000000"
        const val TEXT_KEY = "TEXT_KEY"
    }

    private lateinit var binding: ActivityViewFileBinding
    private val lineList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        binding.viewInput.text = readLineByLine()
    }

    private fun readLineByLine(): String {
        File("$filesDir/$DATA_TXT").bufferedReader().useLines { lines ->
            var counter = COUNTER; lines.forEach {
            lineList.add("$counter. $it \n")
            counter++
        }
        }
        val stringBuilder = StringBuilder()
        lineList.forEach {
            stringBuilder.append(it)
        }
        sharedPreferences.edit().putString(TEXT_KEY, stringBuilder.toString()).apply()
        return stringBuilder.toString()
    }

    private fun loadData() {
        binding.viewInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            sharedPreferences.getInt(SettingsActivity.TEXT_SIZE, DEFAULT_TEXT_SIZE).toFloat()
        )
        binding.viewInput.setTextColor(
            sharedPreferences.getInt(
                SettingsActivity.TEXT_COLOR,
                DEFAULT_TEXT_COLOR.toInt()
            )
        )
    }
}