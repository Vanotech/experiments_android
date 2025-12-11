package com.vanotech.experiments.feature.media.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vanotech.experiments.data.media.Media
import com.vanotech.experiments.data.media.MediaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<ViewRoute>()
    private val mediaId = args.mediaId

    private val _media = MutableStateFlow<Media?>(null)
    val media: StateFlow<Media?> = _media

    init {
        viewModelScope.launch {
            _media.value = mediaRepo.get(mediaId)
        }
    }
}