package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import com.senla.texteditor.MainActivity.Companion.sharedPreferences
import com.senla.texteditor.ViewFileActivity.Companion.TEXT_KEY
import com.senla.texteditor.databinding.ActivityEditFileBinding
import java.io.FileOutputStream

class EditFileActivity : Activity() {

    lateinit var binding: ActivityEditFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
    }

    private fun loadData() {
        binding.userInput.setText(sharedPreferences.getString(TEXT_KEY, ""))
        binding.userInput.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            sharedPreferences.getInt(SettingsActivity.TEXT_SIZE, ViewFileActivity.DEFAULT_TEXT_SIZE)
                .toFloat()
        )
        binding.userInput.setTextColor(
            sharedPreferences.getInt(
                SettingsActivity.TEXT_COLOR,
                ViewFileActivity.DEFAULT_TEXT_COLOR.toInt()
            )
        )
    }

    override fun onPause() {
        super.onPause()

        val fileOutputStream: FileOutputStream =
            openFileOutput(CreatingFileActivity.DATA_TXT, Context.MODE_PRIVATE)
        fileOutputStream.write(binding.userInput.text.toString().toByteArray())
    }
}