package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.ScheduleDoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiContactModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemPmiContactBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemScheduleBinding

class PmiContactAdapter(val list: ArrayList<PmiContactModel>?) :
    RecyclerView.Adapter<PmiContactAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemPmiContactBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pmi_contact, parent, false)
        return ViewHolder(ItemPmiContactBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.pmiTvName.text = list?.get(position)?.nama
        holder.binding.pmiTvAddress.text = list?.get(position)?.alamat
        holder.binding.pmiTvPhone.text = list?.get(position)?.telepon
        holder.binding.pmiTvEmail.text = list?.get(position)?.email
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}