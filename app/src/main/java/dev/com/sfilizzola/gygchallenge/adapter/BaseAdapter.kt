package dev.com.sfilizzola.gygchallenge.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

abstract class BaseAdapter<T, V : ViewDataBinding>(private val layoutId: Int) : RecyclerView.Adapter<BaseAdapter.DataBindViewHolder<V>>() {

    private var dataVersion: Int = 0
    protected var items = mutableListOf<T>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): DataBindViewHolder<V> {
        val binder = DataBindingUtil.inflate<V>(LayoutInflater.from(viewGroup.context), layoutId, viewGroup, false)
        return DataBindViewHolder(binder)
    }

    override fun onBindViewHolder(holder: DataBindViewHolder<V>, position: Int) {
        bind(holder, position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = items.size

    fun getContent(): MutableList<T> = items

    fun remove(position: Int) {
        items.removeAt(position)
    }

    fun update(update: List<T>) {
        dataVersion++
        when {
            items.isEmpty() -> {
                if (update.isEmpty()) {
                    return
                }
                items.addAll(update)
                notifyDataSetChanged()
            }
            update.isEmpty() -> {
                val oldSize = items.size
                items.clear()
                notifyItemRangeRemoved(0, oldSize)
            }
            else -> {
                val startVersion = dataVersion
                val oldItems = items

                doAsync {
                    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                                this@BaseAdapter.areItemsTheSame(oldItems[oldItemPosition], update[newItemPosition])

                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                                this@BaseAdapter.areContentsTheSame(oldItems[oldItemPosition], update[newItemPosition])

                        override fun getOldListSize() = oldItems.size

                        override fun getNewListSize() = update.size
                    })

                    if (startVersion == dataVersion) {
                        items.clear()
                        items.addAll(update)

                        uiThread {
                            diff.dispatchUpdatesTo(this@BaseAdapter)
                        }
                    }
                }
            }
        }
    }

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    protected abstract fun bind(holder: DataBindViewHolder<V>, position: Int)

    class DataBindViewHolder<out T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)
}