package org.jetbrains.kotlin.ui.formatter

import org.jetbrains.kotlin.idea.formatter.CommonAlignmentStrategy
import com.intellij.formatting.Indent
import com.intellij.formatting.Wrap
import com.intellij.psi.codeStyle.CodeStyleSettings
import org.jetbrains.kotlin.idea.formatter.KotlinSpacingBuilder
import com.intellij.lang.ASTNode
import com.intellij.formatting.Block
import com.intellij.formatting.Alignment
import com.intellij.formatting.Spacing
import org.jetbrains.kotlin.idea.formatter.KotlinCommonBlock
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.ASTBlock
import com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.lexer.KtTokens.ARROW
import org.jetbrains.kotlin.KtNodeTypes.WHEN_ENTRY

class KotlinBlock(
        node: ASTNode,
        private val myAlignmentStrategy: CommonAlignmentStrategy,
        private val myIndent: Indent?,
        wrap: Wrap?,
        private val mySettings: CodeStyleSettings,
        private val mySpacingBuilder: KotlinSpacingBuilder) : AbstractBlock(node, wrap, myAlignmentStrategy.getAlignment(node)) {
            
        private val kotlinDelegationBlock = object : KotlinCommonBlock(node, mySettings, mySpacingBuilder, myAlignmentStrategy) {
            
            override fun getNullAlignmentStrategy(): CommonAlignmentStrategy = NULL_ALIGNMENT_STRATEGY
    
            override fun createAlignmentStrategy(alignOption: Boolean, defaultAlignment: Alignment?): CommonAlignmentStrategy {
                return NodeAlignmentStrategy.fromTypes(KotlinAlignmentStrategy.wrap(createAlignment(alignOption, defaultAlignment)))
            }
    
            override fun getAlignmentForCaseBranch(shouldAlignInColumns: Boolean): CommonAlignmentStrategy {
                return if (shouldAlignInColumns) {
                    NodeAlignmentStrategy.fromTypes(
                            KotlinAlignmentStrategy.createAlignmentPerTypeStrategy(listOf(ARROW as IElementType), WHEN_ENTRY, true))
                }
                else {
                    NodeAlignmentStrategy.nullStrategy
                }
            }
    
            override fun getAlignment(): Alignment? = alignment
    
            override fun isIncompleteInSuper(): Boolean = isIncomplete
    
            override fun getSuperChildAttributes(newChildIndex: Int): ChildAttributes = super@KotlinBlock.getChildAttributes(newChildIndex)
    
            override fun getSubBlocks(): List<Block> = subBlocks

            override fun createBlock(node: ASTNode, alignmentStrategy: CommonAlignmentStrategy, indent: Indent?, wrap: Wrap?, settings: CodeStyleSettings, spacingBuilder: KotlinSpacingBuilder, overrideChildren: Sequence<ASTNode>?): ASTBlock {
                return KotlinBlock(
                        node,
                        alignmentStrategy,
                        indent,
                        wrap,
                        mySettings,
                        mySpacingBuilder)
            }
    
            override fun createSyntheticSpacingNodeBlock(node: ASTNode): ASTBlock {
                return object : AbstractBlock(node, null, null) {
                    override fun isLeaf(): Boolean = false
                    override fun getSpacing(child1: Block?, child2: Block): Spacing? = null
                    override fun buildChildren(): List<Block> = emptyList()
                }
            }
        }
    
    override fun getIndent(): Indent? = myIndent
    
    override fun buildChildren(): List<Block> = kotlinDelegationBlock.buildChildren()
    
    override fun getSpacing(child1: Block?, child2: Block): Spacing? = mySpacingBuilder.getSpacing(this, child1, child2)
    
    override fun getChildAttributes(newChildIndex: Int): ChildAttributes = kotlinDelegationBlock.getChildAttributes(newChildIndex)
    
    override fun isLeaf(): Boolean = kotlinDelegationBlock.isLeaf()
}

private fun createAlignment(alignOption: Boolean, defaultAlignment: Alignment?): Alignment? {
    return if (alignOption) createAlignmentOrDefault(null, defaultAlignment) else defaultAlignment
}

private fun createAlignmentOrDefault(base: Alignment?, defaultAlignment: Alignment?): Alignment? {
    return defaultAlignment ?: if (base == null) Alignment.createAlignment() else Alignment.createChildAlignment(base)
}