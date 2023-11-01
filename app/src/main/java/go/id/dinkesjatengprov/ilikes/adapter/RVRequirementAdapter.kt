package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.LicenseRequirementModel
import go.id.dinkesjatengprov.ilikes.data.static.requirementStrttk
import go.id.dinkesjatengprov.ilikes.databinding.ItemBodyRequirementBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemHeaderRequirementBinding

class RVRequirementAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolderHeader(val binding: ItemHeaderRequirementBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ViewHolderBody(val binding: ItemBodyRequirementBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (requirementStrttk.get(position).type) {
            "HEADER" -> 1
            else -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header_requirement, parent, false)
                return ViewHolderHeader(ItemHeaderRequirementBinding.bind(view))
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_body_requirement, parent, false)
                return ViewHolderBody(ItemBodyRequirementBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                val holder1 = holder as ViewHolderHeader
                holder1.binding.ihrTv.text = requirementStrttk.get(position).description
            }
            else -> {
                val holder1 = holder as ViewHolderBody
                holder1.binding.ibrTv.text = requirementStrttk.get(position).description
            }
        }
    }

    override fun getItemCount(): Int {
        return requirementStrttk.size
    }


}