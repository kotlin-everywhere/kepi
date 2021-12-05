package org.kotlin.everywhere.kepi.e01.cli.server

import kotlinx.coroutines.runBlocking
import org.kotlin.everywhere.kepi.e01.cli.common.Def
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