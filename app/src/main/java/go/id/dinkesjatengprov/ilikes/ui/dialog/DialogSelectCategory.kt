package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.ActionBar
import go.id.dinkesjatengprov.ilikes.databinding.DialogSelectCategoryBinding
import go.id.dinkesjatengprov.ilikes.helper.*

class DialogSelectCategory(context: Context) : Dialog(context) {

    lateinit var binding: DialogSelectCategoryBinding
    var listener: DialogSelectCategoryListener? = null

    init {
        listener = object : DialogSelectCategoryListener {
            override fun onClick(dialog: DialogSelectCategory, category: String) {
                dialog.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogSelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dscCvAccident.setOnClickListener {
            listener?.onClick(this, PSC_ACCIDENT)
        }

        binding.dscCvDisaster.setOnClickListener {
            listener?.onClick(this, PSC_DISASTER)
        }

        binding.dscCvReference.setOnClickListener {
            listener?.onClick(this, PSC_REFERENCE)
        }

        binding.dscCvMaternity.setOnClickListener {
            listener?.onClick(this, PSC_MATERNITY)
        }

        binding.dscBtnCancel.setOnClickListener {
            listener?.onClick(this, PSC_CANCEL)
        }
    }

    fun setListener(listener: DialogSelectCategoryListener): DialogSelectCategory {
        this.listener = listener
        return this
    }
}

interface DialogSelectCategoryListener {
    fun onClick(dialog: DialogSelectCategory, category: String)
}