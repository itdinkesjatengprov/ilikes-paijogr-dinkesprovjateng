package go.id.dinkesjatengprov.ilikes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.data.model.ScheduleDoctorModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemScheduleBinding

class ScheduleDoctorRecyclerViewAdapter(val schedule: ArrayList<ScheduleDoctorModel>?) :
    RecyclerView.Adapter<ScheduleDoctorRecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(ItemScheduleBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.isTvDay.text = schedule?.get(position)?.day
        if (schedule?.get(position)?.startTime == null || schedule?.get(position)?.endTime == null) {
            holder.binding.isTvDay.setTextColor(holder.itemView.context.resources.getColor(R.color.ghost_grey))
            holder.binding.isTvTime.text = null
        } else {
            holder.binding.isTvTime.text =
                "${schedule.get(position).startTime} - ${schedule.get(position).endTime}"
            holder.binding.isTvDay.setTextColor(holder.itemView.context.resources.getColor(R.color.dark_grey))
        }
    }

    override fun getItemCount(): Int {
        return schedule?.size ?: 0
    }

}