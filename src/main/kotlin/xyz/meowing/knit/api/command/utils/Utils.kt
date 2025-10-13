package xyz.meowing.knit.api.command.utils

import xyz.meowing.knit.api.command.nodes.LiteralNode
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.tree.LiteralCommandNode

/**
 * Returns the latest [LiteralNode] from a string.
 *
 * @return The corresponding node
 * @author Stivais
 */
fun findCorrespondingNode(node: LiteralNode, name: String): LiteralNode? {
    for (child in node.children) {
        if (child !is LiteralNode) continue
        findCorrespondingNode(child, name)?.let { return it }
    }
    return if (node.name == name) node else null
}

/**
 * Returns the latest [LiteralNode] from a [parse result][ParseResults]
 *
 * @return The corresponding node
 * @author Stivais
 */
fun findCorrespondingNode(node: LiteralNode, results: ParseResults<Any?>): LiteralNode? {
    val last = results.context.nodes.last { it.node is LiteralCommandNode }.node.name
    return findCorrespondingNode(node, last)
}