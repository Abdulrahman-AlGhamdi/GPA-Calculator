package com.ss.gpacalculator.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ss.gpacalculator.BuildConfig
import com.ss.gpacalculator.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        onPreferenceClick()
        showData()
    }

    private fun onPreferenceClick() {
        val darkMode = findPreference(getString(R.string.dark_mode_key)) as? Preference
        val contactMe = findPreference(getString(R.string.contact_me_key)) as? Preference

        darkMode?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true
        }

        contactMe?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Abdulrahman.SS.AlGhamdi@Gmail.Com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "GPA Calculator: suggestion/issue email")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Sending Email"))
            true
        }
    }

    private fun showData() {
        val version = findPreference(getString(R.string.version_key)) as? Preference
        version?.summary = getString(R.string.version_summary, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}