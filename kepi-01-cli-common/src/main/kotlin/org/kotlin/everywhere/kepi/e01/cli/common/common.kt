package org.kotlin.everywhere.kepi.e01.cli.common

import kotlinx.serialization.Serializable
import org.kotlin.everywhere.net.Kenet

class Def : Kenet() {
    val detect by c<Sensors, Unit>()
}

@Serializable
data class Sensors(
    val temperature: Float,
    val humidity: Float,
    val pressure: Float
)