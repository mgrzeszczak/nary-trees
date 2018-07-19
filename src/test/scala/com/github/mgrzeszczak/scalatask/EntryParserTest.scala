package com.github.mgrzeszczak.scalatask

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._

class EntryParserTest extends FlatSpec with Matchers {

  val parser = new EntryParser()

  behavior of "entry parser"

  it should "parse valid entries" in {
    forAll(Table(
      ("a", "b", "c", "id", "expected"),
      ("a", "", "", "1", Entry(1, "a", 0)),
      ("", "b", "", "2", Entry(2, "b", 1)),
      ("", "", "c", "3", Entry(3, "c", 2))
    )) { (a, b, c, id, expected) =>
      parser.fromRow(Array(a,b,c,id)) shouldEqual expected
    }
  }


}
