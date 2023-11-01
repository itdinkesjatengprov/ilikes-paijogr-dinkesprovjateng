package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.psc.PscPhotoModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemPhotoPscBinding

class PscPhotoAdapter(var list: ArrayList<PscPhotoModel>) :
    RecyclerView.Adapter<PscPhotoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPhotoPscBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo_psc, parent, false)
        return ViewHolder(ItemPhotoPscBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(list.get(position).fileImage)
            .into(holder.binding.ippIv)
    }

    override fun getItemCount(): Int {
        return list.size ?: 0
    }

    fun updatePhoto(newList: ArrayList<PscPhotoModel>) {
        list = newList
        notifyDataSetChanged()
    }
}