package com.onurcemkarakoc.core.data.model

data class Coordinates(val posX: Double, val posY: Double){
    override fun toString(): String {
        return "($posX, $posY)"
    }
}
