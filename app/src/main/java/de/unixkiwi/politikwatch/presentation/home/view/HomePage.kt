package de.unixkiwi.politikwatch.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.Card
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExpandedDockedSearchBarWithGap
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarWithGapState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.unixkiwi.politikwatch.presentation.home.viewmodel.HomeState
import de.unixkiwi.politikwatch.presentation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomePage(state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun HomePage(
    state: HomeState,
    modifier: Modifier = Modifier
) {
    val searchBarState = rememberSearchBarWithGapState()
    val scope = rememberCoroutineScope()
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val appBarWithSearchColors = SearchBarDefaults.appBarWithSearchColors()
    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                textFieldState = rememberTextFieldState(),
                searchBarState = rememberSearchBarWithGapState(),
                colors = appBarWithSearchColors.searchBarColors.inputFieldColors,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {
                    Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search")
                },
            )
        }
    Scaffold(
        topBar = {
            AppBarWithSearch(
                scrollBehavior = scrollBehavior,
                state = searchBarState,
                colors = appBarWithSearchColors,
                inputField = inputField,
            )
            ExpandedDockedSearchBarWithGap(state = searchBarState, inputField = inputField) {}
        },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        when (state) {
            is HomeState.Success -> {
                Column(Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 6.dp)) {
                    Text("Recent Polls", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(6.dp))
                    HorizontalPager(
                        state = rememberPagerState { state.polls.count() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) { i ->
                        val poll = state.polls[i]
                        Card(
                            onClick = {}, modifier = Modifier
                                .height(200.dp)
                                .padding(6.dp)
                        ) {
                            Column(
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = poll.label,
                                    style = MaterialTheme.typography.headlineSmall,
                                    maxLines = 2
                                )

                                Text(
                                    text = AnnotatedString.fromHtml(poll.description),
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 4
                                )

                                Text(
                                    text = poll.date.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }

            is HomeState.Error -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        state.title,
                        style = MaterialTheme.typography.headlineMedium,
                        softWrap = true
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(state.desc, style = MaterialTheme.typography.bodySmall, softWrap = true)
                }
            }

            is HomeState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }
        }
    }
}