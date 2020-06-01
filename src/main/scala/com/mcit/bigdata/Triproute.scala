package com.mcit.bigdata

import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source

object Triproute extends App {

  def getCalendarData: List[Calendar] =
  {
    val fSource = Source.fromFile("K://MCIT/big data/study/2 scala/assinment/gtfs_stm/calendar.txt")
    val calendarList: List[Calendar] = fSource
      .getLines()
      .toList
      .tail
      .map(_.split(",", -1))
      .map(c => Calendar(c(0), c(1).toInt, c(2).toInt, c(3).toInt, c(4).toInt, c(5).toInt, c(6).toInt,c(7).toInt,c(8),c(9)))
    fSource.close()
    calendarList
  }
  def getRouteData: List[Route] =
  {
    val fSource = Source.fromFile("K://MCIT/big data/study/2 scala/assinment/gtfs_stm/routes.txt")
    val routeList: List[Route] = fSource
      .getLines()
      .toList
      .tail
      .map(_.split(",", -1))
      .map(r => Route(r(0).toInt, r(1), r(2), r(3), r(4).toInt, r(5),r(6),r(7)))
    fSource.close()
    routeList
  }
  def getTripData: List[Trip] =
  {
    val fSource = Source.fromFile("K:/MCIT/big data/study/2 scala/assinment/gtfs_stm/trips.txt")
    val tripList:List[Trip]=fSource
      .getLines()
      .toList
      .tail
      .map(_.split(",",-1))
      .map(t => Trip(t(0).toInt, t(1), t(2), t(3), t(4).toInt, t(5).toInt, t(6).toInt,
        if (t(7).isEmpty) None else Some(t(7)),
        if (t(8).isEmpty) None else Some(t(8))))
    fSource.close()
    tripList
  }
  val routeMap: RouteLookup = new RouteLookup(getRouteData)
  val routeTrips: List[RouteTrip] =
    getTripData.map(line => RouteTrip(line, routeMap.lookup(line.routeId)))
      .filter(_.route != null)

  val enrichedTrips: List[JoinOutput] =
    new GenericNestedLoopJoin[RouteTrip, Calendar]((i, j) => i.trip.serviceId == j.serviceId)
      .join(routeTrips, getCalendarData)

  val outDataLines: List[String] =
    enrichedTrips
      .map(n =>
        EnrichedTrip.formatOutput(n.left.asInstanceOf[RouteTrip].trip,
          n.left.asInstanceOf[RouteTrip].route,
          n.right.asInstanceOf[Calendar]))

  enrichedTrips.foreach(println)

  val outFile = new File("K:/MCIT/big data/study/2 scala/assinment/output.csv")
  val bw = new BufferedWriter(new FileWriter(outFile))

  val l1 = List("route_id", "service_id", "trip_id", "trip_headsign",
    "direction_id", "shape_id", "wheelchair_accessible",
    "note_fr", "note_en", "route_long_name")
  for (line <- l1) {
    bw.write(line + ",")
  }

  for (line <- outDataLines) {
    bw.newLine()
    bw.write(line)
  }

  bw.close()
}
