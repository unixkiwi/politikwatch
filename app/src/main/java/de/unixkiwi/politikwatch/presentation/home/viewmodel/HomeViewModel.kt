package de.unixkiwi.politikwatch.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.unixkiwi.politikwatch.data.polls.repo.PollRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PollRepository
) : ViewModel() {
    val pollsPagingFlow = repository.getPagedPolls().cachedIn(viewModelScope)
}