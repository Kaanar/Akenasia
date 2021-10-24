package com.example.akenasia

data class PositionTable (var posId: Int, var posLat: Double, var posLong: Double, var partie: Int) {
        @JvmName("getposId1")
        fun getposId(): Int {
            return posId
        }

        @JvmName("getposLat1")
        fun getposLat(): Double {
            return posLat
        }

        @JvmName("getposLong1")
        fun getposLong(): Double {
            return posLong
        }

        @JvmName("getpartie")
        fun getpartie(): Int {
            return partie
        }
}
