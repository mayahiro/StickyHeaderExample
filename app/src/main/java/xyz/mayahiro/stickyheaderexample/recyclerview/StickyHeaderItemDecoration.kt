package xyz.mayahiro.stickyheaderexample.recyclerview

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderItemDecoration(private val listener: StickyHeaderInterface) : RecyclerView.ItemDecoration() {
    private var currentHeader: View? = null

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val prevHeaderPosition = listener.getHeaderPositionForItem(topChildPosition)
        if (prevHeaderPosition == RecyclerView.NO_POSITION) {
            return
        }

        currentHeader = getHeaderViewForItem(topChildPosition, parent)
        currentHeader?.let {
            fixLayoutSize(parent, it)

            val contactPoint = it.bottom

            val childInContact = getChildInContact(parent, contactPoint)

            if (childInContact != null && listener.isHeader(parent.getChildAdapterPosition(childInContact))) {
                childInContact.alpha = 1F
                moveHeader(c, it, childInContact)
                return
            }

            drawHeader(c, it)

            parent.findViewHolderForAdapterPosition(prevHeaderPosition)?.itemView?.alpha = 0F
        }
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View {
        val headerPosition = listener.getHeaderPositionForItem(itemPosition)
        val layoutResId = listener.getHeaderLayout(headerPosition)

        val header = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)

        listener.bindHeaderData(header, headerPosition)
        return header
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0F, 0F)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0F, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if ((child.top <= contactPoint) && (child.bottom > contactPoint)) {
                return child
            }
        }

        return null
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)

        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
