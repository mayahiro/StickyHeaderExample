package xyz.mayahiro.stickyheaderexample.recyclerview.item

import androidx.appcompat.widget.AppCompatTextView
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import org.threeten.bp.LocalDateTime
import xyz.mayahiro.stickyheaderexample.R

class ContentItem(private val text: String, val dateTime: LocalDateTime) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int = R.layout.item_content

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<AppCompatTextView>(R.id.text_view).text = text
    }
}
