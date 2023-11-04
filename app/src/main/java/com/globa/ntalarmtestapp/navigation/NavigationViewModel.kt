package com.globa.ntalarmtestapp.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(

): ViewModel() {
    private val _selectedItem = MutableStateFlow(1)
    val selectedItem = _selectedItem.asStateFlow()

    fun onSelectorItemClick(number: Int) {
        if (number in 1..3) {
            _selectedItem.value = number
        }
    }
}