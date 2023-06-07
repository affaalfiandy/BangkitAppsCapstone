package com.example.mommymate2

import androidx.lifecycle.ViewModel
import com.example.mommymate2.repository.RecordRepository

class RecordViewModel(val recordRepository: RecordRepository): ViewModel() {

    fun startRecording() = recordRepository.startRecording()

    fun stopRecording() = recordRepository.stopRecording()
}