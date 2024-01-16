package com.rac.nugg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel:ViewModel() {

    private val _videoLink =MutableStateFlow<String?>(null)
    val videoLink: MutableStateFlow<String?> = _videoLink

    fun setVideoLink(link:String){
        viewModelScope.launch {
            _videoLink.emit(link)
        }
    }
}