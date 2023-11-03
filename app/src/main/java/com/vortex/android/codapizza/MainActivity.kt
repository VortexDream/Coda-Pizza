package com.vortex.android.codapizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vortex.android.codapizza.model.Topping
import com.vortex.android.codapizza.model.ToppingPlacement
import com.vortex.android.codapizza.ui.PizzaBuilderScreen
import com.vortex.android.codapizza.ui.ToppingCell

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaBuilderScreen()
        }
    }
}