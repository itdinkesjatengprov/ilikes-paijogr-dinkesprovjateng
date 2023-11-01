package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemDialogSelectionBinding

class SelectionRegionRecyclerView(
    val list: ArrayList<SelectionModel>?,
    selection: SelectionModel?
) : RecyclerView.Adapter<SelectionRegionRecyclerView.ViewHolder>() {

    var mSelection: SelectionModel? = selection

    class ViewHolder(val binding: ItemDialogSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_selection, parent, false)
        return ViewHolder(ItemDialogSelectionBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.idsTextview.text = list?.get(position)?.name
        if (list?.get(position) == mSelection) {
            val drawableEnd = holder.itemView.context.resources.getDrawable(R.drawable.ic_check)
            holder.binding.idsTextview.setCompoundDrawables(null, null, drawableEnd, null)
            val backgroundColor = holder.itemView.context.resources.getColor(R.color.soft_brand)
            holder.binding.idsTextview.setBackgroundColor(backgroundColor)
        } else {
            holder.binding.idsTextview.setCompoundDrawables(null, null, null, null)
            val backgroundColor = holder.itemView.context.resources.getColor(R.color.white)
            holder.binding.idsTextview.setBackgroundColor(backgroundColor)
        }

        holder.itemView.setOnClickListener {
            mSelection = list?.get(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}