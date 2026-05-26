package de.unixkiwi.politikwatch.presentation.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.unixkiwi.politikwatch.presentation.core.components.CustomPageIndicator
import de.unixkiwi.politikwatch.presentation.home.viewmodel.HomeState
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeSuccessComponent(state: HomeState.Success, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                "Recent Polls",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            if (state.polls.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(CardDefaults.shape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.pollsLoading) {
                            ContainedLoadingIndicator()
                        } else {
                            Text(
                                "¯\\_(ツ)_/¯",
                                style = MaterialTheme.typography.headlineMediumEmphasized,
                            )
                            Text(
                                "No polls available",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            } else {
                val pagerState = rememberPagerState(pageCount = { state.polls.size })

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CardDefaults.shape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clip(CardDefaults.shape),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            pageSpacing = 16.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)

                        ) { page ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    state.polls[page].label,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 22.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    AnnotatedString.fromHtml(state.polls[page].description),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Light
                                    ),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        state.polls[page].date.format(
                                            DateTimeFormatter.ofPattern(
                                                "dd.MM.yyyy"
                                            )
                                        ),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            //fontWeight = FontWeight.Light
                                        ),
                                    )
                                    Text(
                                        if (state.polls[page].accepted) "Accepted" else "Rejected",
                                        color = if (state.polls[page].accepted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }

                        CustomPageIndicator(
                            pageCount = state.polls.size,
                            currentPage = pagerState.currentPage,
                            modifier = Modifier.padding(bottom = 2.dp, top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}