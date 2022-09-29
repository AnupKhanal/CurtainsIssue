package com.example.curtainsissue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.curtainsissue.ui.theme.WindowsIssueTheme
import curtains.Curtains
import curtains.OnRootViewsChangedListener
import curtains.phoneWindow
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowsIssueTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ComposableSpinner()
                }
            }
        }
        Curtains.onRootViewsChangedListeners += OnRootViewsChangedListener { view, added ->
            println("root $view ${if (added) "added" else "removed"}")
            println("window for view ${view.phoneWindow}")
        }
    }
}

@Composable
fun ComposableSpinner(modifier: Modifier = Modifier) {
    val companies = listOf("Apple", "Google", "Facebook", "Amazon")
    val (text, setText) = remember { mutableStateOf("Apple") }
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable { setExpanded(true) }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = {
            setExpanded(false)
        }) {
            companies.forEach { selectOption ->
                DropdownMenuItem(onClick = {
                    setText(selectOption)
                    setExpanded(false)
                }) {
                    Text(text = selectOption)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WindowsIssueTheme {
        ComposableSpinner()
    }
}