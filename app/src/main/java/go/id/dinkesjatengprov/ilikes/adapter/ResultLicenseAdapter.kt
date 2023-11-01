package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.LicenseResultModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemResultBinding

class ResultLicenseAdapter(
    var list: ArrayList<LicenseResultModel>?,
    val listener: ResultLicenseListener
) :
    RecyclerView.Adapter<ResultLicenseAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ViewHolder(ItemResultBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ihTvDocumentNumber.text = list?.get(position)?.documentNumber
        holder.binding.ihTvName.text = list?.get(position)?.name

        holder.itemView.setOnClickListener {
            listener.downloadResult(list?.get(position))
        }

        holder.binding.ihBtnDownload.setOnClickListener {
            listener.downloadResult(list?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    interface ResultLicenseListener {
        fun downloadResult(result: LicenseResultModel?)
    }

}