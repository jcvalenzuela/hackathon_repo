package com.example.truckwireframe

data class VehicleInfo(
    var vehicleHeight: Float = ConstVal.VEHICLE_HEIGHT,
    var vehicleWidth: Float = ConstVal.VEHICLE_WIDTH,
    var vehicleLength: Float = ConstVal.VEHICLE_LENGTH / 2,
    var senseWidth: Float = vehicleWidth + ConstVal.SENSE_DEFAULT,
    var vehicleImage: Int = R.drawable.truck_wireframe
)
