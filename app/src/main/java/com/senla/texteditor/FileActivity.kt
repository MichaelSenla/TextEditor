package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.senla.texteditor.MainActivity.Companion.EXTRA_CREATE_FILE
import com.senla.texteditor.MainActivity.Companion.EXTRA_EDIT_FILE
import com.senla.texteditor.databinding.ActivityCreatingFileBinding
import java.io.File

class FileActivity : Activity() {

    companion object {
        const val DATA_TXT = "data.txt"
        const val DEFAULT_TEXT_SIZE = ""
        const val DEFAULT_TEXT_COLOR = "000000"
        const val NEW_LINE = "\n"
        const val NUMBER_ONE = 1
        const val DOT = "."
    }

    private lateinit var binding: ActivityCreatingFileBinding
    private lateinit var file: File
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatingFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        if (intent.getBooleanExtra(EXTRA_CREATE_FILE, false)) {
            file = getFile()
            return
        }
        if (intent.getBooleanExtra(EXTRA_EDIT_FILE, true)) {
            configureButtonsVisibility(true)
            setEditText()
        } else {
            configureButtonsVisibility(false)
            setTextView()
        }
    }

    override fun onPause() {
        super.onPause()

        if (intent.getBooleanExtra(EXTRA_CREATE_FILE, false)) {
            saveFile()
        }
        if (intent.getBooleanExtra(EXTRA_EDIT_FILE, false)) {
            file = getFile()
            file.writeText(binding.userInput.text.toString())
        }
    }

    private fun configureButtonsVisibility(flag: Boolean) {
        binding.userInput.isVisible = flag
        binding.viewInput.isVisible = !flag
    }

    private fun saveFile() {
        file.writeText(binding.userInput.text.toString())
        file.createNewFile()
    }

    private fun setTextView() {
        binding.viewInput.text = readLinesWithIndexes()
        binding.viewInput.movementMethod = ScrollingMovementMethod()
        binding.viewInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            when (sharedPreferences.getString(SettingsActivity.TEXT_SIZE, DEFAULT_TEXT_SIZE)) {
                TextSize.SMALL.name -> resources.getDimension(R.dimen.text_size_small)
                TextSize.BIG.name -> resources.getDimension(R.dimen.text_size_big)
                else -> resources.getDimension(R.dimen.text_size_medium)
            }
        )
        binding.viewInput.setTextColor(
            when (sharedPreferences.getString(SettingsActivity.TEXT_COLOR, DEFAULT_TEXT_SIZE)) {
                TextColor.GREEN.name -> ContextCompat.getColor(this, R.color.green)
                TextColor.RED.name -> ContextCompat.getColor(this, R.color.red)
                else -> ContextCompat.getColor(this, R.color.black)
            }
        )
    }

    private fun setEditText() {
        binding.userInput.setText(readLineByLine())
        binding.userInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            when (sharedPreferences.getString(SettingsActivity.TEXT_SIZE, DEFAULT_TEXT_SIZE)) {
                TextSize.SMALL.name -> resources.getDimension(R.dimen.text_size_small)
                TextSize.BIG.name -> resources.getDimension(R.dimen.text_size_big)
                else -> resources.getDimension(R.dimen.text_size_medium)
            }
        )
        binding.userInput.setTextColor(
            when (sharedPreferences.getString(SettingsActivity.TEXT_COLOR, DEFAULT_TEXT_COLOR)) {
                TextColor.GREEN.name -> ContextCompat.getColor(applicationContext, R.color.green)
                TextColor.RED.name -> ContextCompat.getColor(applicationContext, R.color.red)
                else -> ContextCompat.getColor(applicationContext, R.color.black)
            }
        )
    }

    private fun readLineByLine(): String {
        val stringBuilder = StringBuilder()
        getFile().useLines { lines ->
            lines.forEach {
                stringBuilder.apply {
                    append(it)
                    append(NEW_LINE)
                }
            }
        }
        return stringBuilder.toString()
    }

    private fun readLinesWithIndexes(): String {
        val stringBuilder = StringBuilder()
        getFile().useLines { lines ->
            lines.forEachIndexed { index, value ->
                with(stringBuilder) {
                    append("${index + NUMBER_ONE}")
                    append(DOT)
                    append(" $value ")
                    append(NEW_LINE)
                }
            }
        }
        return stringBuilder.toString()
    }

    private fun getFile(): File = File("$filesDir${File.separator}$DATA_TXT")

}
