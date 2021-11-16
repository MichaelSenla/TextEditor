package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.senla.texteditor.MainActivity.Companion.sharedPreferences
import com.senla.texteditor.databinding.ActivityCreatingFileBinding
import java.io.FileOutputStream

class CreatingFileActivity : Activity() {

    companion object {
        const val DATA_TXT = "data.txt"
        const val FILE_IS_CREATED_KEY = "FILE_IS_CREATED_KEY"
        var fileIsCreated = false
    }

    private lateinit var binding: ActivityCreatingFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatingFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()

        val fileOutputStream: FileOutputStream = openFileOutput(DATA_TXT, Context.MODE_PRIVATE)
        fileOutputStream.write(binding.userInput.text.toString().toByteArray())
        fileIsCreated = true
        sharedPreferences.edit().putBoolean(FILE_IS_CREATED_KEY, fileIsCreated).apply()
    }
}