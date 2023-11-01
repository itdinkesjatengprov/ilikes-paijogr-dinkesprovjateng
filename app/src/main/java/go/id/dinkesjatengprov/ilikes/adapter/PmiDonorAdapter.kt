package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.ScheduleDoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodDonorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiContactModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemPmiContactBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemPmiDonorBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemScheduleBinding

class PmiDonorAdapter(val list: ArrayList<PmiBloodDonorModel>?) :
    RecyclerView.Adapter<PmiDonorAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemPmiDonorBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pmi_donor, parent, false)
        return ViewHolder(ItemPmiDonorBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.pmiTvName.text = list?.get(position)?.nama
        holder.binding.pmiTvInstantion.text = list?.get(position)?.instansi
        holder.binding.pmiTvTime.text =
            "${list?.get(position)?.tglMu} ${list?.get(position)?.jamMulai} - ${list?.get(position)?.jamMulai}"
        holder.binding.pmiTvFor.text = list?.get(position)?.peruntukan
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}