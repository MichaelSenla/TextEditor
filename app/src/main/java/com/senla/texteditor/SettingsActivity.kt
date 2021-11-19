package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.senla.texteditor.FileActivity.Companion.SHARED_PREFERENCES
import com.senla.texteditor.databinding.ActivitySettingsBinding

class SettingsActivity : Activity() {

    companion object {
        const val TEXT_SIZE = "TEXT_SIZE"
        const val TEXT_COLOR = "TEXT_COLOR"
    }

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var textSize = ""
    private var textColor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRadioGroupsListeners()
        sharedPreferences =
            getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        restoreSettings()
    }

    private fun restoreSettings() {
        when (sharedPreferences.getString(TEXT_SIZE, "")) {
            TextSize.TextSize.SMALL.name -> binding.radioButtonTextSizeSmall.isChecked = true
            TextSize.TextSize.MEDIUM.name -> binding.radioButtonTextSizeMedium.isChecked = true
            TextSize.TextSize.BIG.name -> binding.radioButtonTextSizeBig.isChecked = true
        }
        when (sharedPreferences.getString(TEXT_COLOR, "")) {
            TextColor.TextColor.GREEN.name -> binding.radioButtonTextColorGreen.isChecked = true
            TextColor.TextColor.BLACK.name -> binding.radioButtonTextColorBlack.isChecked = true
            TextColor.TextColor.RED.name -> binding.radioButtonTextColorRed.isChecked = true
        }
    }

    private fun setRadioGroupsListeners() {
        binding.textSizeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextSizeSmall -> textSize = TextSize.TextSize.SMALL.name
                R.id.radioButtonTextSizeMedium -> textSize = TextSize.TextSize.MEDIUM.name
                R.id.radioButtonTextSizeBig -> textSize = TextSize.TextSize.BIG.name
            }
        }
        binding.textColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextColorGreen -> textColor = TextColor.TextColor.GREEN.name
                R.id.radioButtonTextColorBlack -> textColor = TextColor.TextColor.BLACK.name
                R.id.radioButtonTextColorRed -> textColor = TextColor.TextColor.RED.name
            }
        }
    }

    override fun onPause() {
        sharedPreferences.edit().apply {
            putString(TEXT_SIZE, textSize)
            putString(TEXT_COLOR, textColor)
        }.apply()

        super.onPause()
    }

}

