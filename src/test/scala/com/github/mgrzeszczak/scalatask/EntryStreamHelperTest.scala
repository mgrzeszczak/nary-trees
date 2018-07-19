package com.github.mgrzeszczak.scalatask

import org.scalatest.{FlatSpec, Matchers}
import com.github.mgrzeszczak.scalatask.EntryStreamHelper._

class EntryStreamHelperTest extends FlatSpec with Matchers {

  behavior of "entry stream"

  it should "map to node stream" in {
    val stream = Stream(Entry(1, "name", 0))

    stream.nodeStream(new NodeParser)
      .toList shouldEqual List(Node(1, "name", Nil))
  }


}
