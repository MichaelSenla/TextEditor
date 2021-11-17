package com.senla.texteditor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.senla.texteditor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        const val EXTRA_VIEW_FILE = "EXTRA_VIEW_FILE"
        const val EXTRA_EDIT_FILE = "EXTRA_EDIT_FILE"
        const val EXTRA_CREATE_FILE = "EXTRA_CREATE_FILE"
        lateinit var sharedPreferences: SharedPreferences
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        setButtonsListeners()
        if (sharedPreferences.getBoolean(FileActivity.FILE_IS_CREATED_KEY, false)) {
            changeToFileCreatedButtons()
        } else {
            changeToFileNotCreatedButtons()
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.getBoolean(FileActivity.FILE_IS_CREATED_KEY, false)) {
            changeToFileCreatedButtons()
        } else {
            changeToFileNotCreatedButtons()
        }
    }

    private fun changeToFileCreatedButtons() {
        binding.createFileButton.visibility = View.GONE
        binding.viewFileButton.visibility = View.VISIBLE
        binding.editButton.visibility = View.VISIBLE
    }

    private fun changeToFileNotCreatedButtons() {
        binding.createFileButton.visibility = View.VISIBLE
        binding.viewFileButton.visibility = View.GONE
        binding.editButton.visibility = View.GONE
    }

    private fun setButtonsListeners() {
        binding.createFileButton.setOnClickListener {
            startActivity(Intent(applicationContext, FileActivity::class.java).also {
                it.putExtra(EXTRA_CREATE_FILE, EXTRA_CREATE_FILE)
            })
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        binding.viewFileButton.setOnClickListener {
            startActivity(Intent(applicationContext, FileActivity::class.java).also {
                it.putExtra(EXTRA_VIEW_FILE, EXTRA_VIEW_FILE)
            })
        }
        binding.editButton.setOnClickListener {
            startActivity(Intent(applicationContext, FileActivity::class.java).also {
                it.putExtra(EXTRA_EDIT_FILE, EXTRA_EDIT_FILE)
            })
        }
    }
}