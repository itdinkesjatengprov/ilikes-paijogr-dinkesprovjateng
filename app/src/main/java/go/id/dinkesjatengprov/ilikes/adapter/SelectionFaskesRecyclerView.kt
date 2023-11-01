package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.FaskesModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemFaskesBinding

class SelectionFaskesRecyclerView(
    val list: ArrayList<FaskesModel>?
) : RecyclerView.Adapter<SelectionFaskesRecyclerView.ViewHolder>() {

    var mSelection: FaskesModel? = null

    class ViewHolder(val binding: ItemFaskesBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faskes, parent, false)
        return ViewHolder(ItemFaskesBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.ifTvName.text = list?.get(position)?.name
        holder.binding.ifTvAddress.text = list?.get(position)?.address

        if (list?.get(position) == mSelection) {
            holder.binding.ifCl.setBackgroundResource(R.drawable.bg_selected_patient)
        } else {
            holder.binding.ifCl.setBackgroundColor(holder.itemView.resources.getColor(R.color.pure_white))
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