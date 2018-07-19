package com.github.mgrzeszczak.scalatask

case class Node(id: Int, name: String, nodes: List[Node])

class NodeParser {

  def parseNode(entries: List[Entry]): Option[Node] = {

    def go(mem: Map[Int, List[Node]], rest: List[Entry], depth: Int): List[Node] = {
      rest match {
        case Nil => mem(depth)
        case h :: tail =>
          if (depth == h.depth) {
            go(mem - depth + (depth -> (Node(h.id, h.name, Nil) :: mem(depth))), tail, depth)
          } else if (h.depth > depth) {
            go(mem + (h.depth -> (Node(h.id, h.name, Nil) :: Nil)), tail, h.depth)
          } else {
            val nodes = Node(h.id, h.name, mem(h.depth + 1)) :: mem(h.depth)
            val newMem = mem - (h.depth+1) - h.depth + (h.depth -> nodes)
            go(newMem, tail, h.depth)
          }
      }
    }

    entries match {
      case Nil => None
      case h :: tail => Some(go(Map((h.depth, Node(h.id, h.name, Nil) :: Nil)).withDefaultValue(Nil), tail, h.depth))
        .filter(_.size == 1)
        .map(_.head)
    }
  }

}
