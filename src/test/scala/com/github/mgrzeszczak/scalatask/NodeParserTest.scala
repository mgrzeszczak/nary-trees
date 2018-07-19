package com.github.mgrzeszczak.scalatask

import org.scalatest.{FlatSpec, Matchers}

class NodeParserTest extends FlatSpec with Matchers {

  val nodeParser = new NodeParser()
  behavior of "node parser"

  it should "parse single node tree" in {
    val entries = List(Entry(1, "test", 0))
    nodeParser.parseNode(entries) shouldEqual Some(Node(1, "test", Nil))
  }

  it should "return None for invalid data" in {
    val entries = List(Entry(1, "test", 0), Entry(1, "test", 0))
    nodeParser.parseNode(entries) shouldBe None
  }

  it should "parse a complex nary tree" in {
    val entries = List(
      Entry(10, "test10", 3),
      Entry(9, "test9", 3),
      Entry(8, "test8", 3),
      Entry(7, "test7", 2),
      Entry(6, "test6", 2),
      Entry(5, "test5", 1),
      Entry(4, "test4", 1),
      Entry(3, "test3", 2),
      Entry(2, "test2", 1),
      Entry(1, "test1", 0)
    )

    nodeParser.parseNode(entries) shouldEqual Some(
      Node(1, "test1", List(
        Node(2, "test2", List(Node(3, "test3", Nil))),
        Node(4, "test4", Nil),
        Node(5, "test5", List(
          Node(6, "test6", Nil),
          Node(7, "test7", List(
            Node(8, "test8", Nil),
            Node(9, "test9", Nil),
            Node(10, "test10", Nil)
          ))
        ))
      ))
    )
  }


}
