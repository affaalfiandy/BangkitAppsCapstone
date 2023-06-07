package com.example.mommymate2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mommymate2.repository.RecordRepository

class ViewModelFactory(val recorderRepository: RecordRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RecordViewModel::class.java) -> {
                RecordViewModel(recorderRepository) as T
            }

            else -> throw IllegalArgumentException("Viewmodel tidak ada " + modelClass.name)
        }
    }
}