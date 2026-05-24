package de.unixkiwi.politikwatch.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.unixkiwi.politikwatch.data.awpolls.repo.AWPollRepository
import de.unixkiwi.politikwatch.domain.models.AWPoll
import de.unixkiwi.politikwatch.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pollsRepo: AWPollRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)
    val state = _state.asStateFlow()


    init {
        getPolls()
    }

    private fun getPolls(/*fetchFromRemote: Boolean = false*/) {
        viewModelScope.launch {
            pollsRepo.getLatestPolls(15).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { polls ->
                            _state.update { HomeState.Success(polls) }
                        }
                    }

                    is Resource.Error -> {
                        result.let { (errorTitle, errorDetails) ->
                            _state.update { HomeState.Error(errorTitle, errorDetails ?: "") }
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { HomeState.Loading }
                    }
                }
            }
        }
    }
}

sealed interface HomeState {
    data class Success(val polls: List<AWPoll>) : HomeState

    data class Error(val title: String, val desc: String) : HomeState
    data object Loading : HomeState
}