package com.example.attackontitanapp.presentation.screens.details

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.attackontitanapp.R
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.presentation.components.InfoBox
import com.example.attackontitanapp.presentation.components.OrderedList
import com.example.attackontitanapp.ui.theme.*
import com.example.attackontitanapp.util.Constants.BASE_URL
import com.example.attackontitanapp.util.Constants.DARK_VIBRANT
import com.example.attackontitanapp.util.Constants.MIN_BACKGROUND_IMAGE_HEIGHT
import com.example.attackontitanapp.util.Constants.ON_DARK_VIBRANT
import com.example.attackontitanapp.util.Constants.VIBRANT
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsContent(
    navController: NavHostController,
    selectedTitan: Titan?,
    colors: Map<String, String>
) {
    var vibrant by remember { mutableStateOf("#ffffff") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#ffffff") }

    LaunchedEffect(key1 = selectedTitan) {
        vibrant = colors[VIBRANT]!!
        darkVibrant = colors[DARK_VIBRANT]!!
        onDarkVibrant = colors[ON_DARK_VIBRANT]!!
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(parseColor(darkVibrant))
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = Expanded)
    )

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedTitan?.let {
                BottomSheetContent(
                    selectedTitan = it,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant)),
                    screenHeight = screenHeight,
                    screenWidth = screenWidth
                )
            }
        },
        content = {
            selectedTitan?.let { titan ->
                BackgroundContent(
                    titanImage = titan.image,
                    imageFraction = currentSheetFraction,
                    backgroundColor = Color(parseColor(darkVibrant)),
                    onCloseClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}

@Composable
fun BottomSheetContent(
    selectedTitan: Titan,
    screenHeight: Dp,
    screenWidth: Dp,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .heightIn(max = screenHeight * (1.0f - MIN_BACKGROUND_IMAGE_HEIGHT))
            .background(sheetBackgroundColor)
            .padding(top = LARGE_PADDING, start = LARGE_PADDING, end = LARGE_PADDING)
            .verticalScroll(state = scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedTitan.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_height),
                iconColor = infoBoxIconColor,
                bigText = "${selectedTitan.height} m",
                smallText = stringResource(R.string.height),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_titan),
                iconColor = infoBoxIconColor,
                bigText = selectedTitan.type,
                smallText = stringResource(R.string.type),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedTitan.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fractionPerList = 0.5f

            OrderedList(
                title = stringResource(R.string.inheritors),
                items = selectedTitan.inheritors,
                textColor = contentColor,
                maxWidth = screenWidth * fractionPerList
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedTitan.abilities,
                textColor = contentColor,
                maxWidth = screenWidth * fractionPerList
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.other_names),
                items = selectedTitan.otherNames,
                textColor = contentColor,
                maxWidth = screenWidth
            )
        }

    }
}

@ExperimentalCoilApi
@Composable
fun BackgroundContent(
    titanImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${titanImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.titan_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = SMALL_PADDING),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == Collapsed && targetValue == Collapsed -> 1f
            currentValue == Expanded && targetValue == Expanded -> 0f
            currentValue == Collapsed && targetValue == Expanded -> 1f - fraction
            currentValue == Expanded && targetValue == Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@Composable
@Preview
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedTitan = Titan(
            id = 1,
            name = "Lorem ipsum",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            rating = 4.5,
            height = 0,
            type = "Pure Titan",
            inheritors = listOf("Minato Minato Minato Minato Minato Minato", "Tatake", "Shigenki"),
            abilities = listOf(
                "Sasa geyo Sasa geyo Sasa geyo Sasa geyo",
                "Hardening",
                "Regeneration"
            ),
            otherNames = listOf("giraffe", "Ugly")
        ), screenHeight = (800).dp, screenWidth = (400).dp
    )
}
