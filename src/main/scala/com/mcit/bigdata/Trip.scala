package com.mcit.bigdata

case class Trip(
                 routeId: Int,
                 serviceId: String,
                 tripId: String,
                 tripHeadSign: String,
                 directionId: Int,
                 shapeId: Int,
                 wheelChairAccessible: Int,
                 noteFr: Option[String],
                 noteEn: Option[String])
object Trip {
  def toCsv(trip: Trip): String = {
    trip.routeId + "," +
      trip.serviceId + "," +
      trip.tripId + "," +
      trip.tripHeadSign + "," +
      trip.directionId + "," +
      trip.shapeId + "," +
      trip.wheelChairAccessible + "," +
      trip.noteFr.getOrElse("") + "," +
      trip.noteEn.getOrElse("")
  }

  def getTripHeadings: String = {
    "routeId" + "," +
      "serviceId" + "," +
      "tripId" + "," +
      "tripHeadSign" + ","+
      "directionId" + "," +
      "shapeId" + "," +
      "wheelChairAccessible" + "," +
      "noteFr" + "," +
      "noteEn"
  }
}
