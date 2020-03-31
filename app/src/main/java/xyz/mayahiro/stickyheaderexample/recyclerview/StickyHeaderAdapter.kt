package xyz.mayahiro.stickyheaderexample.recyclerview

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import org.threeten.bp.format.DateTimeFormatter
import xyz.mayahiro.stickyheaderexample.R
import xyz.mayahiro.stickyheaderexample.data.ListData
import xyz.mayahiro.stickyheaderexample.recyclerview.item.ContentItem
import xyz.mayahiro.stickyheaderexample.recyclerview.item.HeaderItem

class StickyHeaderAdapter : GroupAdapter<GroupieViewHolder>(), StickyHeaderInterface {
    private val sections = hashMapOf<String, Section>()

    fun addData(data: List<ListData>) {
        data.forEach {
            val key = it.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)

            var section = sections[key]
            if (section == null) {
                section = Section().also { s ->
                    s.setHeader(HeaderItem(key))
                }
                add(section)
                sections[key] = section
            }

            section.add(ContentItem(it.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), it.dateTime))
        }
    }

    // StickyHeaderInterface
    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return when (val item = getItem(itemPosition)) {
            is HeaderItem -> itemPosition
            else -> {
                val key = (item as ContentItem).dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                val section = sections[key]
                if (section == null) {
                    RecyclerView.NO_POSITION
                } else {
                    val headerItem = section.getItem(0)
                    getAdapterPosition(headerItem)
                }
            }
        }
    }

    override fun getHeaderLayout(headerPosition: Int): Int = getItem(headerPosition).layout

    override fun bindHeaderData(header: View?, headerPosition: Int) {
        val item = getItem(headerPosition) as HeaderItem
        header?.let {
            it.findViewById<AppCompatTextView>(R.id.text_view).text = item.text
        }
    }

    override fun isHeader(itemPosition: Int): Boolean = getItem(itemPosition) is HeaderItem
}
