package com.app.war.common.ui.compositions


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.war.common.ui.resources.strings.StringResources

@Composable
fun ExitAlertDialog(
    cancelButtonClick: (isOpen: Boolean) -> Unit,
    okButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(
                text = stringResource(StringResources.closeGame),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = stringResource(StringResources.doYouReallyWantToExit), fontSize = 16.sp)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    okButtonClick()
                }) {
                Text(
                    stringResource(StringResources.yesLabel),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    cancelButtonClick(false)
                }) {
                Text(
                    stringResource(StringResources.noLabel),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
    )
}
