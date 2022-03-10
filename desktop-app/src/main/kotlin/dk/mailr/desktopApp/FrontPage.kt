package dk.mailr.desktopApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.mailr.ordering.features.AddOrderRequest
import dk.mailr.ordering.features.GetOrderDTO
import dk.mailr.ordering.features.GetOrderQueryResponseDTO
import dk.mailr.ordering.features.GetOrdersQueryResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun FrontPage(httpClient: HttpClient) {
    val coroutineScope = rememberCoroutineScope()
    val orders = remember { mutableStateOf<List<GetOrderDTO>>(emptyList()) }
    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
        GetOrdersButton(coroutineScope, httpClient, orders)

        AddOrderComponent(coroutineScope, httpClient, orders)
        OrderList(coroutineScope, httpClient, orders)
    }
}

@Composable
private fun AddOrderComponent(
    coroutineScope: CoroutineScope,
    httpClient: HttpClient,
    orders: MutableState<List<GetOrderDTO>>,
) {
    val orderName = remember { mutableStateOf("") }
    Row {
        TextField(
            orderName.value,
            onValueChange = {
                orderName.value = it
            },
            label = {
                Text(text = "Add order")
            }
        )
        Button(onClick = {
            coroutineScope.launch {
                val orderId: UUID = httpClient.post("http://localhost:8080/ordering/add-order") {
                    body = AddOrderRequest(orderName.value)
                }
                val order: GetOrderQueryResponseDTO = httpClient.get("http://localhost:8080/ordering/orders/$orderId")
                orders.value += order.order
            }
        }) {
            Text("Add order")
        }
    }
}

@Composable
private fun OrderList(
    coroutineScope: CoroutineScope,
    httpClient: HttpClient,
    orders: MutableState<List<GetOrderDTO>>,
) {
    orders.value.map { order ->
        Row {
            Text(order.name)
            Text(order.createdAt.toString())
            Button(onClick = {
                coroutineScope.launch {
                    httpClient.post<HttpResponse>("http://localhost:8080/ordering/delete-order/${order.id}")
                    orders.value = orders.value.filter { it.id != order.id }
                }
            }) {
                Text("Delete")
            }
        }
    }
}

@Composable
private fun GetOrdersButton(
    coroutineScope: CoroutineScope,
    httpClient: HttpClient,
    orders: MutableState<List<GetOrderDTO>>,
) {
    Button(onClick = {
        coroutineScope.launch {
            val response: GetOrdersQueryResponseDTO = httpClient.get("http://localhost:8080/ordering/orders")
            orders.value = response.orders
        }
    }) {
        Text("Get orders")
    }
}
