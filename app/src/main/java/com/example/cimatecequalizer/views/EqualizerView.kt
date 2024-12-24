package com.example.cimatecequalizer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.cimatecequalizer.models.Equalizer
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EqualizerView(
    navController: NavController,
    user: User,
    userViewModel: UserViewModel,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Equalizador",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                            )
                        }
                    )
                }
            )
        },
        content = { padding ->
            EqualizerContent(
                padding = padding,
                user = user,
                userViewModel = userViewModel,
            )
        }
    )
}

@Composable
internal fun EqualizerContent(
    padding: PaddingValues,
    user: User,
    userViewModel: UserViewModel,
) {
    val frequencies = user.equalizer.frequencies

    Column {
        Text(
            text = "Equalizador ${user.name}",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(padding)
        )
        GraphicEqualizer(
            equalizer = user.equalizer,
            updateFrequency = { column, frequency ->
                userViewModel.updateEqualizer(userId = user.id, column, frequency)
            }
        )
    }
}

@Composable
internal fun GraphicEqualizer(
    equalizer: Equalizer,
    updateFrequency: (column: Int, frequency: Float) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            // padding para nao atrapalhar botao do ios
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        content = {
            items(6) { index ->
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        SliderCustom(
                            index = index,
                            frequency = equalizer.frequencies[index],
                            thumbSize = 35,
                            updateFrequency = updateFrequency,
                            modifier = Modifier
                                .padding(horizontal = 1.dp)
                                .fillMaxHeight(fraction = .75f)
                                .width(45.dp)
                                .zIndex(-1f)
                        )
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SliderCustom(
    frequency: Float,
    index: Int,
    modifier: Modifier = Modifier,
    updateFrequency: (column: Int, frequency: Float) -> Unit,
    thumbBackground: Color = Color(color = 0xFF191919),
    thumbSize: Int = 30,
) {
    var sliderValue by remember(frequency) { mutableFloatStateOf(frequency) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Text(
                    text = "${sliderValue.toInt()}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "dB",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        )

        //slider
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1F)
                .background(Color.Transparent),
        ) {

            //ranhuras
            Grooves()

            //track externa
            ExternalTrack()

            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                valueRange = -12f..12f,
                onValueChangeFinished = {
                    updateFrequency(
                        index,
                        sliderValue,
                    )
                },
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(width = thumbSize.dp, height = (thumbSize * .9).dp)
                            .padding(5.dp)
                            .thumb(shape = RoundedCornerShape(3.dp))
                            .background(thumbBackground)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(3.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .background(Color.White)
                                .height(thumbSize.dp)
                                .width(2.dp)
                        )
                    }
                },
                track = {
                    Box(modifier = Modifier.track())
                },
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = 270f
                        transformOrigin = TransformOrigin(0f, 0f)
                    }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            Constraints(
                                minWidth = constraints.minHeight,
                                maxWidth = constraints.maxHeight,
                                minHeight = constraints.minWidth,
                                maxHeight = constraints.maxHeight,
                            )
                        )
                        layout(placeable.height, placeable.width) {
                            placeable.place(-placeable.width, 0)
                        }
                    },
            )
        }
    }
}

@Composable
private fun Grooves() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 14.5.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (x in 0..12) {
            if (x == 0 || x == 12 || x == 6) {
                Box(
                    modifier = Modifier
                        .background(Color(color = 0xFFE70000))
                        .fillMaxWidth(0.8f)
                        .height(1.5.dp)
                        .zIndex(-2F),
                )
            } else {
                Box(
                    modifier = Modifier
                        .background(Color(color = 0xFFE70000))
                        .fillMaxWidth(.6F)
                        .height(1.5.dp)
                        .zIndex(-2F)
                )
            }
        }
    }
}

private fun Modifier.track(
    height: Dp = 8.dp,
    shape: Shape = CircleShape
) = fillMaxWidth()
    .heightIn(min = height)
    .clip(shape)

private fun Modifier.thumb(
    size: Dp = 20.dp,
    shape: Shape = CircleShape
) = defaultMinSize(minWidth = size, minHeight = size).clip(shape)

@Composable
private fun ExternalTrack() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.3F)
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .zIndex(-1F),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(.3F)
                    .fillMaxHeight()
                    .track()
                    .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = CircleShape
                    )
                    .background(Color(0xFF181818))
                    .zIndex(-1F)
            )
        }
    }
}
