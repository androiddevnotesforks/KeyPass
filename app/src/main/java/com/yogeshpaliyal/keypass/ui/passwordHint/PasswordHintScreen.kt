package com.yogeshpaliyal.keypass.ui.passwordHint

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogeshpaliyal.common.utils.setPasswordHint
import com.yogeshpaliyal.keypass.R
import com.yogeshpaliyal.keypass.ui.commonComponents.DefaultBottomAppBar
import com.yogeshpaliyal.keypass.ui.commonComponents.KeyPassInputField
import com.yogeshpaliyal.keypass.ui.nav.LocalUserSettings
import com.yogeshpaliyal.keypass.ui.redux.actions.Action
import com.yogeshpaliyal.keypass.ui.redux.actions.BatchActions
import com.yogeshpaliyal.keypass.ui.redux.actions.GoBackAction
import com.yogeshpaliyal.keypass.ui.redux.actions.ToastAction
import kotlinx.coroutines.launch
import org.reduxkotlin.compose.rememberTypedDispatcher

@Composable
fun PasswordHintScreen() {
    val dispatchAction = rememberTypedDispatcher<Action>()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userSettings = LocalUserSettings.current
    val (passwordHint, setPasswordHint) = rememberSaveable { mutableStateOf(userSettings.passwordHint) }

    Scaffold(bottomBar = { DefaultBottomAppBar() }) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(1f)) {
                KeyPassInputField(
                    placeholder = R.string.enter_password_hint,
                    value = passwordHint,
                    setValue = setPasswordHint
                )
                Button(modifier = Modifier.fillMaxWidth(1f), onClick = {
                    coroutineScope.launch {
                        context.setPasswordHint(passwordHint)
                        dispatchAction(BatchActions(GoBackAction, ToastAction(R.string.hint_change_success)))
                    }
                }) {
                    Text(text = stringResource(id = R.string.change_app_hint))
                }

                OutlinedButton(modifier = Modifier.fillMaxWidth(1f), onClick = {
                    coroutineScope.launch {
                        context.setPasswordHint(null)
                        dispatchAction(BatchActions(GoBackAction, ToastAction(R.string.hint_removed_success)))
                    }
                }) {
                    Text(text = stringResource(id = R.string.remove_app_hint))
                }
            }
        }
    }
}

@Preview
@Composable
fun PPasswordHintScreen() {
    PasswordHintScreen()
}
