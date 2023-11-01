package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.TelemedicineModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemTelemedicineBinding


class TelemedicineAdapter(val list: ArrayList<TelemedicineModel>) :
    RecyclerView.Adapter<TelemedicineAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTelemedicineBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_telemedicine, parent, false)
        return ViewHolder(ItemTelemedicineBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.iconsTvName.text = list.get(position).name

        Glide.with(holder.itemView.context)
            .load(list.get(position).icon)
            .into(holder.binding.iconsIv)

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).url))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}