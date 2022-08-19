package com.test.rebuildintroduction.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


open class BaseViewHolder<T : ViewDataBinding>(var viewBinding: T) : RecyclerView.ViewHolder(viewBinding.root) {

}