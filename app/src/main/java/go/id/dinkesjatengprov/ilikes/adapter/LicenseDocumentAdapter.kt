package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemLicenseDocumentBinding
import okhttp3.internal.notify

class LicenseDocumentAdapter(
    val status: String?,
    val list: ArrayList<LicenseDocumentModel>?,
    val listener: Listener
) :
    RecyclerView.Adapter<LicenseDocumentAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLicenseDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_license_document, parent, false)
        return ViewHolder(ItemLicenseDocumentBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ildTvName.text = list?.get(position)?.document?.description

        if (list?.get(position)?.id != null) {
            holder.binding.ildClFile.visibility = View.VISIBLE
            val context = holder.itemView.context

            val status = if (list[position].status == "") "UPLOADED" else list[position].status?.toUpperCase()
            holder.binding.ildTvFilename.text = status

            val note = list[position].note
            holder.binding.ildTvNote.visibility = if (note!=null) View.VISIBLE else View.GONE
            holder.binding.ildTvNote.text = note

            holder.binding.ildParent.setBackgroundTint(context, status)
            holder.binding.ildClFile.setBackground(context, status)
            holder.binding.ildTvFilename.setTextColor(context, status)
            holder.binding.ildTvNote.setTextColor(context, status)
            holder.binding.ildIvUpload.setIcon(context, status)

        } else {
            holder.binding.ildClFile.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            listener.onClick(status, list?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    private fun ConstraintLayout.setBackgroundTint(context: Context, status: String?) {
        backgroundTintList = when (status) {
            "APPROVED" -> context.resources.getColorStateList(R.color.tint_color_white_green)
            "REJECTED" -> context.resources.getColorStateList(R.color.tint_color_white_red)
            else -> context.resources.getColorStateList(R.color.tint_color_white_yellow)
        }
    }

    private fun ConstraintLayout.setBackground(context: Context, status: String?) {
        background = when (status) {
            "APPROVED" -> context.resources.getDrawable(R.drawable.bg_soft_approved)
            "REJECTED" -> context.resources.getDrawable(R.drawable.bg_soft_brand)
            else -> context.resources.getDrawable(R.drawable.bg_soft_default)
        }
    }

    private fun TextView.setTextColor(context: Context, status: String?) {
        when (status) {
            "APPROVED" -> setTextColor(context.resources.getColor(R.color.basic_green))
            "REJECTED" -> setTextColor(context.resources.getColor(R.color.basic_red))
            else -> setTextColor(context.resources.getColor(R.color.basic_yellow))
        }
    }

    private fun ImageView.setIcon(context: Context, status: String?) {
        when (status) {
            "APPROVED" -> Glide.with(context)
                .load(R.drawable.ic_checkbox)
                .into(this)
            "REJECTED" -> Glide.with(context)
                .load(R.drawable.ic_rejected)
                .into(this)
            else -> Glide.with(context)
                .load(R.drawable.ic_upload)
                .into(this)
        }
    }

    interface Listener {
        fun onClick(status: String?, document: LicenseDocumentModel?)
    }

}