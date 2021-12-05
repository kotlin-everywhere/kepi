package org.kotlin.everywhere.kepi.e02.server

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kotlin.everywhere.kepi.e02.common.ClientMsg
import org.kotlin.everywhere.kepi.e02.common.Def
import org.kotlin.everywhere.kepi.e02.common.ServerMsg
import org.kotlin.everywhere.net.HttpServerEngine
import org.kotlin.everywhere.net.createServer
import org.kotlin.everywhere.net.invoke
import java.net.Inet4Address
import java.net.NetworkInterface


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
}

object State {
    val addresses: List<Inet4Address> =
        NetworkInterface.getNetworkInterfaces().asSequence().flatMap { it.inetAddresses.asSequence() }
            .filterIsInstance<Inet4Address>().filter { !it.isLoopbackAddress }.toList()

    var connected by mutableStateOf(false)
    var fatalError by mutableStateOf<String?>(null)
    var sensors by mutableStateOf(ClientMsg.Sensors(0f, 0f, 0f))
    var message by mutableStateOf("")
    private var sendToClient: SendChannel<ServerMsg>? = null

    private val scope = MainScope()

    init {
        scope.launch { initServer() }
    }

    private suspend fun initServer() {
        try {
            val def = Def()
            def.init()
            val server = createServer(def, HttpServerEngine())
            server.launch(5000)
        } catch (e: Exception) {
            fatalError = e.toString()
            throw e
        }
    }

    private fun Def.init() {
        pipe { send, receive ->
            withContext(scope.coroutineContext) {
                sendToClient = send
                connected = true
                try {
                    for (clientMsg in receive) {
                        handle(clientMsg)
                    }
                } finally {
                    connected = false
                }
            }
        }
    }

    private fun handle(msg: ClientMsg) {
        when (msg) {
            is ClientMsg.Sensors -> {
                sensors = msg
            }
        }
    }

    fun onSendMessage() {
        val send = sendToClient ?: return
        send.trySend(ServerMsg.ShowMessage(message))
    }
}

@Composable
@Preview
fun App() {
    if (State.fatalError != null) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("! 오류")
            Text("")
            Text("${State.fatalError}")
        }
        return;
    }

    when (State.connected) {
        true -> PiApp()
        false -> Help()
    }

}

@Composable
@Preview
fun Help() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("클라이언트의 연결을 기다리고 있습니다.")
        Text("")
        Text("* 연결 가능한 주소")
        for (address in State.addresses) {
            Text("http://${address.hostAddress}:5000")
        }
    }
}

@Composable
@Preview
fun PiApp() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Head()
        MessageSender()
    }
}

@Composable
@Preview
fun Head() {
    Row {
        Text("온도 : ${State.sensors.temperature}")
        Spacer(Modifier.width(10.dp))
        Text("습도 : ${State.sensors.humidity}")
        Spacer(Modifier.width(10.dp))
        Text("압력 : ${State.sensors.pressure}")
    }
}

@Composable
@Preview
fun MessageSender() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(State.message, { State.message = it }, label = { Text("메시지") })
        Spacer(Modifier.width(10.dp))
        Button(onClick = { State.onSendMessage() }) {
            Text("적용")
        }
    }
}