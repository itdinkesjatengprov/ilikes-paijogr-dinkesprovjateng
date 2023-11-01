package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.databinding.DialogLoadingBinding
import go.id.dinkesjatengprov.ilikes.databinding.DialogViewBannerBinding

class DialogViewBanner(context: Context) : Dialog(context) {

    lateinit var binding: DialogViewBannerBinding

    var imageInt: Int? = null
    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogViewBannerBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (imageInt != null) {
            binding.dvbIv.setImageResource(imageInt!!)
        }

        if (imageUrl != null) {
            Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(binding.dvbIv)
        }
    }

    fun setImage(image: Int): DialogViewBanner {
        imageInt = image
        return this
    }

    fun setImage(image: String): DialogViewBanner {
        imageUrl = image
        return this
    }
}