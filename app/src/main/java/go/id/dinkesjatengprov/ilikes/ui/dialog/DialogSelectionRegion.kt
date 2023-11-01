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
import go.id.dinkesjatengprov.ilikes.adapter.SelectionRegionRecyclerView
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.databinding.DialogSelectStringBinding

class DialogSelectionRegion(
    context: Context,
    val titleDialog: String,
    val list: ArrayList<SelectionModel>?,
    val selection: SelectionModel?
) : Dialog(context) {

    lateinit var binding: DialogSelectStringBinding
    var mSelection: SelectionModel? = selection

    var mListener: DialogSelectionRegionListener? = null

    init {
        mListener = object : DialogSelectionRegionListener {
            override fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?) {
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
        val adapter = SelectionRegionRecyclerView(list, selection)
        binding.dsgRvOption.adapter = adapter

        binding.dmBtnSecondary.setOnClickListener {
            dismiss()
            mSelection = adapter.mSelection
            mListener?.onClick(this, mSelection)
        }

    }

    fun setListenerButtonPrimary(listener: DialogSelectionRegionListener): DialogSelectionRegion {
        mListener = listener
        return this
    }
}

interface DialogSelectionRegionListener {
    fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?)
}