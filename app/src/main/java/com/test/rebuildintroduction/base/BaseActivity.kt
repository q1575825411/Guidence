package com.test.rebuildintroduction.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initData()
        initView()
        initEvent()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()


}