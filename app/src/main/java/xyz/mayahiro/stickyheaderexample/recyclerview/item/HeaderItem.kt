package xyz.mayahiro.stickyheaderexample.recyclerview.item

import androidx.appcompat.widget.AppCompatTextView
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import xyz.mayahiro.stickyheaderexample.R

class HeaderItem(val text: String) : Item<GroupieViewHolder>(), StickyHeaderItem {
    override fun getLayout(): Int = R.layout.item_header

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<AppCompatTextView>(R.id.text_view).text = text
        viewHolder.itemView.alpha = 1F
    }
}
