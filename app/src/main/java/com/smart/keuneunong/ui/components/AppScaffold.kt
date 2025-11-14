package com.smart.keuneunong.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * A reusable scaffold with header and navigation drawer for the app.
 * @param drawerContent Composable for the drawer content
 * @param topBar Composable for the header/top bar
 * @param bottomBar Optional composable for the bottom bar
 * @param content Main screen content that receives PaddingValues and CoroutineScope to open drawer
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    drawerContent: @Composable () -> Unit,
    topBar: @Composable (() -> Unit) -> Unit = { },
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues, () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = drawerContent
    ) {
        Scaffold(
            modifier = modifier,
            topBar = { topBar(openDrawer) },
            bottomBar = bottomBar,
            content = { paddingValues ->
                content(paddingValues, openDrawer)
            }
        )
    }
}
