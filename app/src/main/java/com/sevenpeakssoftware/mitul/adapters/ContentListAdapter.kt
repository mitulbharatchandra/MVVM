package com.sevenpeakssoftware.mitul.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.mitul.R
import com.sevenpeakssoftware.mitul.databinding.ListItemContentBinding
import com.sevenpeakssoftware.mitul.domain.Content

class ContentListAdapter: RecyclerView.Adapter<ContentViewHolder>() {

    var list: List<Content> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val withDataBinding: ListItemContentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ContentViewHolder.LAYOUT,
            parent,
            false)
        return ContentViewHolder(withDataBinding)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.content = list[position]
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class ContentViewHolder(val viewDataBinding: ListItemContentBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_content
    }
}