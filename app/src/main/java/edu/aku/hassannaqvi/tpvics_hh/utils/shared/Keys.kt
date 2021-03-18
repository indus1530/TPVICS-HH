package edu.aku.hassannaqvi.tpvics_hh.utils.shared

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}