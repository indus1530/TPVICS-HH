package edu.aku.hassannaqvi.tpvics_hh.ui.other

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import edu.aku.hassannaqvi.tpvics_hh.CONSTANTS
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.models.Districts
import kotlinx.coroutines.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashscreenActivity : Activity() {
    private val activityScope = CoroutineScope(Dispatchers.Main)

    init {
        provinces = mutableListOf("....")
        districtsMap = mutableMapOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        activityScope.launch {
            /*val def = withContext(Dispatchers.Main) { getEnumData(this@SplashscreenActivity) }
            if (def.isNotEmpty())
                withContext(Dispatchers.Main) { setProvinceDistricts(this@SplashscreenActivity, def) }*/
            delay(SPLASH_TIME_OUT.toLong())
            finish()
            startActivity(Intent(this@SplashscreenActivity, LoginActivity::class.java).putExtra(CONSTANTS.LOGIN_SPLASH_FLAG, true))
        }
    }

    companion object {
        private const val SPLASH_TIME_OUT = 500
        lateinit var provinces: MutableList<String>
        lateinit var districtsMap: MutableMap<String, Pair<String, Districts>>
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }


    //Only use for calling coroutine in java
    abstract class Continuation<in T> : kotlin.coroutines.Continuation<T> {
        abstract fun resume(value: T)
        abstract fun resumeWithException(exception: Throwable)
        override fun resumeWith(result: Result<T>) = result.fold(::resume, ::resumeWithException)
    }
}