package org.kotlin.everywhere.kepi.e02.client

import kotlinx.coroutines.*
import org.kotlin.everywhere.kepi.e02.common.ClientMsg
import org.kotlin.everywhere.kepi.e02.common.Def
import org.kotlin.everywhere.kepi.e02.common.ServerMsg
import org.kotlin.everywhere.net.HttpClientEngine
import org.kotlin.everywhere.net.createClient
import org.kotlin.everywhere.net.invoke
import rpi.sensehat.api.SenseHat
import rpi.sensehat.api.dto.Color


fun main(array: Array<String>) = runBlocking {
    val urlPrefix = array.firstOrNull()
        ?: throw IllegalArgumentException("접속할 서버를 지정하여 주십시오. 보기) kepi-e02-client http://192.168.0.2:5000")

    val def = Def()

    val client = createClient(def, HttpClientEngine(urlPrefix))

    val senseHat = SenseHat()
    while (true) {
        senseHat.ledMatrix.showLetter("K", Color.RED, Color.of(0, 0, 0))

        try {
            client.kenet.pipe { s, r ->
                senseHat.ledMatrix.showMessage("kenet", 0.03f, Color.BLUE, Color.of(0, 0, 0))
                senseHat.ledMatrix.clear()
                launch {
                    while (isActive) {
                        val sensors = withContext(Dispatchers.IO) {
                            ClientMsg.Sensors(
                                temperature = senseHat.environmentalSensor.temperature,
                                humidity = senseHat.environmentalSensor.humidity,
                                pressure = senseHat.environmentalSensor.pressure
                            )
                        }
                        s.send(sensors)
                        delay(timeMillis = 1_000)
                    }
                }

                while (isActive) {
                    for (serverMsg in r) {
                        when (serverMsg) {
                            is ServerMsg.ShowMessage -> {
                                withContext(Dispatchers.IO) {
                                    senseHat.ledMatrix.showMessage(serverMsg.message)
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            delay(timeMillis = 1_000)
        }
    }
}