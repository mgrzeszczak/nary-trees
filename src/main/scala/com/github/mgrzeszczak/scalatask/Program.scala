package com.github.mgrzeszczak.scalatask

import com.github.mgrzeszczak.scalatask.EntryStreamHelper._
import io.circe.generic.auto._
import io.circe.syntax._

object Program {

  def main(args: Array[String]): Unit = {
    val entryParser = new EntryParser()
    println(new CsvReader(args(0))
      .rows()
      .map(entryParser.fromRow)
      .nodeStream(new NodeParser)
      .toList.asJson)
  }

}
