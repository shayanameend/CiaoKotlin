package com.zedsols.ciao_kotlin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ComposeTest {

  @Test
  fun simpleCheck() = runComposeUiTest {
    setContent {
      var txt by remember { mutableStateOf("Go") }

      Column {
        Text(
          text = txt,
          modifier = Modifier.testTag("t_text")
        )

        Button(
          onClick = { txt += "." },
          modifier = Modifier.testTag("t_button")
        ) {
          Text("click me")
        }
      }
    }

    onNodeWithTag("t_button").apply {
      repeat(3) { performClick() }
    }

    onNodeWithTag("t_text").assertTextEquals("Go...")
  }
}
