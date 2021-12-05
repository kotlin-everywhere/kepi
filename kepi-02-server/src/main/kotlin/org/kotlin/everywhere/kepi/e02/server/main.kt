package org.kotlin.everywhere.kepi.e02.server

import kotlinx.coroutines.runBlocking
import org.kotlin.everywhere.kepi.e02.common.Def
import org.kotlin.everywhere.net.HttpServerEngine
import org.kotlin.everywhere.net.createServer
import org.kotlin.everywhere.net.invoke

val def = Def()

fun Def.init() {
    detect {
        println(it)
    }
}

fun main() = runBlocking {
    def.init()

    val server = createServer(def, HttpServerEngine())
    server.launch(5000)
}