package com.mcit.bigdata

object EnrichedTrip {
  def formatOutput(trip: Trip, route: Route, calendar: Calendar): String = {
    trip.tripId + "," +
      trip.serviceId + "," +
      trip.tripId+ "," +
      trip.tripHeadSign + "," +
      trip.directionId + "," +
      trip.shapeId + "," +
      trip.wheelChairAccessible + "," +
      trip.noteFr.getOrElse("") + "," +
      trip.noteEn.getOrElse("") + "," +
      route.routeLongName

  }


}
