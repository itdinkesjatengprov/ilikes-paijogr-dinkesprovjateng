package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.ConsultationModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemConsultationBinding


class ConsultationAdapter(val list: ArrayList<ConsultationModel>) :
    RecyclerView.Adapter<ConsultationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemConsultationBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_consultation, parent, false)
        return ViewHolder(ItemConsultationBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.iconsTvName.text = list.get(position).name

        Glide.with(holder.itemView.context)
            .load(list.get(position).icon)
            .into(holder.binding.iconsIv)

        holder.binding.iconsTvConsultation.visibility =
            if (list.get(position).isConsultation) View.VISIBLE else View.GONE
        holder.binding.iconsTvMedicine.visibility =
            if (list.get(position).isMedicine) View.VISIBLE else View.GONE
        holder.binding.iconsTvMedicineRedeem.visibility =
            if (list.get(position).isMedicineRedeem) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).url))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}