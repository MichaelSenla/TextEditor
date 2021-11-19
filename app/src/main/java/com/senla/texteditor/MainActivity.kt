package com.senla.texteditor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.senla.texteditor.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        const val EXTRA_EDIT_FILE = "EXTRA_EDIT_FILE"
        const val EXTRA_CREATE_FILE = "EXTRA_CREATE_FILE"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        setButtonsListeners()
        if (File(FileActivity.DATA_TXT).exists()) {
            configureButtonsVisibility(flag = true)
        } else {
            configureButtonsVisibility(flag = false)
        }
    }

    override fun onResume() {
        super.onResume()

        if (sharedPreferences.getBoolean(FileActivity.FILE_IS_CREATED_KEY, false)) {
            configureButtonsVisibility(flag = true)
        } else {
            configureButtonsVisibility(flag = false)
        }
    }

    private fun configureButtonsVisibility(flag: Boolean) {
        binding.createFileButton.isVisible = !flag
        binding.viewFileButton.isVisible = flag
        binding.editFileButton.isVisible = flag
    }

    private fun setButtonsListeners() {
        binding.createFileButton.setOnClickListener {
            startActivity(Intent(this, FileActivity::class.java).also {
                it.putExtra(EXTRA_CREATE_FILE, true)
            })
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.viewFileButton.setOnClickListener {
            startActivity(Intent(this, FileActivity::class.java).also {
                it.putExtra(EXTRA_EDIT_FILE, false)
            })
        }
        binding.editFileButton.setOnClickListener {
            startActivity(Intent(this, FileActivity::class.java).also {
                it.putExtra(EXTRA_EDIT_FILE, true)
            })
        }
    }
}