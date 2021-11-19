package com.senla.texteditor

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
            getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        setDefaultSettings()
        restoreSettings()
    }

    private fun setDefaultSettings() {
        binding.radioButtonTextSizeMedium.isChecked = true
        binding.radioButtonTextColorBlack.isChecked = true
    }

    private fun restoreSettings() {
        when (sharedPreferences.getString(TEXT_SIZE, "")) {
            TextSize.SMALL.name -> binding.radioButtonTextSizeSmall.isChecked = true
            TextSize.MEDIUM.name -> binding.radioButtonTextSizeMedium.isChecked = true
            TextSize.BIG.name -> binding.radioButtonTextSizeBig.isChecked = true
        }
        when (sharedPreferences.getString(TEXT_COLOR, "")) {
            TextColor.GREEN.name -> binding.radioButtonTextColorGreen.isChecked = true
            TextColor.BLACK.name -> binding.radioButtonTextColorBlack.isChecked = true
            TextColor.RED.name -> binding.radioButtonTextColorRed.isChecked = true
        }
    }

    private fun setRadioGroupsListeners() {
        binding.textSizeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextSizeSmall -> textSize = TextSize.SMALL.name
                R.id.radioButtonTextSizeMedium -> textSize = TextSize.MEDIUM.name
                R.id.radioButtonTextSizeBig -> textSize = TextSize.BIG.name
            }
        }
        binding.textColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonTextColorGreen -> textColor = TextColor.GREEN.name
                R.id.radioButtonTextColorBlack -> textColor = TextColor.BLACK.name
                R.id.radioButtonTextColorRed -> textColor = TextColor.RED.name
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

enum class TextSize {
    SMALL,
    MEDIUM,
    BIG
}

enum class TextColor {
    GREEN,
    BLACK,
    RED
}
