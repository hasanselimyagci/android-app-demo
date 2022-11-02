package com.example.sixt
import kotlinx.serialization.*

@Serializable
data class Car(
    val id               : String? = null,
    val modelIdentifier  : String? = null,
    val modelName        : String? = null,
    val name             : String? = null,
    val make             : String? = null,
    val group            : String? = null,
    val color            : String? = null,
    val series           : String? = null,
    val fuelType         : String? = null,
    val fuelLevel        : Double? = null,
    val transmission     : String? = null,
    val licensePlate     : String? = null,
    val latitude         : Double? = null,
    val longitude        : Double? = null,
    val innerCleanliness : String? = null,
    val carImageUrl      : String? = null
)
