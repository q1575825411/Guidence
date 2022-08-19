package com.test.rebuildintroduction.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView()
        initData()
        initEvent()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化事件监听
     */
    open fun initEvent() {}

    /**
     * string转资源id
     */
    open fun stringToId(string:String): Int {
        return resources.getIdentifier(string, "array", requireContext().packageName)
    }
}