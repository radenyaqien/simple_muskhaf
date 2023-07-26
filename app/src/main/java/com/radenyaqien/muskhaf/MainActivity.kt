package com.radenyaqien.muskhaf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.radenyaqien.muskhaf.ui.theme.MuskhafTheme
import com.radenyaqien.muskhaf.ui.components.SearchTextField
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuskhafTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val scope = rememberCoroutineScope()
                    val pagerState = rememberPagerState()
                    var value by remember {
                        mutableStateOf("")
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val context = LocalContext.current
                        SearchTextField(
                            value = value,
                            onValueChange = {
                                value = it
                            },
                            onSearch = {
                                if (value.toInt() > 604) {
                                    Toast.makeText(
                                        context,
                                        "batas halaman sampai 604",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@SearchTextField
                                }
                                if (value.isNotBlank()) scope.launch {
                                    pagerState.animateScrollToPage(value.toInt().minus(1))
                                }

                            },
                            modifier = Modifier.fillMaxWidth(),
                            shouldShowHint = value.isBlank()
                        )

                        HorizontalPager(
                            pageCount = 604,
                            reverseLayout = true,
                            state = pagerState
                        ) { index ->
                            val pagerOffset =
                                pagerState.currentPage.minus(index) + pagerState.currentPageOffsetFraction
                            val imageSize by animateFloatAsState(
                                targetValue = if (pagerOffset != 0.0f) 0.75f else 1f,
                                animationSpec = tween(durationMillis = 300)
                            )

                            val link = when (val page = index + 1) {
                                in 1..9 -> "https://media.qurankemenag.net/khat2/QK_00$page.webp"
                                in 10..99 -> "https://media.qurankemenag.net/khat2/QK_0$page.webp"
                                in 100..604 -> "https://media.qurankemenag.net/khat2/QK_$page.webp"
                                else -> "https://media.qurankemenag.net/khat2/QK_001.webp"
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,

                                ) {
                                ShowContent(
                                    link = link,
                                    modifier = Modifier
                                        .graphicsLayer {
                                            scaleX = imageSize
                                            scaleY = imageSize
                                        }
                                )
                            }


                        }

                    }

                }
            }
        }
    }

}

@Composable
fun ShowContent(link: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(model = link)
    val state = painter.state

    if (state is AsyncImagePainter.State.Loading) {
        LoadingAnimation()
    }
    if (state is AsyncImagePainter.State.Error) {
        Text(text = "Error Loading Image")
    }

    Image(
        painter = painter,
        contentDescription = "quran",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )

}

@Composable
fun LoadingAnimation() {
    val animation = rememberInfiniteTransition()
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart,
        )
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                5.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MuskhafTheme {
        ShowContent(link = "")
    }
}