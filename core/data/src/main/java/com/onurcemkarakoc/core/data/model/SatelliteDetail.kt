package com.onurcemkarakoc.core.data.model

data class SatelliteDetail(
    val id: Int,
    val cost_per_launch: Long,
    val first_flight: String,
    val height: Int,
    val mass: Long
){
    override fun toString(): String {
        return "SatelliteDetail(id=$id, cost_per_launch=$cost_per_launch, first_flight='$first_flight', height=$height, mass=$mass)"
    }
}