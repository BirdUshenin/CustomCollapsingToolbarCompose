package com.ilyaushenin.customcollapsingtoolbarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.ilyaushenin.customcollapsingtoolbarcompose.ui.theme.CustomCollapsingToolbarComposeTheme
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomCollapsingToolbarComposeTheme {
                CollapsingToolbar()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbar(){
    var screenSize by remember { mutableStateOf(ScreenSize(0.dp, 0.dp)) }

    val scrollState = rememberScrollState()
    val scrollOffset = min(1f, 1f * scrollState.value / 150)

    val imageHeight = 150.dp
    val imageHeightToPx = with(LocalDensity.current) { imageHeight.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White),
    ) {
        Image(
            painter = painterResource(id = R.drawable.insights),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { screenSize = ScreenSize(it.width.dp, it.height.dp) }
                .height(imageHeight)
                .graphicsLayer {
                    alpha = lerp(1f, 0.5f, scrollOffset)
                    translationY = -scrollState.value.toFloat() / 2f
                }
        )
        Image(
            painter = painterResource(id = R.drawable.insights),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(imageHeight))
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column {
                    repeat(40){
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.White)
                        )
                    }
                }
            }
        }
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(
                    onClick = {
                       /* TODO onClickBack */
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .statusBarsPadding()
                .background(
                    color = if (scrollState.value >= imageHeightToPx)
                        MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
        )
    }
}

private data class ScreenSize(val widthDp: Dp, val heightDp: Dp)