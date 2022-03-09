package dk.mailr.desktopApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.mailr.ordering.features.AddOrderRequest
import dk.mailr.ordering.features.GetOrderDTO
import dk.mailr.ordering.features.GetOrdersQueryResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.launch

@Composable
fun FrontPage(httpClient: HttpClient) {
    val coroutineScope = rememberCoroutineScope()
    val orders = remember { mutableStateListOf<GetOrderDTO>() }
    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                coroutineScope.launch {
                    val response: GetOrdersQueryResponseDTO = httpClient.get("http://localhost:8080/ordering/orders")
                    orders.clear()
                    orders.addAll(response.orders)
                }
            }
        ) {
            Text("Get orders")
        }

        val orderName = remember { mutableStateOf("") }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
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
                    httpClient.post("http://localhost:8080/ordering/add-order") {
                        body = AddOrderRequest(orderName.value)
                    }
                }
            }) {
                Text("Add order")
            }
        }

        orders.map {
            Text(it.name)
            Text(it.createdAt.toString())
        }
    }
}
