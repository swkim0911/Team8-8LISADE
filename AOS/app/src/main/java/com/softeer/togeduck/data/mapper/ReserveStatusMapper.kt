package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusItemResponse
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.utils.addCommas


fun ReserveStatusItemResponse.toReserveStatusModel(): ReserveStatusModel {
    return ReserveStatusModel(
        id = id,
        festivalName = title,
        festivalImg = imagePath,
        departureTime = startedAt,
        departurePlace = stationName,
        festivalPlace = location,
        cost = price,
        formattedCost = price.addCommas(),
        recruitStatus = totalSeats == reservatedSeats,
        recruitPhrase = "${status}(${reservatedSeats}/${totalSeats})",
    )
}
