package com.vortex.android.codapizza.ui

import android.icu.text.NumberFormat
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.vortex.android.codapizza.R
import com.vortex.android.codapizza.model.Pizza
import com.vortex.android.codapizza.model.PizzaSize
import com.vortex.android.codapizza.model.Topping
import com.vortex.android.codapizza.model.ToppingPlacement

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza(size = PizzaSize.Large)) }
    var sizeListExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SizeListExpandable(
            expanded = sizeListExpanded,
            onIconClick = { sizeListExpanded = true },
            onDismissRequest = { sizeListExpanded = false },
            onSetSize = { size -> pizza = pizza.copy(size = size) },
            modifier = Modifier
        )

        ToppingList(
            pizza = pizza,
            onEditPizza = { pizza = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )

        OrderButton(
            pizza = pizza,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun ToppingList(
    pizza: Pizza,
    //Лямбда, вызываемая при каждом редактирование переменной pizza
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }

    toppingBeingAdded?.let {topping ->
        ToppingPlacementDialog(
            topping = topping,
            onSetToppingPlacement = {placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            },
            onDismissRequest = {
                toppingBeingAdded = null
            }
        )
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(Topping.values()) {topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    /*val isOnPizza = pizza.toppings[topping] != null
                    onEditPizza(pizza.withTopping(
                        topping = topping,
                        placement = if (isOnPizza) {
                            null
                        } else {
                            ToppingPlacement.All
                        }
                    ))*/
                    toppingBeingAdded = topping
                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        val currencyFormatter = remember {
            NumberFormat.getCurrencyInstance()
        }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button, price)
                .toUpperCase(Locale.current)
        )
    }
}

@Composable
private fun SizeListExpandable(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSetSize: (size: PizzaSize) -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
        .wrapContentSize(Alignment.TopCenter)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onIconClick) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Localized description")
            }

            Text(
                text = "Choose pizza size"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            PizzaSize.values().forEach {size ->
                DropdownMenuItem(
                    onClick = {
                        onSetSize(size)
                        onDismissRequest()
                    }
                ) {
                    Text(stringResource(size.pizzaSizeName))
                }
            }
        }
    }
}