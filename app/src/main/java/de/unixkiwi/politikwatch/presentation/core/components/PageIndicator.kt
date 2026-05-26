package de.unixkiwi.politikwatch.presentation.core.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomPageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    dotHeight: Dp = 8.dp,
    dotSpacing: Dp = 6.dp,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { index ->
            CustomPageDot(
                isActive = index == currentPage,
                dotHeight = dotHeight,
                activeColor = activeColor,
                inactiveColor = inactiveColor,
            )
        }
    }
}

@Composable
private fun CustomPageDot(
    isActive: Boolean,
    dotHeight: Dp,
    activeColor: Color,
    inactiveColor: Color,
) {
    val width: Dp by animateDpAsState(
        targetValue = if (isActive) 22.dp else dotHeight,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "dot_width",
    )

    val color = if (isActive) activeColor else inactiveColor

    Box(
        modifier = Modifier
            .size(width = width, height = dotHeight)
            .clip(RoundedCornerShape(percent = 50))
            .background(color),
    )
}