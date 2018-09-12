package org.lancegatlin.example

package object compare {
  implicit class CompareAnythingEx[A](val self: A) extends AnyVal {
    // note: not using "compare" as method name here since it will shadow
    // any method on that type called compare already
    def cmp(other: A)(implicit c:Compare[A]) : Int =
      c.compare(self,other)
  }
}
