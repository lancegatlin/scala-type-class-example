package org.lancegatlin.example.compare

import java.time.{LocalDate, LocalDateTime}

/**
  * An example type-class for Comparing a type to an instance of the same type
  * that handles null values for all types
  * Note: scala.math.Ordering provides this behavior already (without null checking)
  * @tparam A type-class type
  */
trait Compare[A] {
  final def compare(x1: A, y1: A) : Int = {
    // Provide semantics for comparing null values
    if (x1 == null && y1 == null) 0
    if (x1 == null && y1 != null) -1
    else if (x1 != null && y1 == null) 1
    else doCompare(x1,y1)
  }

  protected def doCompare(x1: A, y1: A) : Int
}

object Compare {
  // Note: Scala will bind all implicit instances below automatically if no other instance
  // is in scope for the type (See https://docs.scala-lang.org/tutorials/FAQ/finding-implicits.html)

  implicit object IntCompare extends Compare[Int] {
    override def doCompare(x1: Int, y1: Int): Int =
      x1.compareTo(x1)
  }
  implicit object StringCompare extends Compare[String] {
    override def doCompare(x1: String, y1: String): Int =
      x1.compareTo(x1)
  }
  implicit object BigDecimalCompare extends Compare[BigDecimal] {
    override protected def doCompare(x1: BigDecimal, y1: BigDecimal): Int = {
      x1.compare(y1)
    }
  }
  implicit object LocalDateCompare extends Compare[LocalDate] {
    override protected def doCompare(x1: LocalDate, y1: LocalDate): Int = {
      x1.compareTo(y1)
    }
  }
  implicit object LocalDateTimeCompare extends Compare[LocalDateTime] {
    override protected def doCompare(x1: LocalDateTime, y1: LocalDateTime): Int = {
      x1.compareTo(y1)
    }
  }
}

