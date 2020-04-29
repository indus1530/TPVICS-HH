package edu.aku.hassannaqvi.tpvics_hh.ui.other

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.repository.getEnumData
import edu.aku.hassannaqvi.tpvics_hh.repository.setProvinceDistricts
import kotlinx.coroutines.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashscreenActivity : Activity() {
    private val activityScope = CoroutineScope(Dispatchers.Main)

    init {
        provinces = mutableListOf("....")
        districts = mutableMapOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        activityScope.launch {
            val def = withContext(Dispatchers.Main) { getEnumData(this@SplashscreenActivity) }
            if (def.isNotEmpty())
                withContext(Dispatchers.Main) { setProvinceDistricts(def) }
            delay(SPLASH_TIME_OUT.toLong())
            finish()
            startActivity(Intent(this@SplashscreenActivity, LoginActivity::class.java))
        }
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
        lateinit var provinces: List<String>
        lateinit var districts: MutableMap<String, String>
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}