package org.kotlin.everywhere.kepi.e02.server

import kotlinx.coroutines.runBlocking
import org.kotlin.everywhere.kepi.e02.common.ClientMsg
import org.kotlin.everywhere.kepi.e02.common.Def
import org.kotlin.everywhere.net.HttpServerEngine
import org.kotlin.everywhere.net.createServer
import org.kotlin.everywhere.net.invoke

val def = Def()

fun Def.init() {
    pipe { _, r ->
        for (clientMsg in r) {
            when (clientMsg) {
                is ClientMsg.Sensors -> println(clientMsg)
            }
        }
    }
}

fun main() = runBlocking {
    def.init()

    val server = createServer(def, HttpServerEngine())
    server.launch(5000)
}