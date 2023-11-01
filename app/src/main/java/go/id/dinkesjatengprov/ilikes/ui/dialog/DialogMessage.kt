package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBar
import go.id.dinkesjatengprov.ilikes.databinding.DialogMessageBinding

class DialogMessage(context: Context) : Dialog(context) {

    var title: String? = null
    var message: String? = null
    var background: Int? = null
    var textPrimary: String? = null
    var listenerPrimary: DialogMessageListener? = null
    var textSecondary: String? = null
    var listenerSecondary: DialogMessageListener? = null
    var isDialogCancelable: Boolean = true

    lateinit var binding: DialogMessageBinding

    init {
        title = "Perhatian"
        message = "Terjadi Kesalahan"
        textPrimary = "Ok"
        listenerPrimary = object :
            DialogMessageListener {
            override fun onClick(dialogMessage: DialogMessage) {
                dismiss()
            }
        }
        listenerSecondary = object :
            DialogMessageListener {
            override fun onClick(dialogMessage: DialogMessage) {
                dismiss()
            }
        }
        isDialogCancelable = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dmTvTitle.text = title
        binding.dmTvMessage.text = message

        binding.dmBtnPrimary.text = textPrimary
        binding.dmBtnPrimary.setOnClickListener {
            dismiss()
            listenerPrimary?.onClick(this)
        }

        binding.dmBtnSecondary.visibility = if (textSecondary != null) View.VISIBLE else View.GONE
        binding.dmBtnSecondary.text = textSecondary
        binding.dmBtnSecondary.setOnClickListener {
            dismiss()
            listenerSecondary?.onClick(this)
        }

        this.setCancelable(isDialogCancelable)

    }

    fun setTitle(text: String?): DialogMessage {
        title = text
        return this
    }

    fun setMessage(text: String?): DialogMessage {
        message = text
        return this
    }

    fun setTextButtonPrimary(text: String): DialogMessage {
        textPrimary = text
        return this
    }

    fun setListenerButtonPrimary(listener: DialogMessageListener): DialogMessage {
        listenerPrimary = listener
        return this
    }

    fun setTextButtonSecondary(text: String): DialogMessage {
        textSecondary = text
        return this
    }

    fun setListenerButtonSecondary(listener: DialogMessageListener): DialogMessage {
        listenerSecondary = listener
        return this
    }

    fun setDialogCancelable(value: Boolean): DialogMessage {
        isDialogCancelable = value
        return this
    }

    fun showDialog() {
        show()
    }
}

interface DialogMessageListener {
    fun onClick(dialogMessage: DialogMessage)
}