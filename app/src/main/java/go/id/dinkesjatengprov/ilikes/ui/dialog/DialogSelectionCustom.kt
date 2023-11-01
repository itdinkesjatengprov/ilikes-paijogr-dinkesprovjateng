package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.SelectionCustomRecyclerView
import go.id.dinkesjatengprov.ilikes.adapter.SelectionRecyclerView
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.databinding.DialogSelectStringBinding

class DialogSelectionCustom(
    context: Context,
    val titleDialog: String,
    val list: ArrayList<SelectionModel>?,
    val selectionText: String?
) : Dialog(context) {

    lateinit var binding: DialogSelectStringBinding
    var mSelectionText: String? = selectionText

    var mListener: DialogSelectionCustomListener? = null

    init {
        mListener = object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
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
        val adapter = SelectionCustomRecyclerView(list, selectionText)
        binding.dsgRvOption.adapter = adapter

        binding.dmBtnSecondary.setOnClickListener {
            dismiss()
            mSelectionText = adapter.mSelection?.name
            mListener?.onClick(this, adapter.mSelection)
//            mListener?.onClick(this, mSelectionText)
        }

    }

    fun setListenerButtonPrimary(listener: DialogSelectionCustomListener): DialogSelectionCustom {
        mListener = listener
        return this
    }
}

interface DialogSelectionCustomListener {
//    fun onClick(dialog: DialogSelectionCustom, text: String?)
    fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?)
}