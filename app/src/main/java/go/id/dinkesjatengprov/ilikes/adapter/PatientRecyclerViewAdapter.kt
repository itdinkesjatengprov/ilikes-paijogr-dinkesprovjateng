package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemPatientBinding
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_yyyy_MM_dd
import go.id.dinkesjatengprov.ilikes.helper.parser.countAge

class PatientRecyclerViewAdapter(val list: ArrayList<BookingModel>) :
    RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder>() {

    var selectedPatient: BookingModel? = null

    class ViewHolder(val binding: ItemPatientBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return ViewHolder(ItemPatientBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (selectedPatient==list.get(position)){
            holder.binding.ipClParent.background = holder.itemView.context.resources.getDrawable(R.drawable.bg_selected_patient)
        }else{
            holder.binding.ipClParent.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
        }

        val age = countAge(list[position].patientIdentity?.birthDate, SDF_TYPE_yyyy_MM_dd)
        holder.binding.ipTvAge.text = "${age}th"

        if (age < 17) Glide.with(holder.itemView.context).load(R.drawable.ic_child)
            .into(holder.binding.ipIvAge) else Glide.with(holder.itemView.context)
            .load(R.drawable.ic_adult).into(holder.binding.ipIvAge)

        holder.binding.ipTvName.text = list.get(position).patientIdentity?.name
        holder.binding.ipTvAddress.text = list.get(position).patientIdentity?.addressKtp?.address

        holder.itemView.setOnClickListener {
            selectedPatient = list.get(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}