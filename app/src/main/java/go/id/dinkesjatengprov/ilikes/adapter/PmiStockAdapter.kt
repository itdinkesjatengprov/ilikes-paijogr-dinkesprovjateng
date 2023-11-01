package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.ScheduleDoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodStockModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiContactModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemPmiContactBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemPmiStockBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemScheduleBinding

class PmiStockAdapter(val list: ArrayList<PmiBloodStockModel>?) :
    RecyclerView.Adapter<PmiStockAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemPmiStockBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pmi_stock, parent, false)
        return ViewHolder(ItemPmiStockBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.pmiTvName.text = list?.get(position)?.nama
        holder.binding.pmiTvGoldA.text = "Golongan Darah A\t\t: ${list?.get(position)?.goldaA}"
        holder.binding.pmiTvGoldB.text = "Golongan Darah B\t\t: ${list?.get(position)?.goldaB}"
        holder.binding.pmiTvGoldAb.text = "Golongan Darah AB\t: ${list?.get(position)?.goldaAb}"
        holder.binding.pmiTvGoldO.text = "Golongan Darah O\t\t: ${list?.get(position)?.goldaO}"
        holder.binding.pmiTvUpdate.text = "Update per ${list?.get(position)?.tglUpdate}"
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}