package com.github.mgrzeszczak.scalatask

import org.scalatest.{FlatSpec, Matchers}

class CsvReaderTest extends FlatSpec with Matchers {

  behavior of "csv reader"

  it should "return lines without header" in {
    new CsvReader(getClass.getResource("/test.csv").getPath).rows().length shouldEqual 3
  }

  it should "return stream of rows (string arrays)" in {
    val expected = List(Array("a","b","c"), Array("b","c","d"), Array("c","d","e"))
    new CsvReader(getClass.getResource("/test.csv").getPath).rows().toList should contain theSameElementsAs expected
  }

}
