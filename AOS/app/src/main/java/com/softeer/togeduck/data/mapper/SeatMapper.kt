package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.home.seat.SeatsInfoResponse
import com.softeer.togeduck.data.model.home.seat.SeatStatusModel
import com.softeer.togeduck.data.model.home.seat.SeatsInfoModel
import com.softeer.togeduck.utils.addCommas

fun SeatsInfoResponse.toSeatInfoModel(): SeatsInfoModel {
    return SeatsInfoModel(
        seatsCntPerRow = row,
        totalRows = column,
        backSeatsCnt = backSeat,
        totalSeats = totalSeats,
        reservedSeats = reservedSeats,
        departurePlace = "${sourceCity}(${source})",
        arrivalPlace = "${destinationCity}(${destination})",
        price = price,
        formattedPrice = price.addCommas(),
        seats = seats.map { seat ->
            SeatStatusModel(
                id = seat.id,
                seatNo = seat.seatNo,
                status = seat.status
            )
        }
    )
}
