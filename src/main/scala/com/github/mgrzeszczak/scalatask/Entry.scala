package com.github.mgrzeszczak.scalatask

case class Entry(id: Int, name: String, depth: Int)

class EntryParser {

  def fromRow(row: Array[String]): Entry = row.splitAt(row.length - 1) match {
    case (a, b) =>
      val depth = a.indexWhere(s => !s.isEmpty)
      Entry(b(0).toInt, a(depth), depth)
  }

}

object EntryStreamHelper {
  implicit class EntryStream[+A <: Entry](stream: Stream[A]) {

    def nodeStream(parser: NodeParser): Stream[Node] = groupByTopLevelNodes(stream)
      .flatMap(parser.parseNode)

    private def groupByTopLevelNodes(entries: Stream[Entry]): Stream[List[Entry]] = {
      extractTopNode(entries) match {
        case (l, Stream.Empty) =>
          if (l.isEmpty) Stream.Empty
          else Stream(l)
        case (l, rest) => l #:: groupByTopLevelNodes(rest)
      }
    }

    private def extractTopNode(entries: Stream[Entry]): (List[Entry], Stream[Entry]) = {
      def go(left: List[Entry], right: Stream[Entry], depth: Int): (List[Entry], Stream[Entry]) = {
        right match {
          case Stream.Empty => (left, Stream.Empty)
          case h #:: tail =>
            if (h.depth == depth) (left, right)
            else go(h :: left, tail, depth)
        }
      }

      entries match {
        case Stream.Empty => (Nil, Stream.Empty)
        case h #:: tail => go(List(h), tail, h.depth)
      }
    }
  }
}