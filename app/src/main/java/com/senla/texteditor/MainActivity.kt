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
        binding.apply {
            createFileButton.isVisible = !flag
            viewFileButton.isVisible = flag
            editFileButton.isVisible = flag
        }
    }

    private fun setButtonsListeners() {
        binding.apply {
            createFileButton.setOnClickListener {
                startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                    putExtra(EXTRA_CREATE_FILE, true)
                })
            }

            settingsButton.apply {
                setOnClickListener {
                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                }
            }
            viewFileButton.apply {
                setOnClickListener {
                    startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                        putExtra(EXTRA_EDIT_FILE, false)
                    })
                }
            }
            editFileButton.apply {
                setOnClickListener {
                    startActivity((Intent(this@MainActivity, FileActivity::class.java)).apply {
                        putExtra(EXTRA_EDIT_FILE, true)
                    })
                }
            }
        }
    }
}