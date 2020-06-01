package com.mcit.bigdata

  class TripRouteMapJoin[Left,Right](val joinCond: Left => String )(val joinCond1: Right => String ) extends Join[Left,Right, JoinOutput] {

    override def join(a: List[Left], b: List[Right]): List[JoinOutput] = {
      val l: Map[String, Right] = b.map(b => joinCond1(b) -> b).toMap
      a.filter(a => l.contains(joinCond(a))).map(a => JoinOutput(a, Some(l(joinCond(a)))))
    }
  }

