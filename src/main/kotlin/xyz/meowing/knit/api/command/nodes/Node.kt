package xyz.meowing.knit.api.command.nodes

/**
 * # Node
 *
 * Base class for all nodes for [Commodore][xyz.meowing.knit.api.command.Commodore].
 *
 * @see LiteralNode
 * @see Executable
 * @author Stivais
 */
abstract class Node {

    /**
     * This node's parent.
     *
     * This will always be a [literal node][LiteralNode], since [Executable] can never have children nodes.
     */
    var parent: LiteralNode? = null

    internal abstract fun build(parent: LiteralNode)
}