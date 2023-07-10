package com.example.work_3_2

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView

class DragItemHelperCallback(
    private val mAdapter: ItemTouchHelperAdapter,
    private val itemChangePositionCallback: (Int, Int) -> Unit
) : ItemTouchHelper.Callback() {
    private var oldPosition = -1

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ACTION_STATE_IDLE) {
            oldPosition = viewHolder?.absoluteAdapterPosition ?: -1
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        itemChangePositionCallback(
            oldPosition,
            viewHolder.absoluteAdapterPosition,
        )
        super.clearView(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onItemMove(
            viewHolder.absoluteAdapterPosition,
            target.absoluteAdapterPosition,
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}