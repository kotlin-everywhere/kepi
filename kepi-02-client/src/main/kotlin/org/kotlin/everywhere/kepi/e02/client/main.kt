package org.kotlin.everywhere.kepi.e02.client

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.kotlin.everywhere.kepi.e02.common.ClientMsg
import org.kotlin.everywhere.kepi.e02.common.Def
import org.kotlin.everywhere.net.HttpClientEngine
import org.kotlin.everywhere.net.createClient
import org.kotlin.everywhere.net.invoke
import rpi.sensehat.api.SenseHat


fun main(array: Array<String>) = runBlocking {
    val urlPrefix = array.firstOrNull()
        ?: throw IllegalArgumentException("접속할 서버를 지정하여 주십시오. 보기) kepi-01-cli-client http://192.168.0.2:5000")

    val def = Def()

    val client = createClient(def, HttpClientEngine(urlPrefix))

    val senseHat = SenseHat()

    client.kenet.pipe { s, _ ->
        while (isActive) {
            s.send(
                ClientMsg.Sensors(
                    temperature = senseHat.environmentalSensor.temperature,
                    humidity = senseHat.environmentalSensor.humidity,
                    pressure = senseHat.environmentalSensor.pressure
                )
            )
            delay(timeMillis = 1_000)
        }
    }
}