package com.senla.texteditor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.senla.texteditor.FileActivity.Companion.DATA_TXT
import com.senla.texteditor.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EDIT_FILE = "EXTRA_EDIT_FILE"
        const val EXTRA_CREATE_FILE = "EXTRA_CREATE_FILE"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsListeners()
    }

    override fun onResume() {
        super.onResume()

        if (File("$filesDir${File.separator}$DATA_TXT").exists()) {
            configureButtonsVisibility(flag = true)
        } else {
            configureButtonsVisibility(flag = false)
        }
    }

    private fun configureButtonsVisibility(flag: Boolean) {
        binding.createFileButton.apply { isVisible = !flag }
        binding.viewFileButton.apply { isVisible = flag }
        binding.editFileButton.apply { isVisible = flag }
    }

    private fun setButtonsListeners() {
        binding.createFileButton.apply {
            setOnClickListener {
                startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                    putExtra(EXTRA_CREATE_FILE, true)
                })
            }
        }
        binding.settingsButton.apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
        }
        binding.viewFileButton.apply {
            setOnClickListener {
                startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                    putExtra(EXTRA_EDIT_FILE, false)
                })
            }
        }
        binding.editFileButton.apply {
            setOnClickListener {
                startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                    putExtra(EXTRA_EDIT_FILE, true)
                })
            }
        }
    }
}