package twentytwentyone

import libadvent.util.allLowerCase
import libadvent.util.allUpperCase
import libadvent.util.itemsPairedWith
import libadvent.util.toPair
import res.asType
import res.load
import libadvent.part1
import libadvent.part2


class IllegalCaveException(input: String) : RuntimeException("$input is not a valid cave node")

sealed class CaveNode(open val label: String) {
    object Start : CaveNode("start")
    object End : CaveNode("end")
    data class Big(override val label: String) : CaveNode(label)
    data class Small(override val label: String) : CaveNode(label)
}

fun String.toCaveNodeOrNull(): CaveNode? {
    return when {
        this == "start" -> CaveNode.Start
        this == "end" -> CaveNode.End
        this.allUpperCase() -> CaveNode.Big(this)
        this.allLowerCase() -> CaveNode.Small(this)
        else -> null
    }
}

fun String.toCaveNode() = toCaveNodeOrNull() ?: throw IllegalCaveException(this)

fun String.toCaveNodePair(): Pair<CaveNode, CaveNode> {
    return split("-", limit = 2)
        .map { it.toCaveNode() }
        .toPair()
}

class Cave(private val connections: List<Pair<CaveNode, CaveNode>>) {
    private val nodes = connections.flatMap { sequenceOf(it.first, it.second) }.toSet()
    private val pathways = nodes.associateWith { connections.itemsPairedWith(it) }

    private fun findAllPathsRecursively(
        currentNode: CaveNode,
        pathSoFar: List<CaveNode>,
        allowRevisitingSmallNodes: Boolean,
    ): Collection<List<CaveNode>> {
        val path = pathSoFar.toMutableList()
        path.add(currentNode)

        return if (currentNode == CaveNode.End) {
            listOf(path.toList())
        } else {
            // Get the nodes that connect to the current one, otherwise return an empty list
            val connectedNodes = pathways[currentNode] ?: return emptyList()

            val availableNextNodes = if (allowRevisitingSmallNodes) {
                // We can go anywhere, other than back to start.
                connectedNodes.filter { it != CaveNode.Start }
            } else {
                // Our next steps must either be a Big node, or one we haven't visited yet
                connectedNodes.filter { it is CaveNode.Big || it !in pathSoFar }
            }
            if (availableNextNodes.isEmpty()) {
                // There's nowhere to go
                emptyList()
            } else {
                // Recursively check each next available node in the Cave
                availableNextNodes.flatMap { next ->
                    val isSmallRevisiting = next is CaveNode.Small && next in path
                    val shouldAllowRevisiting = allowRevisitingSmallNodes && !isSmallRevisiting
                    findAllPathsRecursively(next, path, shouldAllowRevisiting)
                }
            }
        }
    }

    fun findAllPaths(allowRevisitingOneSmallNode: Boolean = false): Collection<List<CaveNode>> {
        return findAllPathsRecursively(CaveNode.Start, listOf(), allowRevisitingOneSmallNode)
    }
}

fun day12Part1(input: List<Pair<CaveNode, CaveNode>>): Int {
    val cave = Cave(input)
    return cave.findAllPaths().size
}

fun day12Part2(input: List<Pair<CaveNode, CaveNode>>): Int {
    val cave = Cave(input)
    return cave.findAllPaths(allowRevisitingOneSmallNode = true).size
}

fun main() {
    val input = load("2021/day12.txt").asType { it.toCaveNodePair() }
    part1 { day12Part1(input) }
    part2 { day12Part2(input) }
}