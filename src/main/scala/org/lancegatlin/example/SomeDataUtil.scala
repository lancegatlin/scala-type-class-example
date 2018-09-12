package org.lancegatlin.example

import org.lancegatlin.example.compare._

object SomeDataUtil {
  implicit object SomeDataCompare extends Compare[SomeData] {
    override protected def doCompare(x1: SomeData, y1: SomeData): Int =
      x1.i cmp y1.i
  }
}
