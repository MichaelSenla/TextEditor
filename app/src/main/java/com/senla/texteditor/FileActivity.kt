package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.senla.texteditor.MainActivity.Companion.EXTRA_CREATE_FILE
import com.senla.texteditor.MainActivity.Companion.EXTRA_EDIT_FILE
import com.senla.texteditor.MainActivity.Companion.EXTRA_VIEW_FILE
import com.senla.texteditor.MainActivity.Companion.sharedPreferences
import com.senla.texteditor.SettingsActivity.Companion.TEXT_SIZE_MEDIUM
import com.senla.texteditor.databinding.ActivityCreatingFileBinding
import java.io.File
import java.io.FileOutputStream

class FileActivity : Activity() {

    companion object {
        const val DATA_TXT = "data.txt"
        const val FILE_IS_CREATED_KEY = "FILE_IS_CREATED_KEY"
        const val DEFAULT_TEXT_SIZE = 18
        const val DEFAULT_TEXT_COLOR = "000000"
        var fileIsCreated = false
    }

    private lateinit var binding: ActivityCreatingFileBinding
    private lateinit var fileOutputStream: FileOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatingFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (sharedPreferences.getBoolean(FILE_IS_CREATED_KEY, false)) {
            loadData()
        }
        if (intent.getStringExtra(EXTRA_VIEW_FILE).equals(EXTRA_VIEW_FILE)) {
            changeToViewFileLayout()
        } else if (intent.getStringExtra(EXTRA_EDIT_FILE).equals(EXTRA_EDIT_FILE)) {
            changeToEditFileLayout()
        }
    }

    override fun onPause() {
        super.onPause()
        if (intent.getStringExtra(EXTRA_CREATE_FILE).equals(EXTRA_CREATE_FILE)) {
            createFile()
        }
        if (intent.getStringExtra(EXTRA_EDIT_FILE).equals(EXTRA_EDIT_FILE)) {
            fileOutputStream = openFileOutput(DATA_TXT, Context.MODE_PRIVATE)
            fileOutputStream.write(
                binding.userInput.text.toString()
                    .toByteArray()
            )
            fileOutputStream.close()
        }
    }

    private fun changeToEditFileLayout() {
        binding.userInput.visibility = View.VISIBLE
        binding.viewInput.visibility = View.GONE
    }

    private fun changeToViewFileLayout() {
        binding.viewInput.visibility = View.VISIBLE
        binding.userInput.visibility = View.GONE
    }

    private fun createFile() {
        fileOutputStream = openFileOutput(DATA_TXT, Context.MODE_PRIVATE)
        fileOutputStream.write(binding.userInput.text.toString().toByteArray())
        fileOutputStream.close()
        fileIsCreated = true
        sharedPreferences.edit().putBoolean(FILE_IS_CREATED_KEY, fileIsCreated).apply()
        sharedPreferences.edit().putInt(SettingsActivity.TEXT_SIZE, TEXT_SIZE_MEDIUM).apply()
        sharedPreferences.edit().putInt(
            SettingsActivity.TEXT_COLOR, ContextCompat
                .getColor(applicationContext, R.color.black)
        ).apply()
    }

    private fun loadData() {
        binding.userInput.setText(readLineByLine())
        binding.userInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            sharedPreferences.getInt(SettingsActivity.TEXT_SIZE, DEFAULT_TEXT_SIZE)
                .toFloat()
        )
        binding.userInput.setTextColor(
            sharedPreferences.getInt(
                SettingsActivity.TEXT_COLOR,
                DEFAULT_TEXT_COLOR.toInt()
            )
        )
        binding.viewInput.text = readLinesWithIndexes()
        binding.viewInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            sharedPreferences.getInt(SettingsActivity.TEXT_SIZE, DEFAULT_TEXT_SIZE)
                .toFloat()
        )
        binding.viewInput.setTextColor(
            sharedPreferences.getInt(
                SettingsActivity.TEXT_COLOR,
                ContextCompat.getColor(applicationContext, R.color.black)
            )
        )
    }

    private fun readLineByLine(): String {
        val stringBuilder = StringBuilder()
        File("$filesDir/$DATA_TXT").useLines { lines ->
            lines.forEach {
                stringBuilder.append("$it \n")
            }
        }
        return stringBuilder.toString()
    }

    private fun readLinesWithIndexes(): String {
        val stringBuilder = StringBuilder()
        File("$filesDir/$DATA_TXT").useLines { lines ->
            lines.forEachIndexed { index, value ->
                stringBuilder.append("${index + 1}. $value \n")
            }
        }
        return stringBuilder.toString()
    }
}
