package com.example.mynotes.gestures

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R

abstract class SwipeNoteCallBack(
    private val context: Context,
    private val recyclerView: RecyclerView
) : ItemTouchHelper.Callback() {

    private val deleteIcon =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_sweep_24)
    private val editIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_edit_24)
    private val intrinsicWidthDeleteIcon = deleteIcon!!.intrinsicWidth
    private val intrinsicHeightDeleteIcon = deleteIcon!!.intrinsicHeight
    private val intrinsicWidthEditIcon = editIcon!!.intrinsicWidth
    private val intrinsicHeightEditIcon = editIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColorDelete = Color.parseColor("#FF2D55")
    private val backgroundColorComplete = Color.parseColor("#34C759")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        when {
            isCanceled -> {
                clearCanvas(
                    c,
                    itemView.right + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                return
            }
            dX < 0 -> {

                //Background color set to red on left swipe
                background.color = backgroundColorDelete
                //background image set to delete on swipe
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeightEditIcon) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeightEditIcon) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidthEditIcon
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeightEditIcon

                deleteIcon!!.setBounds(
                    deleteIconLeft,
                    deleteIconTop,
                    deleteIconRight,
                    deleteIconBottom
                )
                deleteIcon.draw(c)
            }
            else -> {
                //background color is set to green on right swipe
                background.color = backgroundColorComplete
                //background image is set to edit on swipe
                background.setBounds(
                    itemView.left + dX.toInt(),
                    itemView.top,
                    itemView.left,
                    itemView.bottom
                )
                background.draw(c)

                val editIconTop = itemView.top + (itemHeight - intrinsicHeightDeleteIcon) / 2
                val editIconMargin = (itemHeight - intrinsicHeightDeleteIcon) / 2
                val editIconRight = editIconMargin + intrinsicWidthDeleteIcon
                val editIconBottom = editIconTop + intrinsicHeightDeleteIcon

                editIcon!!.setBounds(editIconMargin, editIconTop, editIconRight, editIconBottom)
                editIcon.draw(c)
            }
        }
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

}