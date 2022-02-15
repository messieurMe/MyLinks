package com.messieurme.mylinks.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.messieurme.mylinks.R
import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.custom.LinkFolderFolded

import android.content.Intent
import android.net.Uri
import com.messieurme.mylinks.BaseApplication.Companion.instance


@Composable
fun LinkItem(link: Link, toDelete: () -> Unit, toEdit: () -> Unit, changePosition: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp, 2.dp)
            .clickable {
                val openUrlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link.link)
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                instance.startActivity(openUrlIntent)
            },
        border = BorderStroke(2.dp, MaterialTheme.colors.primarySurface),
        elevation = 6.dp,
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ChangePosition()
            Icon(
                painterResource(id = R.drawable.ic_baseline_link_24),
                contentDescription = null,
                modifier = Modifier.padding(5.dp, 0.dp)
            )
            Text(
                text = link.name,
                maxLines = 1,
                color = Color.Blue,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
            )
            SettingsIcons(toDelete, toEdit)
        }
    }
}

@Composable
fun LinkFolderItem(
    linkff: LinkFolderFolded,
    toDelete: () -> Unit,
    toEdit: () -> Unit,
    changePosition: (Int) -> Unit,
    open: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(5.dp, 2.dp)
            .clickable { open(linkff.id) },
        border = BorderStroke(2.dp, MaterialTheme.colors.primarySurface),
        elevation = 6.dp,
        shape = RoundedCornerShape(7.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ChangePosition()
            Icon(
                painterResource(id = R.drawable.ic_baseline_folder_24),
                contentDescription = null,
                modifier = Modifier.padding(5.dp, 0.dp)
            )
            Text(
                text = linkff.name,
                maxLines = 1,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            SettingsIcons(toDelete, toEdit)
        }
    }
}

@Composable
fun SettingsIcons(toDelete: () -> Unit, toEdit: () -> Unit) {
    Row {
        IconButton(onClick = toEdit) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
        IconButton(onClick = toDelete) {
            Icon(Icons.Default.Close, contentDescription = null)
        }
    }
}

@Composable
fun ChangePosition() {
    Column {
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier.absoluteOffset(0.dp, (8).dp)

        ) {
            Icon(
                Icons.Filled.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.clip(MaterialTheme.shapes.small)
            )
        }
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier.absoluteOffset(0.dp, (-8).dp)
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.clip(MaterialTheme.shapes.small)
            )
        }
    }
}