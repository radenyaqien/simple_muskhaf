package com.radenyaqien.muskhaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.radenyaqien.muskhaf.ui.PageSelector
import com.radenyaqien.muskhaf.ui.SwippableComponent
import com.radenyaqien.muskhaf.ui.theme.MuskhafTheme
import com.radenyaqien.muskhaf.ui.theme.SearchTextField

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuskhafTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {


                    val value by viewModel.value.collectAsStateWithLifecycle()
                    val link by viewModel.text.collectAsStateWithLifecycle()
                    val page by viewModel.page.collectAsStateWithLifecycle()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchTextField(
                            value = value,
                            onValueChange = viewModel::onValueChange,
                            onSearch = viewModel::onSearch,
                            shouldShowHint = value.isBlank(),
                            onFocusChange = {
                                it.isFocused
                            }, modifier = Modifier.fillMaxWidth()
                        )
                        PageSelector(
                            page = page,
                            onPrevious = viewModel::onPreviousPage,
                            onNextPage = viewModel::onNextPage,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        SwippableComponent(
                            modifier = Modifier.fillMaxSize(),
                            onSwipeRight = viewModel::onNextPage,
                            onSwipeLeft = viewModel::onPreviousPage
                        ) {

                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(link)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "QUran",
                                placeholder = painterResource(id = R.drawable.loading_img),

                                )

                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting() {
    AsyncImage(
        model = "https://media.qurankemenag.net/khat2/QK_604.webp",
        contentDescription = "QUran",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MuskhafTheme {
        Greeting()
    }
}