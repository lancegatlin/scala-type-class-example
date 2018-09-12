package org.lancegatlin.example

import java.time.LocalDate

import org.lancegatlin.example.compare._

object CompareDemo {
  /*
  Example: Using type-class
   */
  val s1 = "test"
  val s2 = "test"
  s1 cmp s2 // calls extension method

  val s3 : String = null
  // note: this will not throw NPE since it is replaced by compiler with StringOrdering.compare(s3,s2)
  s3 cmp s2

  val bd1 = BigDecimal("0.0")
  val bd2 = BigDecimal("1.0")
  bd1 cmp bd2

  val ld1 = LocalDate.parse("1970-01-01")
  val ld2 = LocalDate.parse("1970-01-01")
  ld1 cmp ld2

  /*
  Example: Resolving type-class implicit directly
  */
  s1 cmp s2
  implicitly[Compare[String]].compare(s1,s2) // this is the same as above
  Compare.StringCompare.compare(s1,s2) // this is the same as both above

  /*
  Example: Shadowing an implementation in local scope
  */
  {
    implicit val stringInsensitiveCompare = new Compare[String] {
      override protected def doCompare(x1: String, y1: String): Int =
        x1.compareToIgnoreCase(y1)
    }
    s1 cmp s2 // Uses stringInsensitiveCompare
  }
  s1 cmp s2 // Uses Compare.StringCompare

  /*
  Example: compiler error when attempt to call Compare on types that don't have an implicit Compare[A] in scope
  */
  val sd1 = SomeData(1)
  val sd2 = SomeData(1)
// uncomment line below to get compiler error
//  sd1 cmp sd2

  /*
  Example: import an implementation to fix above error
  */
  {
    import SomeDataUtil._
    sd1 cmp sd2
  }

  /*
  Example: putting Compare implementation in class's companion object
  */
  case class MoreData(i: Int)
  object MoreData {
    implicit object MoreDataCompare extends Compare[MoreData] {
      override protected def doCompare(x1: MoreData, y1: MoreData): Int = {
        x1.i cmp y1.i
      }
    }
  }

  val md1 = MoreData(1)
  val md2 = MoreData(1)
  md1 cmp md2
 }
