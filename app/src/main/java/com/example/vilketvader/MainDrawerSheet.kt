package com.example.vilketvader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun MainDrawerSheet(
    items: ImmutableList<UiLocation>,
    selectedItem: UiLocation,
    onDrawerItemClicked: (UiLocation) -> Unit
) {
    DismissibleDrawerSheet(
        drawerContainerColor = Color.Transparent,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White.copy(
                    0.1f
                )
            )
        ) {
            DrawerLazyColumn(
                items = items,
                selectedItem = selectedItem,
                onDrawerItemClicked = onDrawerItemClicked
            )
        }


    }
}

