package com.example.composelearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composelearning.data.TabIcons
import com.example.composelearning.ui.theme.ComposeLearningTheme


@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val iconList = listOf(
            TabIcons(
                "Home", Icons.Outlined.Home, Icons.Filled.Home
            ),
            TabIcons(
                "Browse", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart
            ),
            TabIcons(
                "Account", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle
            ),
        )

        setContent {
            ComposeLearningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    var selectedTabRowIndex by remember {
                        mutableIntStateOf(0)
                    }

                    val pagerState = rememberPagerState {
                        iconList.size
                    }

                    LaunchedEffect(selectedTabRowIndex) {
                        pagerState.animateScrollToPage(selectedTabRowIndex)
                    }

                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {

                        if (!pagerState.isScrollInProgress) {
                            selectedTabRowIndex = pagerState.currentPage

                        }
                    }


                    Column(modifier = Modifier.fillMaxSize()) {
//                        Spacer(modifier = Modifier.weight(1f))

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { index ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = iconList[index].title)
                            }
                        }

                        TabRow(selectedTabIndex = selectedTabRowIndex) {
                            iconList.forEachIndexed { index, tabIcons ->
                                (Tab(selected = index == selectedTabRowIndex, onClick = {
                                    selectedTabRowIndex = index
                                }, text = { Text(text = tabIcons.title) }, icon = {
                                    Icon(
                                        imageVector = if (index == selectedTabRowIndex) {
                                            tabIcons.selectedIcon
                                        } else tabIcons.unselectedIcon,

                                        contentDescription = tabIcons.title,

                                        )
                                }))
                            }
                        }
                    }
                }
            }
        }
    }
}



