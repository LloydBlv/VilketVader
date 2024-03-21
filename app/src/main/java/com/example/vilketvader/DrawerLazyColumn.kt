package com.example.vilketvader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun DrawerLazyColumn(
    items: List<UiLocation>,
    selectedItem: MutableState<UiLocation>,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        contentPadding = PaddingValues(8.dp)
    ) {
        drawerTopAppBar()
        drawerItems(items, selectedItem, scope, drawerState)
    }
}

private fun LazyListScope.drawerItems(
    items: List<UiLocation>,
    selectedItem: MutableState<UiLocation>,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    items(items) { item ->
        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                selectedContainerColor = Color.Transparent,
                unselectedTextColor = Color.White.copy(alpha = 0.8f),
                selectedTextColor = Color.White,
            ),
            icon = item.takeIf { it == selectedItem.value }
                ?.let {
                    {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null
                        )
                    }
                },
            label = {
                DrawerLocationItem(item, selectedItem)
            },
            selected = item == selectedItem.value,
            onClick = {
                scope.launch { drawerState.close() }
                selectedItem.value = item
            },
            modifier = Modifier.padding(horizontal = 4.dp)
        )

    }
}

@Composable
private fun DrawerLocationItem(
    item: UiLocation,
    selectedItem: MutableState<UiLocation>
) {
    Text(
        item.cityName,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = if (item == selectedItem.value) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    )
}

private fun LazyListScope.drawerTopAppBar() {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    tint = Color.White.copy(alpha = 0.6f),
                    contentDescription = "Settings"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = Color.White.copy(alpha = 0.6f),
                    contentDescription = "Settings"
                )
            }
        }
    }
}