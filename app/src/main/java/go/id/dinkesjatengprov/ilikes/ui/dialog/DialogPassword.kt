package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBar
import go.id.dinkesjatengprov.ilikes.databinding.DialogPasswordBinding

class DialogPassword(context: Context) : Dialog(context) {

    var title: String? = null
    var message: String? = null
    var background: Int? = null
    var textPrimary: String? = null
    var listenerPrimary: DialogPasswordListener? = null
    var textSecondary: String? = null
    var listenerSecondary: DialogPasswordListener? = null
    var isDialogCancelable: Boolean = true

    lateinit var binding: DialogPasswordBinding

    init {
        title = "Perhatian"
        message = "Terjadi Kesalahan"
        textPrimary = "Ok"
        listenerPrimary = object : DialogPasswordListener {
            override fun onClick(dialog: DialogPassword, isTrue: Boolean) {
                dismiss()
            }
        }
        listenerSecondary = object :
            DialogPasswordListener {
            override fun onClick(dialog: DialogPassword, isTrue: Boolean) {
                dismiss()
            }
        }
        isDialogCancelable = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dmTvTitle.text = title

        binding.dmBtnPrimary.text = textPrimary
        binding.dmBtnPrimary.setOnClickListener {
            dismiss()
            listenerPrimary?.onClick(
                this,
                binding.tilPassword.editText?.text.toString() == "DINKESGAYENG"
            )
        }

        binding.dmBtnSecondary.visibility = if (textSecondary != null) View.VISIBLE else View.GONE
        binding.dmBtnSecondary.text = textSecondary
        binding.dmBtnSecondary.setOnClickListener {
            dismiss()
            listenerSecondary?.onClick(
                this,
                binding.tilPassword.editText?.text.toString() == "DINKESGAYENG"
            )
        }

        this.setCancelable(isDialogCancelable)

    }

    fun setTitle(text: String?): DialogPassword {
        title = text
        return this
    }

    fun setMessage(text: String?): DialogPassword {
        message = text
        return this
    }

    fun setTextButtonPrimary(text: String): DialogPassword {
        textPrimary = text
        return this
    }

    fun setListenerButtonPrimary(listener: DialogPasswordListener): DialogPassword {
        listenerPrimary = listener
        return this
    }

    fun setTextButtonSecondary(text: String): DialogPassword {
        textSecondary = text
        return this
    }

    fun setListenerButtonSecondary(listener: DialogPasswordListener): DialogPassword {
        listenerSecondary = listener
        return this
    }

    fun setDialogCancelable(value: Boolean): DialogPassword {
        isDialogCancelable = value
        return this
    }

    fun showDialog() {
        show()
    }
}

interface DialogPasswordListener {
    fun onClick(dialog: DialogPassword, isTrue: Boolean)
}