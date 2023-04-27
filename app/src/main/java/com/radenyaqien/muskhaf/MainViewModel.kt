package com.radenyaqien.muskhaf

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()
    private val _value = MutableStateFlow("")
    val value = _value.asStateFlow()
    val text = _page.map { page ->
        when (page.toString().length) {
            1 -> "https://media.qurankemenag.net/khat2/QK_00$page.webp"
            2 -> "https://media.qurankemenag.net/khat2/QK_0$page.webp"
            3 -> "https://media.qurankemenag.net/khat2/QK_$page.webp"
            else -> "https://media.qurankemenag.net/khat2/QK_001.webp"
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        "https://media.qurankemenag.net/khat2/QK_001.webp"
    )

    fun onValueChange(value: String) {
        _value.update {
            value
        }
    }

    fun onSearch() {
        if (_value.value.isNotEmpty() && _value.value.isDigitsOnly()) {
            _page.update {
                _value.value.toInt()
            }
        }
    }

    fun onNextPage() {
        _page.update {
            if (it < 604) it.plus(1) else it
        }
    }

    fun onPreviousPage() {
        _page.update {
            if (it > 1) it.minus(1) else it
        }
    }
}