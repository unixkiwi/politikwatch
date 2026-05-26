package de.unixkiwi.politikwatch.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.unixkiwi.politikwatch.data.awpolls.repo.AWPollRepository
import de.unixkiwi.politikwatch.data.surveys.repo.DawumSurveyRepository
import de.unixkiwi.politikwatch.domain.models.AWPoll
import de.unixkiwi.politikwatch.domain.models.Survey
import de.unixkiwi.politikwatch.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pollsRepo: AWPollRepository,
    private val surveyRepo: DawumSurveyRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)
    val state = _state.asStateFlow()

    init {
        getLatestSurveys()
        getPolls()
    }

    private fun getLatestSurveys() {
        viewModelScope.launch(Dispatchers.Default) {
            surveyRepo.getSurveys().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { surveys ->
                            _state.update { currentState ->
                                when (currentState) {
                                    is HomeState.Success -> currentState.copy(
                                        surveys = surveys,
                                        surveysLoading = false
                                    )

                                    else -> HomeState.Success(
                                        surveys = surveys,
                                        surveysLoading = false
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        result.let { (errorTitle, errorDetails) ->
                            _state.update { HomeState.Error(errorTitle, errorDetails ?: "") }
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { currentState ->
                            when (currentState) {
                                is HomeState.Success -> currentState.copy(surveysLoading = true)

                                else -> HomeState.Success(surveysLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPolls(/*fetchFromRemote: Boolean = false*/) {
        viewModelScope.launch {
            pollsRepo.getLatestPolls(15).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { polls ->
                            _state.update { currentState ->
                                when (currentState) {
                                    is HomeState.Success -> currentState.copy(
                                        polls = polls,
                                        pollsLoading = false
                                    )

                                    else -> HomeState.Success(
                                        polls = polls,
                                        pollsLoading = false
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        result.let { (errorTitle, errorDetails) ->
                            _state.update { HomeState.Error(errorTitle, errorDetails ?: "") }
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { currentState ->
                            when (currentState) {
                                is HomeState.Success -> currentState.copy(pollsLoading = true)

                                else -> HomeState.Success(pollsLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface HomeState {
    data class Success(
        val polls: List<AWPoll> = emptyList(),
        val surveys: List<Survey> = emptyList(),
        val pollsLoading: Boolean = false,
        val surveysLoading: Boolean = false
    ) : HomeState

    data class Error(val title: String, val desc: String) : HomeState
    data object Loading : HomeState
}