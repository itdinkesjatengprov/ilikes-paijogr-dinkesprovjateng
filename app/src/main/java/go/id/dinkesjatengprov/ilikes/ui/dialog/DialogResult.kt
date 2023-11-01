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
import go.id.dinkesjatengprov.ilikes.databinding.DialogResultBinding
import java.io.File

class DialogResult(context: Context, val file: File?) : Dialog(context) {

    var listener: DialogResultListener? = null

    lateinit var binding: DialogResultBinding

    init {
        listener = object :
            DialogResultListener {
            override fun onClick(dialogMessage: DialogResult) {
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.drPdfview.fromFile(file!!).show()

        binding.drBtnShare.setOnClickListener {
            dismiss()
            listener?.onClick(this)
        }

        binding.drIvClose.setOnClickListener {
            dismiss()
        }

    }

    fun setListenerButtonPrimary(listener: DialogResultListener): DialogResult {
        this.listener = listener
        return this
    }

    fun showDialog() {
        show()
    }
}

interface DialogResultListener {
    fun onClick(dialogMessage: DialogResult)
}