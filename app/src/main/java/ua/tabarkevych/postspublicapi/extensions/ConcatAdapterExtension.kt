package ua.tabarkevych.postspublicapi.extensions

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

fun <R> ConcatAdapter.findAdapterOrNull(klass: Class<R>): R? where R : RecyclerView.Adapter<out RecyclerView.ViewHolder> {
    return adapters
        .filterIsInstance(klass)
        .firstOrNull()
}