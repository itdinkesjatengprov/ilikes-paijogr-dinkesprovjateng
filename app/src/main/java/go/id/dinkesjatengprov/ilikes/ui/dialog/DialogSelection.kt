package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.SelectionRecyclerView
import go.id.dinkesjatengprov.ilikes.databinding.DialogSelectStringBinding

class DialogSelection(
    context: Context,
    val titleDialog: String,
    val list: ArrayList<String>?,
    val selectionText: String?
) : Dialog(context) {

    lateinit var binding: DialogSelectStringBinding
    var mSelectionText: String? = selectionText

    var mListener: DialogSelectionListener? = null

    init {
        mListener = object : DialogSelectionListener {
            override fun onClick(dialog: DialogSelection, text: String?) {
                dialog.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogSelectStringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dmTvTitle.text = titleDialog
        binding.dsgRvOption.layoutManager = LinearLayoutManager(context)
        val adapter = SelectionRecyclerView(list, selectionText)
        binding.dsgRvOption.adapter = adapter

        binding.dmBtnSecondary.setOnClickListener {
            dismiss()
            mSelectionText = adapter.mSelectionText
            mListener?.onClick(this, mSelectionText)
        }

    }

    fun setListenerButtonPrimary(listener: DialogSelectionListener): DialogSelection {
        mListener = listener
        return this
    }
}

interface DialogSelectionListener {
    fun onClick(dialog: DialogSelection, text: String?)
}