package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.TreatmentModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemTreatmentBinding
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.WebviewActivity


class TreatmentAdapter(val list: ArrayList<TreatmentModel>) :
    RecyclerView.Adapter<TreatmentAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTreatmentBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_treatment, parent, false)
        return ViewHolder(ItemTreatmentBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itTvTitle.text = list.get(position).name
        holder.binding.itTvAddress.text = list.get(position).address
        Glide.with(holder.itemView.context)
            .load(list.get(position).icon)
            .into(holder.binding.itIv)
        if (list.get(position).url != null ) {
            holder.binding.itTvUrl.visibility = View.VISIBLE
            if(list.get(position).url!!.startsWith("https://play.google.com")){
                holder.binding.itTvUrl.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).url))
                    holder.itemView.context.startActivity(browserIntent)
                }
            }else {
                holder.binding.itTvUrl.setOnClickListener {
                    val intent = Intent(holder.itemView.context, WebviewActivity::class.java)
                    intent.putExtra("URL_TITLE", list.get(position).name)
                    intent.putExtra("URL", list.get(position).url)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}