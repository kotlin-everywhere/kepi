package org.kotlin.everywhere.kepi.e02.common

import kotlinx.serialization.Serializable
import org.kotlin.everywhere.net.Kenet

class Def : Kenet() {
    val pipe by p<ServerMsg, ClientMsg>()
}

@Serializable
sealed class ClientMsg {
    @Serializable
    data class Sensors(val temperature: Float, val humidity: Float, val pressure: Float) : ClientMsg()
}

@Serializable
sealed class ServerMsg {
    @Serializable
    class ShowMessage(val message: String) : ServerMsg()
}