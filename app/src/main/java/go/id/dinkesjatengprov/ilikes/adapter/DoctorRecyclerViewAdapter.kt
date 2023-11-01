package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.ClinicModel
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemDoctorBinding

class DoctorRecyclerViewAdapter(val clinic: ClinicModel?) :
    RecyclerView.Adapter<DoctorRecyclerViewAdapter.ViewHolder>() {

    var selectedDoctor: DoctorModel? = null

    class ViewHolder(val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return ViewHolder(ItemDoctorBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = clinic?.doctor
        if (selectedDoctor == list?.get(position)) {
            holder.binding.doctorCl.background = holder.itemView.context.resources.getDrawable(R.drawable.bg_selected_patient)
        } else {
            holder.binding.doctorCl.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
        }

        holder.binding.doctorTvName.text = list?.get(position)?.name
        holder.binding.doctorTvClinic.text = clinic?.name

        resetViewDay(holder)
        for (schedule in list?.get(position)?.schedule?:ArrayList()){
            when(schedule.day){
                "Senin" -> setViewOfDays(holder.binding.doctorTvSenin, holder)
                "Selasa" -> setViewOfDays(holder.binding.doctorTvSelasa,holder)
                "Rabu" -> setViewOfDays(holder.binding.doctorTvRabu,holder)
                "Kamis" -> setViewOfDays(holder.binding.doctorTvKamis,holder)
                "Jumat" -> setViewOfDays(holder.binding.doctorTvJumat,holder)
                "Sabtu" -> setViewOfDays(holder.binding.doctorTvSabtu,holder)
                "Minggu" -> setViewOfDays(holder.binding.doctorTvMinggu,holder)
            }
        }

        holder.itemView.setOnClickListener {
            selectedDoctor = clinic?.doctor?.get(position)
            notifyDataSetChanged()
        }
    }

    private fun setViewOfDays(textview: TextView, holder: ViewHolder) {
        textview.background = holder.itemView.context.resources.getDrawable(R.drawable.bg_selected_patient)
        textview.setTextColor(holder.itemView.context.resources.getColor(R.color.dark_grey))
    }

    private fun resetViewDay(holder: ViewHolder) {
        holder.binding.doctorTvSenin.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvSelasa.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvRabu.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvKamis.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvJumat.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvSabtu.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))
        holder.binding.doctorTvMinggu.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.soft_grey))

        holder.binding.doctorTvSenin.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvSelasa.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvRabu.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvKamis.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvJumat.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvSabtu.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
        holder.binding.doctorTvMinggu.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
    }

    override fun getItemCount(): Int {
        return clinic?.doctor?.size ?: 0
    }
}