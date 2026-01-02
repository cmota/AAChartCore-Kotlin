package com.github.aachartmodel.aainfographics.demo.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Default color palette for the expandable list.
 * Contains 18 predefined colors that cycle based on group position.
 */
val DefaultColors = listOf(
    Color(0xFF5470c6),
    Color(0xFF91cc75),
    Color(0xFFfac858),
    Color(0xFFee6666),
    Color(0xFF73c0de),
    Color(0xFF3ba272),
    Color(0xFFfc8452),
    Color(0xFF9a60b4),
    Color(0xFFea7ccc),
    Color(0xFF5470c6),
    Color(0xFF91cc75),
    Color(0xFFfac858),
    Color(0xFFee6666),
    Color(0xFF73c0de),
    Color(0xFF3ba272),
    Color(0xFFfc8452),
    Color(0xFF9a60b4),
    Color(0xFFea7ccc)
)

/**
 * Formats a position index as a two-digit string.
 * Position 0 becomes "01", position 9 becomes "10", etc.
 *
 * @param position The zero-based position index
 * @return A two-digit string representation of (position + 1)
 */
fun formatIndex(position: Int): String = String.format("%02d", position + 1)

/**
 * Gets the accent color for a group based on its position.
 * Colors cycle through the provided color list.
 *
 * @param groupPosition The zero-based group position
 * @param colors The list of colors to cycle through (defaults to DefaultColors if empty)
 * @return The color at position (groupPosition % colors.size)
 */
fun getAccentColor(groupPosition: Int, colors: List<Color>): Color {
    val effectiveColors = colors.ifEmpty { DefaultColors }
    return effectiveColors[groupPosition % effectiveColors.size]
}

/**
 * A composable that displays a group item in the expandable list.
 * 
 * Features:
 * - Card container with rounded corners
 * - Index badge with accent color background
 * - Group name with accent color text
 * - Expand/collapse indicator with rotation animation
 * - Background color changes based on expanded state
 *
 * @param index The zero-based index of the group
 * @param name The display name of the group
 * @param isExpanded Whether the group is currently expanded
 * @param accentColor The accent color for this group
 * @param onClick Callback when the group is clicked
 * @param modifier Optional modifier for the composable
 */
@Composable
fun GroupItem(
    index: Int,
    name: String,
    isExpanded: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate the rotation of the expand indicator
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "indicatorRotation"
    )
    
    // Calculate colors with alpha
    val indexBgColor = accentColor.copy(alpha = 0.82f) // ~210/255
    val textColor = accentColor.copy(alpha = 0.90f) // ~230/255
    val strokeColor = accentColor.copy(alpha = 0.24f) // ~60/255
    val expandedBgColor = accentColor.copy(alpha = 0.11f) // ~28/255
    val backgroundColor = if (isExpanded) expandedBgColor else Color.White
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, strokeColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Index badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = indexBgColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatIndex(index),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Group name
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            
            // Expand/collapse indicator
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle),
                tint = Color.Gray
            )
        }
    }
}

/**
 * A composable that displays a child item in the expandable list.
 * 
 * Features:
 * - Card container with rounded corners and accent color background
 * - Index badge with accent color background
 * - Name text
 * - Subtitle text with format "示例 XX · [分组名称]"
 * - Chevron icon rotated -90 degrees
 * - Click ripple effect with accent color
 *
 * @param index The zero-based index of the child item
 * @param name The display name of the child item
 * @param groupName The name of the parent group (used in subtitle)
 * @param accentColor The accent color for this child item (inherited from parent group)
 * @param onClick Callback when the child item is clicked
 * @param modifier Optional modifier for the composable
 */
@Composable
fun ChildItem(
    index: Int,
    name: String,
    groupName: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Calculate colors with alpha matching original implementation
    val indexBgColor = accentColor.copy(alpha = 0.82f) // ~210/255
    val subtitleColor = accentColor.copy(alpha = 0.67f) // ~170/255
    val strokeColor = accentColor.copy(alpha = 0.18f) // ~45/255
    val backgroundColor = accentColor.copy(alpha = 0.09f) // ~24/255
    val rippleColor = accentColor.copy(alpha = 0.35f) // ~90/255
    val chevronColor = accentColor.copy(alpha = 0.78f) // ~200/255
    
    // Format subtitle: "示例 XX · [分组名称]"
    val subtitle = "示例 ${formatIndex(index)} · $groupName"
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = rippleColor),
                onClick = onClick
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, strokeColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Index badge
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = indexBgColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatIndex(index),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Name and subtitle column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    color = subtitleColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Chevron icon (rotated -90 degrees to point right)
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Navigate",
                modifier = Modifier
                    .size(20.dp)
                    .rotate(-90f),
                tint = chevronColor
            )
        }
    }
}


/**
 * A composable that displays an expandable list with groups and child items.
 * 
 * Features:
 * - LazyColumn for efficient scrolling
 * - Expandable groups that show/hide child items on click
 * - State management using mutableStateMapOf for expand/collapse state
 * - Customizable colors with cycling through the color list
 * - Child item click callbacks with group and child indices
 *
 * @param groupData List of group names to display
 * @param childData List of lists containing child item names for each group
 * @param colors List of colors to cycle through for groups (defaults to DefaultColors)
 * @param onChildClick Callback when a child item is clicked, receives (groupIndex, childIndex)
 * @param modifier Optional modifier for the composable
 */
@Composable
fun ExpandableListCompose(
    groupData: List<String>,
    childData: List<List<String>>,
    colors: List<Color> = DefaultColors,
    onChildClick: (groupIndex: Int, childIndex: Int) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    // State to track which groups are expanded
    val expandedGroups = remember { mutableStateMapOf<Int, Boolean>() }
    
    LazyColumn(modifier = modifier) {
        groupData.forEachIndexed { groupIndex, groupName ->
            // Check if this group is expanded
            val isExpanded = expandedGroups[groupIndex] == true
            val accentColor = getAccentColor(groupIndex, colors)
            
            // Group item
            item(key = "group_$groupIndex") {
                GroupItem(
                    index = groupIndex,
                    name = groupName,
                    isExpanded = isExpanded,
                    accentColor = accentColor,
                    onClick = {
                        // Toggle expand state
                        expandedGroups[groupIndex] = !(expandedGroups[groupIndex] ?: false)
                    }
                )
            }
            
            // Child items (only rendered when group is expanded)
            if (isExpanded) {
                val children = childData.getOrNull(groupIndex) ?: emptyList()
                children.forEachIndexed { childIndex, childName ->
                    item(key = "child_${groupIndex}_$childIndex") {
                        ChildItem(
                            index = childIndex,
                            name = childName,
                            groupName = groupName,
                            accentColor = accentColor,
                            onClick = {
                                onChildClick(groupIndex, childIndex)
                            }
                        )
                    }
                }
            }
        }
    }
}
