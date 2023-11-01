package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseBackground
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseColor
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseText
import go.id.dinkesjatengprov.ilikes.databinding.ItemHistoryBinding
import go.id.dinkesjatengprov.ilikes.helper.LICENSE
import go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk.ResultStrttkActivity

class HistoryLicenseAdapter(
    var list: ArrayList<LicenseModel>?
) :
    RecyclerView.Adapter<HistoryLicenseAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(ItemHistoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = list?.get(position)?.status
        holder.binding.ihTvStatus.text = setStatusLicenseText(status)
        holder.binding.ihTvStatus.background =
            holder.itemView.context.setStatusLicenseBackground(status)
        holder.binding.ihTvStatus.setTextColor(
            holder.itemView.context.setStatusLicenseColor(
                status
            )
        )

        holder.binding.ihTvNik.text = list?.get(position)?.identity?.nik
        holder.binding.ihTvName.text = list?.get(position)?.identity?.name
        holder.binding.ihTvBirthdate.text = list?.get(position)?.identity?.birthDate

        if (status == "Done")
            holder.binding.ihBtnDownload.visibility =
                View.VISIBLE else holder.binding.ihBtnDownload.visibility = View.GONE

        holder.binding.ihBtnDownload.setOnClickListener {
            val intent = Intent(holder.itemView.context, ResultStrttkActivity::class.java)
            intent.putExtra(LICENSE, list?.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun updateData(newList: ArrayList<LicenseModel>?) {
        list = newList
        notifyDataSetChanged()
    }

}