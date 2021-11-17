package com.senla.texteditor

import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.senla.texteditor.MainActivity.Companion.sharedPreferences
import com.senla.texteditor.databinding.ActivitySettingsBinding

class SettingsActivity : Activity() {

    companion object {
        const val TEXT_SIZE = "TEXT_SIZE"
        const val TEXT_COLOR = "TEXT_COLOR"
        const val TEXT_SIZE_SMALL = 14
        const val TEXT_SIZE_MEDIUM = 20
        const val TEXT_SIZE_BIG = 32
    }

    private lateinit var binding: ActivitySettingsBinding
    private var textSize = 0
    private var textColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRadioButtonsListeners()
    }

    private fun setRadioButtonsListeners() {
        binding.textSizeButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextSizeSmall -> textSize = TEXT_SIZE_SMALL

                R.id.radioButtonTextSizeMedium -> textSize = TEXT_SIZE_MEDIUM

                R.id.radioButtonTextSizeBig -> textSize = TEXT_SIZE_BIG
            }
        }
        binding.textColorButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextColorGreen -> textColor =
                    ContextCompat.getColor(applicationContext, R.color.green)

                R.id.radioButtonTextColorBlack -> textColor =
                    ContextCompat.getColor(applicationContext, R.color.black)

                R.id.radioButtonTextColorRed -> textColor =
                    ContextCompat.getColor(applicationContext, R.color.red)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (textSize != 0 || textColor != 0) {
            sharedPreferences.edit().apply {
                if (textSize != 0) {
                    putInt(
                        TEXT_SIZE,
                        textSize
                    )
                }
                if (textColor != 0) {
                    putInt(
                        TEXT_COLOR,
                        textColor
                    )
                }
            }.apply()
        }
    }
}