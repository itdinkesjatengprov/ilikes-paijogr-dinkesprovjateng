package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.MainMenuModel
import go.id.dinkesjatengprov.ilikes.databinding.ItemMenuBinding
import go.id.dinkesjatengprov.ilikes.helper.MENU_TOSS_TBC
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.activity.pmi.PmiActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.WebviewActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogPassword
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogPasswordListener


class MenuAdapter(val list: ArrayList<MainMenuModel>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(ItemMenuBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menuTv.text = list.get(position).title
        holder.binding.menuCl.background =
            holder.itemView.resources.getDrawable(R.drawable.bg_menu_icon)
        holder.binding.menuCl.backgroundTintList =
            holder.itemView.resources.getColorStateList(list.get(position).tint)
        Glide.with(holder.itemView.context).load(list.get(position).icon)
            .into(holder.binding.menuIv)

        holder.itemView.setOnClickListener {
            val menu = list[position]

            if (menu.isLock==true){
                DialogPassword(holder.itemView.context)
                    .setTextButtonPrimary("CEK")
                    .setTextButtonSecondary("BATAL")
                    .setListenerButtonPrimary(object : DialogPasswordListener {
                        override fun onClick(dialog: DialogPassword, isTrue: Boolean) {
                            dialog.dismiss()
                            if (isTrue) {
                                openMenu(menu, holder, holder.absoluteAdapterPosition)
                            }else{
                                holder.itemView.context.showToast("Kata Sandi Salah")
                            }
                        }
                    })
                    .showDialog()
            }else{
                openMenu(menu, holder, holder.absoluteAdapterPosition)
            }
        }
    }

    fun openMenu(menu: MainMenuModel, holder: ViewHolder, position: Int,){
        when (menu.type) {
            1 -> {
                val intent =
                    Intent(holder.itemView.context, list[position].page!!::class.java)
                holder.itemView.context.startActivity(intent)
            }
            2 -> {
                val intent = Intent(holder.itemView.context, WebviewActivity::class.java)
                intent.putExtra("URL_TITLE", "${menu.title?.replace("\n", " ")}")
                intent.putExtra("URL", menu.url)
                holder.itemView.context.startActivity(intent)
            }
            3 -> {
                if(menu.tag== MENU_TOSS_TBC){
                    val intent = holder.itemView.context.packageManager.getLaunchIntentForPackage(menu.url!!)
                    if (intent != null) {
                        holder.itemView.context.startActivity(intent)
                    } else {
                        val playStoreIntent = Intent(Intent.ACTION_VIEW)
                        playStoreIntent.data = Uri.parse("market://details?id=${menu.url}")
                        holder.itemView.context.startActivity(playStoreIntent)
                    }
                }else{
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(menu.url)
                    holder.itemView.context.startActivity(i)
                }
            }
            4 -> {
                val intent = Intent(holder.itemView.context, PmiActivity::class.java)
                intent.putExtra("SLUG", menu.tag)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}