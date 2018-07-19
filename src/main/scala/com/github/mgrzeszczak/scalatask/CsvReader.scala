package com.github.mgrzeszczak.scalatask

import scala.io.Source


class CsvReader(file: String) {

  def rows(): Stream[Array[String]] =
    Source.fromFile(file)
      .getLines()
      .toStream
      .drop(1)
      .map(line => line.split(','))

}
