package com.vortex.android.codapizza.model

import androidx.annotation.StringRes
import com.vortex.android.codapizza.R

enum class PizzaSize(
    @StringRes val pizzaSizeName: Int,
    val valueModifier: Float
) {
    Small(
        pizzaSizeName = R.string.size_small,
        valueModifier = 0.5f
    ),

    Medium(
        pizzaSizeName = R.string.size_medium,
        valueModifier = 1.0f
    ),

    Large(
        pizzaSizeName = R.string.size_large,
        valueModifier = 1.5f
    ),

    ExtraLarge(
        pizzaSizeName = R.string.size_extralarge,
        valueModifier = 2.0f
    )
}